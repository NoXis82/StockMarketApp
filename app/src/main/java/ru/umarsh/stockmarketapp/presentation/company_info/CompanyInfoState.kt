package ru.umarsh.stockmarketapp.presentation.company_info

import ru.umarsh.stockmarketapp.domain.model.CompanyInfo
import ru.umarsh.stockmarketapp.domain.model.IntraDayInfo

data class CompanyInfoState(
    val stockInfos: List<IntraDayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
