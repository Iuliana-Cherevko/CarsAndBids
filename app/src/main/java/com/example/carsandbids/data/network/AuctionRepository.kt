package com.example.carsandbids.data.network

import com.example.carsandbids.data.models.Auction
import com.example.carsandbids.data.models.AuctionResponse
import com.example.carsandbids.data.network.AuctionAPI
import javax.inject.Inject

class AuctionRepository @Inject constructor(
    private val api: AuctionAPI
) {
    suspend fun getAllAuctions(): List<Auction> {
        val response = api.getAllAuctions()
        return response.data.auctions
    }

    suspend fun getAuctionById(auctionId: String): Auction {
        val response = api.getAuction(auctionId)
        return response.data.first()
    }
}
