package com.pkg.stockmarketapp.presentation.company_info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CompanyInfoScreen(
    modifier: Modifier = Modifier,
    symbol: String,
    detailViewModel: CompanyInfoViewModel = hiltViewModel(),
) {

    val state = detailViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        detailViewModel.onEvent(CompanyInfoEvent.UpdateSymbol(symbol = symbol))
    }

    Column(modifier = modifier) {
        if (state.value.companyInfo != null)
            Text(text = state.value.companyInfo.toString())
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.value.isLoading)
            CircularProgressIndicator()
    }
}

