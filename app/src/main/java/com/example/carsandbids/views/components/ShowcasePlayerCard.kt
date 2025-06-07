package com.example.carsandbids.views.components

import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.carsandbids.R
import com.example.carsandbids.data.models.VideoItem
import com.example.carsandbids.ui.theme.MineShaft
import com.example.carsandbids.ui.theme.OffGreen
import com.example.carsandbids.ui.theme.Shamrock
import com.example.carsandbids.ui.theme.SilverChalice
import com.example.carsandbids.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(UnstableApi::class)
@ExperimentalMaterial3Api
@Composable
fun ShowcasePlayerCard(
    item: VideoItem,
    exoPlayer: ExoPlayer?,
    isVisible: Boolean,
    onTogglePlayPause: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (exoPlayer == null) return

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val playerView = remember {
        PlayerView(context).apply {
            useController = false
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        }
    }

    DisposableEffect(exoPlayer, isVisible) {
        if (isVisible) {
            playerView.player = exoPlayer
        } else {
            playerView.player = null
        }
        onDispose {
            playerView.player = null
        }
    }

    var showCenterIcon by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                isPlaying = isPlayingNow
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    exoPlayer.seekTo(0)
                    exoPlayer.play()
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    var playbackPosition by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(1L) }
    val sliderMax = (duration.takeIf { it>0 } ?: 1L).toFloat()

    LaunchedEffect(isVisible, exoPlayer) {
        if (isVisible) {
            exoPlayer.play()
            while (true) {
                playbackPosition = exoPlayer.currentPosition
                duration = exoPlayer.duration
                delay(500)
            }
        }
    }

    var isLiked by remember { mutableStateOf(item.isLiked) }
    var likeCount by remember { mutableStateOf(item.likes) }
    var followText by remember { mutableStateOf("Follow") }

    Box(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    onTogglePlayPause()
                    showCenterIcon = true
                    scope.launch {
                        delay(1000)
                        showCenterIcon = false
                    }
                },
                onDoubleTap = { offset ->
                    val screenWidth = size.width
                    if (offset.x < screenWidth / 2f) exoPlayer.seekBack()
                    else exoPlayer.seekForward()
                }
            )
        }
    ) {
        AndroidView(
            factory = { playerView },
            modifier = Modifier.fillMaxSize()
        )

        AnimatedVisibility(
            visible = showCenterIcon || !isPlaying,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(
                        id = if (!isPlaying) R.drawable.play2 else R.drawable.pause2
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center)
                        .alpha(0.8f)
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 0.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = item.author,
                    color = White,
                    style = MaterialTheme.typography.labelMedium
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { followText = if(followText == "Follow") "Unfollow" else "Follow"},
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    modifier = Modifier.height(28.dp),
                    colors = ButtonDefaults.buttonColors(if(followText == "Follow") Shamrock else OffGreen)
                ) {
                    Text(
                        text = followText,
                        style = MaterialTheme.typography.labelSmall,
                        color = MineShaft
                    )
                }
            }

            Text(
                text = item.title,
                color = White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 80.dp, end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    isLiked = !isLiked
                    likeCount = if (isLiked) likeCount + 1 else likeCount - 1
                }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isLiked) R.drawable.heart_filled else R.drawable.heart_outline
                    ),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(30.dp)
                )
            }
            Text(
                text = formatCount(likeCount),
                color = White,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            IconButton(onClick = { /* todo */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.comment),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(28.dp)
                )
            }
            Text(
                text = "${item.comments}",
                color = White,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 2.dp)
            )
        }


        Slider(
            value = if (duration > 1000) playbackPosition.coerceAtMost(duration).toFloat() else 0f,
            onValueChange = {
                exoPlayer.seekTo(it.toLong())
                playbackPosition = it.toLong()
            },
            onValueChangeFinished = {
                if (!exoPlayer.isPlaying && exoPlayer.playbackState == Player.STATE_READY) {
                    exoPlayer.play()
                }
            },
            valueRange = 0f..sliderMax,
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .align(Alignment.BottomCenter),
            colors = SliderDefaults.colors(
                thumbColor = Shamrock,
                activeTrackColor = Shamrock,
                inactiveTrackColor = SilverChalice,
                disabledThumbColor = Shamrock,
            ),
        )
    }
}

fun formatCount(count: Int): String {
    return when {
        count >= 1_000_000 -> String.format("%.1fM", count / 1_000_000f)
        count >= 1_000 -> String.format("%.1fk", count / 1_000f)
        else -> count.toString()
    }
}