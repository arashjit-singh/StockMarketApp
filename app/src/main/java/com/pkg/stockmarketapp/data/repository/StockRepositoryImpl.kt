package com.pkg.stockmarketapp.data.repository

import androidx.room.withTransaction
import com.pkg.stockmarketapp.data.csv.CsvParser
import com.pkg.stockmarketapp.data.local.StockDatabase
import com.pkg.stockmarketapp.data.mapper.toCompanyEntity
import com.pkg.stockmarketapp.data.mapper.toCompanyListing
import com.pkg.stockmarketapp.data.remote.StockApi
import com.pkg.stockmarketapp.di.IoDispatcher
import com.pkg.stockmarketapp.domain.modal.CompanyListing
import com.pkg.stockmarketapp.domain.repository.StockRepository
import com.pkg.stockmarketapp.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class StockRepositoryImpl(
    private val api: StockApi,
    private val db: StockDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val csvParser: CsvParser<CompanyListing>,
) : StockRepository {
    override fun getCompanyListing(
        query: String,
        fetchFromRemote: Boolean,
    ): Flow<Resource<List<CompanyListing>>> =
        flow {
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
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
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
}