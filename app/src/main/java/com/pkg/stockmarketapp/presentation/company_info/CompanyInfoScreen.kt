package com.pkg.stockmarketapp.presentation.company_info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pkg.stockmarketapp.domain.modal.CompanyInfo

@Composable
fun CompanyInfoScreen(
    modifier: Modifier = Modifier,
    detailViewModel: CompanyInfoViewModel = hiltViewModel(),
) {

    val state = detailViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        state.value.companyInfo?.let {
            CompanyDetail(it)
        }

        state.value.stockInfo.let {
            if (it.isNotEmpty())
                StockChart(
                    stockInfo = state.value.stockInfo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .align(CenterHorizontally)
                )
        }
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.value.isLoading) CircularProgressIndicator()
    }
}

@Composable
fun CompanyDetail(companyInfo: CompanyInfo, modifier: Modifier = Modifier) {

    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = companyInfo.name, style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ), maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = companyInfo.symbol, style = TextStyle(
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold
            ), modifier = Modifier.fillMaxWidth()

        )
        Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
        Text(
            text = "Industry: ${companyInfo.industry}", style = TextStyle(
                fontSize = 14.sp,
            ),
            overflow = TextOverflow.Ellipsis, modifier = Modifier.fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Country: ${companyInfo.country}", style = TextStyle(
                fontSize = 14.sp,
            ),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
        Text(
            text = companyInfo.description,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Market Summary", style = TextStyle(
                fontSize = 18.sp,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
    }


}

