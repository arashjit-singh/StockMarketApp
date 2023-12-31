package com.pkg.stockmarketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(exportSchema = false, entities = [CompanyListingEntity::class], version = 1)
abstract class StockDatabase : RoomDatabase() {
    abstract fun provideDao(): StockDao
}