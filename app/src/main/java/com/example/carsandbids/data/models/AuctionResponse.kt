package com.example.carsandbids.data.models

data class AuctionResponse(
    val data: AuctionGroup
)

data class AuctionGroup(
    val id: Int,
    val documentId: String,
    val name: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val auctions: List<Auction>
)

data class AuctionSingleResponse(
    val data: List<Auction>,
    val meta: Meta
)
