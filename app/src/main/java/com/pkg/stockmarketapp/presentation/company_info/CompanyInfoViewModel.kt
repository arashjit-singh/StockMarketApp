package com.pkg.stockmarketapp.presentation.company_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkg.stockmarketapp.domain.repository.StockRepository
import com.pkg.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val stockRepository: StockRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private var _uiState = MutableStateFlow(CompanyInfoState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: CompanyInfoEvent) {
        when (event) {
            is CompanyInfoEvent.UpdateSymbol -> {
                viewModelScope.launch {
                    val symbol = _uiState.value.symbol
                    if (symbol.isBlank()) {
                        _uiState.update {
                            it.copy(symbol = event.symbol)
                        }
                        getCompanyDetails()
                    }
                }
            }
        }
    }

    private fun getCompanyDetails() {
        viewModelScope.launch {
            stockRepository.getCompanyDetails(_uiState.value.symbol).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                error = "",
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                companyInfo = result.data
                            )
                        }
                    }
                }
            }
        }
    }
}