package com.example.carsandbids.viewmodels

import androidx.lifecycle.ViewModel
import com.example.carsandbids.data.models.VideoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.carsandbids.R

class ReelsViewModel : ViewModel() {
    private val _state = MutableStateFlow<List<VideoItem>>(emptyList())
    val state = _state.asStateFlow()

    init {
        _state.value = listOf(
            VideoItem(1, R.raw.video2, "@DougDeMuro", "Every Generation of Range Rover Ranked by Doug DeMuro", 5100, 158, false),
            VideoItem(2, R.raw.video1, "@DougDeMuro", "A New $600,000 V12 Monster from Ferrari!", 8100, 335, false),
            VideoItem(3, R.raw.video4, "@DougDeMuro", "Cadillac Cien Concept Supercar, Doug DeMuro Wants to Buy It!", 37000, 511, false),
            VideoItem(4, R.raw.video3, "@DougDeMuro", "The Original Audi R8 is the Greatest Halo Car of All Time!", 16000, 334, false),
            )
    }
}