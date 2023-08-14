package com.pkg.stockmarketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pkg.stockmarketapp.presentation.company_info.CompanyInfoScreen
import com.pkg.stockmarketapp.presentation.company_listings.CompanyListingScreen
import com.pkg.stockmarketapp.ui.theme.StockMarketAppTheme
import com.pkg.stockmarketapp.util.Constants.KEY_COMPANY_SYMBOL
import com.pkg.stockmarketapp.util.Constants.ROUTE_COMPANY_LIST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            StockMarketAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = ROUTE_COMPANY_LIST) {
                        composable(route = ROUTE_COMPANY_LIST) {
                            CompanyListingScreen(navHostController = navController)
                        }
                        composable(
                            "companyDetail/{symbol}",
                            arguments = listOf(navArgument(
                                name = KEY_COMPANY_SYMBOL
                            ) {
                                type = NavType.StringType
                            })
                        ) {
                            CompanyInfoScreen()
                        }
                    }
                }
            }
        }
    }
}
