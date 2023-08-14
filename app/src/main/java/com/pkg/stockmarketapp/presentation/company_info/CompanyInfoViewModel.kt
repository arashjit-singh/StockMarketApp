package com.pkg.stockmarketapp.presentation.company_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkg.stockmarketapp.domain.repository.StockRepository
import com.pkg.stockmarketapp.util.Constants.KEY_COMPANY_SYMBOL
import com.pkg.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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

    init {
        getAllDetails()
    }

    private fun getAllDetails() {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>(KEY_COMPANY_SYMBOL) ?: return@launch

            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            val companyDetails = async {
                stockRepository.getCompanyInfo(symbol)
            }

            val intraDayInfo = async {
                stockRepository.getIntraDayInfo(symbol)
            }

            when (val result = companyDetails.await()) {
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            error = result.message, isLoading = false, companyInfo = null
                        )
                    }
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            companyInfo = result.data, isLoading = false, error = null
                        )
                    }
                }

                else -> Unit
            }


            when (val result = intraDayInfo.await()) {
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            error = result.message, isLoading = false, stockInfo = emptyList()
                        )
                    }
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            stockInfo = result.data ?: emptyList(), isLoading = false, error = null
                        )
                    }
                }

                else -> Unit
            }
        }
    }
}