package com.example.carsandbids.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.carsandbids.views.AuctionDetailsScreen
import com.example.carsandbids.views.AuctionListScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.AuctionList.route
    ) {
        composable(route = Routes.AuctionList.route) {
            AuctionListScreen(
                onAuctionClick = { auctionId ->
                    navController.navigate(Routes.AuctionDetail.createRoute(auctionId))
                }
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