package com.pkg.stockmarketapp.di

import android.content.Context
import androidx.room.Room
import com.pkg.stockmarketapp.data.csv.CompanyListingParser
import com.pkg.stockmarketapp.data.csv.CsvParser
import com.pkg.stockmarketapp.data.local.StockDatabase
import com.pkg.stockmarketapp.data.remote.StockApi
import com.pkg.stockmarketapp.data.repository.StockRepositoryImpl
import com.pkg.stockmarketapp.domain.modal.CompanyListing
import com.pkg.stockmarketapp.domain.repository.StockRepository
import com.pkg.stockmarketapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockDatabase(@ApplicationContext context: Context): StockDatabase {
        return Room.databaseBuilder(
            context = context, name = Constants.DATABASE_NAME, klass = StockDatabase::class.java
        ).build()
    }


    @Provides
    @Singleton
    fun provideStockRepository(
        stockDatabase: StockDatabase,
        stockApi: StockApi,
        @IoDispatcher dispatcher: CoroutineDispatcher,
        csvParser: CsvParser<CompanyListing>,
    ): StockRepository {
        return StockRepositoryImpl(
            api = stockApi,
            db = stockDatabase,
            csvParser = csvParser,
            dispatcher = dispatcher
        )
    }

    @Provides
    @Singleton
    fun provideCsvParser(@IoDispatcher dispatcher: CoroutineDispatcher): CsvParser<CompanyListing> {
        return CompanyListingParser(dispatcher)
    }

}