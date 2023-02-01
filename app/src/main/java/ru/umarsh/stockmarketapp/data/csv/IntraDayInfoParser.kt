package ru.umarsh.stockmarketapp.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.umarsh.stockmarketapp.data.mapper.toIntraDayInfo
import ru.umarsh.stockmarketapp.data.remote.dto.IntraDayInfoDto
import ru.umarsh.stockmarketapp.domain.model.IntraDayInfo
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class IntraDayInfoParser @Inject constructor() : CSVParser<IntraDayInfo> {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun parse(stream: InputStream): List<IntraDayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader.readAll().drop(1).mapNotNull { line ->
                val timestamp = line.getOrNull(0)
                val close = line.getOrNull(4)
                val dto = IntraDayInfoDto(
                    timestamp = timestamp ?: return@mapNotNull null,
                    close = close?.toDouble() ?: return@mapNotNull null,
                )
                dto.toIntraDayInfo()
            }.filter {
                it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
            }.sortedBy {
                it.date.hour
            }.also {
                csvReader.close()
            }
        }
    }
}