package ru.umarsh.stockmarketapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.umarsh.stockmarketapp.BuildConfig
import ru.umarsh.stockmarketapp.data.local.StockDataBase
import ru.umarsh.stockmarketapp.data.remote.StockApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }


    @Provides
    @Singleton
    fun provideStockDb(app: Application): StockDataBase {
        return Room.databaseBuilder(
            app,
            StockDataBase::class.java,
            "stockdb"
        ).build()
    }
}