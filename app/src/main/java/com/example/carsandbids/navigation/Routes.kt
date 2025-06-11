package com.example.carsandbids.navigation

sealed class Routes (val route: String) {
    object AuctionList : Routes("auction_list")
    object Reels : Routes("reels")
    object AuctionDetail : Routes("auction_detail/{auctionId}") {
        fun createRoute(auctionId: String) = "auction_detail/$auctionId"
    }
}