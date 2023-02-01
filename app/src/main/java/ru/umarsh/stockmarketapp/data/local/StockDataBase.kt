package ru.umarsh.stockmarketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [CompanyListingEntity::class],
    exportSchema = false
)
abstract class StockDataBase : RoomDatabase() {
    abstract val stockDao: StockDao
}