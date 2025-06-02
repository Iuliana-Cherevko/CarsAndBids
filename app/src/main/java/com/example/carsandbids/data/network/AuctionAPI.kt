package com.example.carsandbids.data.network

import com.example.carsandbids.data.models.AuctionResponse
import com.example.carsandbids.data.models.AuctionSingleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AuctionAPI {
    @GET("api/auction-groups/myvj0bjns2k55kvwuuukalb1?populate[0]=auctions.main_image")
    suspend fun getAllAuctions() : AuctionResponse

    @GET("api/auctions")
    suspend fun getAuction(
        @Query("filters[auction_id][\$eq]") auctionId: String
    ): AuctionSingleResponse
}