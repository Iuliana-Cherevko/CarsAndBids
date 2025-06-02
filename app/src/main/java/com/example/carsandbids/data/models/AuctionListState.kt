package com.example.carsandbids.data.models

data class AuctionListState (
    val isLoading: Boolean = false,
    val auctions: List<Auction> = emptyList(),
    val error: String = "",
)

data class AuctionDetailState(
    val isLoading: Boolean = false,
    val auction: Auction? = null,
    val error: String = ""
)