package com.example.carsandbids.data.models

data class Meta(
    val pagination: Pagination
)

data class Pagination(
    val page: Int,
    val pageCount: Int,
    val pageSize: Int,
    val total: Int
)