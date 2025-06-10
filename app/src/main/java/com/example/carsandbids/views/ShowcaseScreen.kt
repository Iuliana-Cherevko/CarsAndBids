package com.example.carsandbids.views

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.carsandbids.R
import com.example.carsandbids.viewmodels.VideoPlayerViewModel
import com.example.carsandbids.views.components.BottomNavBar
import com.example.carsandbids.views.components.ShowcasePlayerCard
import kotlinx.coroutines.delay

@ExperimentalMaterial3Api
@Composable
fun ShowcaseScreen(
    viewModel: VideoPlayerViewModel = viewModel(),
    navController: NavController
) {
    val videos by viewModel.videos.collectAsState()
    val pagerState = rememberPagerState(pageCount = { videos.size })
    val context = LocalContext.current
    val currentRoute = navController.currentBackStackEntry?.destination?.route ?: ""

    val currentVideoId = remember { mutableStateOf<Int?>(null) }
    val isBuffering = viewModel.isBuffering

    LaunchedEffect(pagerState.currentPage, videos) {
        if (videos.isNotEmpty()) {
            val video = videos[pagerState.currentPage]
            if (currentVideoId.value != video.id) {
                currentVideoId.value = video.id
//                viewModel.releasePlayer()
                viewModel.exoPlayer?.pause()
                viewModel.initializePlayer(context, video.videoResId)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.releasePlayer()
        }
    }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Scaffold(
            topBar = {},
            bottomBar = {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        if (route != currentRoute) {
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    color = Color.Black
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
//                DebugVideoPlayer()
                VerticalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { pageIndex ->
                    key(videos[pageIndex].id) {
                        ShowcasePlayerCard(
                            item = videos[pageIndex],
                            viewModel = viewModel,
                            isVisible = (pageIndex == pagerState.currentPage),
                            onTogglePlayPause = { viewModel.togglePlayPause() },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                if (isBuffering) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun DebugVideoPlayer() {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val uri = Uri.parse("android.resource://${context.packageName}/${R.raw.video1}")
            val mediaItem = MediaItem.fromUri(uri)
            setMediaItem(mediaItem)
            prepare()
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
//                layoutParams = FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//                )
                useController = true
            }
        },
        modifier = Modifier.fillMaxSize()
    )

    LaunchedEffect(Unit) {
        delay(300)
        exoPlayer.playWhenReady = true
    }
}
