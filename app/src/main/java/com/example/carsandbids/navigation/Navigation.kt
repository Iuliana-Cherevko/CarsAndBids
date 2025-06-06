package com.example.carsandbids.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.carsandbids.views.AuctionDetailsScreen
import com.example.carsandbids.views.AuctionListScreen
import com.example.carsandbids.views.ShowcaseScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.AuctionList.route
    ) {
        composable(route = Routes.AuctionList.route) {
            AuctionListScreen(
                navController = navController,
                onAuctionClick = { auctionId ->
                    navController.navigate(Routes.AuctionDetail.createRoute(auctionId))
                }
            )
        }

        composable(route = Routes.Showcase.route) {
            ShowcaseScreen(
                navController = navController,
            )
        }

        composable(
            route = Routes.AuctionDetail.route,
            arguments = listOf(navArgument("auctionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val auctionId = backStackEntry.arguments?.getString("auctionId") ?: return@composable
            AuctionDetailsScreen(
                auctionId = auctionId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}