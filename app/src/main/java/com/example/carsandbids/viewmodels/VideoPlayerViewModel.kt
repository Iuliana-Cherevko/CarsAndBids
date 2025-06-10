package com.example.carsandbids.viewmodels

import android.content.Context
import androidx.annotation.RawRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.example.carsandbids.R
import com.example.carsandbids.data.models.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideoPlayerViewModel : ViewModel() {

    private val _videos = MutableStateFlow<List<VideoItem>>(emptyList())
    val videos = _videos.asStateFlow()

    private var _exoPlayer: ExoPlayer? = null
    val exoPlayer: ExoPlayer? get() = _exoPlayer

    var playbackPosition by mutableStateOf(0L)
        private set

    var playWhenReady by mutableStateOf(true)
        private set

    var isBuffering by mutableStateOf(true)
        private set

    var isPlayerReady by mutableStateOf(false)
        private set

    private var currentVideoResId: Int? = null

    var playbackPositionFlow = MutableStateFlow(0L)
    var durationFlow = MutableStateFlow(1L)

    private var positionJob: Job? = null

    init {
        _videos.value = listOf(
            VideoItem(1, R.raw.video1, "@DougDeMuro", "A New $600,000 V12 Monster from Ferrari!", 8100, 335, false),
            VideoItem(2, R.raw.video2, "@DougDeMuro", "Every Generation of Range Rover Ranked by Doug DeMuro", 5100, 158, false),
            VideoItem(3, R.raw.video3, "@DougDeMuro", "The Original Audi R8 is the Greatest Halo Car of All Time!", 16000, 334, false),
            VideoItem(4, R.raw.video4, "@DougDeMuro", "Cadillac Cien Concept Supercar, Doug DeMuro Wants to Buy It!", 37000, 511, false),
        )
    }

    fun initializePlayer(context: Context, @RawRes videoResId: Int) {
        if (currentVideoResId == videoResId && _exoPlayer != null) return

        _exoPlayer?.run {
            stopTrackingProgress()
            playWhenReady = false
            release()
        }
        _exoPlayer = null

        currentVideoResId = videoResId
        val uri = "android.resource://${context.packageName}/$videoResId"

        _exoPlayer = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            seekTo(0L)
            playWhenReady = true

            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    isBuffering = (state == Player.STATE_BUFFERING)
                    isPlayerReady = (state == Player.STATE_READY)

                    if (state == Player.STATE_READY && playWhenReady) {
                        play()
                    }
                }
            })
        }

//        if (currentVideoResId != videoResId) {
//            playbackPosition = 0L
//        }

        startTrackingProgress()
    }

    fun togglePlayPause() {
        _exoPlayer?.let {
            if (it.isPlaying) it.pause() else it.play()
        }
    }

    fun releasePlayer() {
        _exoPlayer?.run {
            playbackPosition = currentPosition
            playWhenReady = this.playWhenReady
            stopTrackingProgress()
            release()
        }
        _exoPlayer = null
        currentVideoResId = null
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }

    fun startTrackingProgress() {
        positionJob?.cancel()
        positionJob = viewModelScope.launch {
            while (true) {
                _exoPlayer?.let {
                    playbackPositionFlow.value = it.currentPosition
                    durationFlow.value = it.duration.takeIf { d -> d > 0 } ?: 1L
                }
                delay(500)
            }
        }
    }

    fun stopTrackingProgress() {
        positionJob?.cancel()
    }

}