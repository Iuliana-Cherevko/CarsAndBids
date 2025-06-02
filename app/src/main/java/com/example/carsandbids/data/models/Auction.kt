package com.example.carsandbids.data.models

import com.squareup.moshi.Json

data class Auction(
    val id: Int,
    val documentId: String,
    val title: String,
    val subtitle: String,
    @Json(name = "auction_id")
    val auctionId: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    @Json(name = "num_bids")
    val numBids: Int,
    @Json(name = "num_comments")
    val numComments: Int,
    @Json(name = "high_bid")
    val highBid: Int,
    @Json(name = "auction_url")
    val auctionUrl: String,
    @Json(name = "auction_end")
    val auctionEnd: String,
    @Json(name = "auction_status")
    val auctionStatus: String,
    @Json(name = "main_image")
    val mainImage: AuctionImage?
)

data class AuctionImage(
    val id: Int,
    val documentId: String,
    val name: String,
    val alternativeText: String?,
    val caption: String?,
    val width: Int,
    val height: Int,
    val formats: ImageFormats,
    val hash: String,
    val ext: String,
    val mime: String,
    val size: Double,
    val url: String,
    val previewUrl: String?,
    val provider: String,
    @Json(name = "provider_metadata")
    val providerMetadata: Any?,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String
)

data class ImageFormats(
    val large: ImageFormat?,
    val small: ImageFormat?,
    val medium: ImageFormat?,
    val thumbnail: ImageFormat?
)

data class ImageFormat(
    val ext: String,
    val url: String,
    val hash: String,
    val mime: String,
    val name: String,
    val path: String?,
    val size: Double,
    val width: Int,
    val height: Int,
    val sizeInBytes: Int
)