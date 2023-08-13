package com.pkg.stockmarketapp.presentation.company_listings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkg.stockmarketapp.domain.repository.StockRepository
import com.pkg.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val stockRepository: StockRepository,
) : ViewModel() {

    private var _uiState = MutableStateFlow(CompanyListingState())
    val uiState = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        getCompanyListing()
    }

    fun onUserEvent(event: CompanyListingsEvent) {
        when (event) {
            is CompanyListingsEvent.OnQueryChange -> {
                _uiState.update {
                    it.copy(
                        searchQuery = event.query.lowercase()
                    )
                }

                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListing()
                }

            }

            CompanyListingsEvent.Refresh -> {
                getCompanyListing(true)
            }
        }
    }

    private fun getCompanyListing(fetchFromRemote: Boolean = false) {
        Timber.i("Fetch ${uiState.value.searchQuery}")
        viewModelScope.launch {
            stockRepository.getCompanyListing(
                query = _uiState.value.searchQuery,
                fetchFromRemote = fetchFromRemote
            )
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    message = it.message
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }

                        is Resource.Success -> {
                            result.data?.let { list ->
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        companies = list
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }
}