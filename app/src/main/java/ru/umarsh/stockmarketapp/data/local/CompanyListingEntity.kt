package ru.umarsh.stockmarketapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companylistingentity")
data class CompanyListingEntity(
    @PrimaryKey val id: Long? = null,
    val name: String,
    val symbol: String,
    val exchange: String
)