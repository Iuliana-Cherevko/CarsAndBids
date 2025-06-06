package com.example.carsandbids.data.models

data class VideoItem (
    val id: Int,
    val videoResId: Int,
    val author: String,
    val title: String,
    val likes: Int,
    val comments: Int,
    var isLiked: Boolean,
)