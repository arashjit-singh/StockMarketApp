package com.pkg.stockmarketapp.data.repository

import androidx.room.withTransaction
import com.pkg.stockmarketapp.data.csv.CsvParser
import com.pkg.stockmarketapp.data.local.StockDatabase
import com.pkg.stockmarketapp.data.mapper.toCompanyEntity
import com.pkg.stockmarketapp.data.mapper.toCompanyInfo
import com.pkg.stockmarketapp.data.mapper.toCompanyListing
import com.pkg.stockmarketapp.data.remote.StockApi
import com.pkg.stockmarketapp.di.IoDispatcher
import com.pkg.stockmarketapp.domain.modal.CompanyInfo
import com.pkg.stockmarketapp.domain.modal.CompanyListing
import com.pkg.stockmarketapp.domain.modal.IntraDayInfo
import com.pkg.stockmarketapp.domain.repository.StockRepository
import com.pkg.stockmarketapp.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class StockRepositoryImpl(
    private val api: StockApi,
    private val db: StockDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val csvParser: CsvParser<CompanyListing>,
    private val intraDayParser: CsvParser<IntraDayInfo>,
) : StockRepository {

    val errorMessage = "Couldn't load data"

    override fun getCompanyListing(
        query: String,
        fetchFromRemote: Boolean,
    ): Flow<Resource<List<CompanyListing>>> = flow {
        val stockDao = db.provideDao()
        emit(Resource.Loading())
        val companyListing = stockDao.searchCompanyListing(query)
        emit(Resource.Success(data = companyListing.map {
            it.toCompanyListing()
        }))

        val isDbEmpty = companyListing.isEmpty() && query.isBlank()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

        if (shouldJustLoadFromCache) {
            return@flow
        }

        val remoteListings = try {
            val response = api.getListings()
            csvParser.parse(response.byteStream())
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error(errorMessage))
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error(errorMessage))
            null
        }

        remoteListings?.let {
            db.withTransaction {
                stockDao.clearTable()
                stockDao.insertCompany(it.map { item ->
                    item.toCompanyEntity()
                })
            }
            emit(Resource.Success(data = stockDao.searchCompanyListing("").map {
                it.toCompanyListing()
            }))
        }

    }.flowOn(dispatcher)

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return withContext(dispatcher) {
            try {
                val response = api.getCompanyInfo(symbol = symbol)
                Resource.Success(data = response.toCompanyInfo())
            } catch (e: IOException) {
                e.printStackTrace()
                Resource.Error(errorMessage)
            } catch (e: HttpException) {
                e.printStackTrace()
                Resource.Error(errorMessage)
            }
        }
    }

    override suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfo>> {
        return withContext(dispatcher) {
            try {
                val response = api.getIntraDayInfo(symbol = symbol)
                val data = intraDayParser.parse(response.byteStream())
                Resource.Success(data = data)
            } catch (e: IOException) {
                e.printStackTrace()
                Resource.Error(errorMessage)
            } catch (e: HttpException) {
                e.printStackTrace()
                Resource.Error(errorMessage)
            }
        }
    }
}