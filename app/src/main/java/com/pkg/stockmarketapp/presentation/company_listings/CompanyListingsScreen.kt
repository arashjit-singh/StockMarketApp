package com.pkg.stockmarketapp.presentation.company_listings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListingScreen(
    modifier: Modifier = Modifier,
    listingViewModel: CompanyListingViewModel = hiltViewModel(),
) {
    val state = listingViewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            value = state.value.searchQuery, onValueChange = {
                listingViewModel.onUserEvent(CompanyListingsEvent.OnQueryChange(it))
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), placeholder = {
                Text("Search...")
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.value.companies) {
                CompanyListingItem(company = it)
            }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (state.value.isLoading) {
                CircularProgressIndicator()
            }
        }

    }
}
