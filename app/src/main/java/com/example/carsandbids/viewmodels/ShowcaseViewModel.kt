package com.example.carsandbids.viewmodels

import androidx.lifecycle.ViewModel
import com.example.carsandbids.data.models.VideoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.carsandbids.R

class ShowcaseViewModel : ViewModel() {
    private val _state = MutableStateFlow<List<VideoItem>>(emptyList())
    val state = _state.asStateFlow()

    // download videos and input real data
    init {
        _state.value = listOf(
            VideoItem(1, R.raw.video1, "@DougDeMuro", "Porsche Tractors Cost More than First Gen Cayennes!", 2700, 67, false),
            VideoItem(2, R.raw.video2, "@DougDeMuro", "A New $600,000 V12 Monster from Ferrari!", 8100, 335, false),
            VideoItem(3, R.raw.video3, "@DougDeMuro", "Every Generation of Range Rover Ranked by Doug DeMuro", 5100, 158, false),
            VideoItem(4, R.raw.video4, "@DougDeMuro", "The Original Audi R8 is the Greatest Halo Car of All Time!", 16000, 334, false),
            VideoItem(5, R.raw.video5, "@DougDeMuro", "Cadillac Cien Concept Supercar, Doug DeMuro Wants to Buy It!", 37000, 511, false),
            )
    }
}