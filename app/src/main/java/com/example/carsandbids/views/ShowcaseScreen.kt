package com.example.carsandbids.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import com.example.carsandbids.ui.theme.White
import com.example.carsandbids.viewmodels.ShowcaseViewModel
import com.example.carsandbids.views.components.BottomNavBar
import com.example.carsandbids.views.components.ShowcasePlayerCard

@ExperimentalMaterial3Api
@Composable
fun ShowcaseScreen(
    viewModel: ShowcaseViewModel = viewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val videos by viewModel.state.collectAsState()
    val pagerState = rememberPagerState(pageCount = { videos.size })
    val currentRoute = navController.currentBackStackEntry?.destination?.route ?: ""

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            playWhenReady = true
        }
    }

    LaunchedEffect(pagerState.currentPage, videos) {
        if (videos.isNotEmpty()) {
            val video = videos[pagerState.currentPage]
            val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}/${video.videoResId}")
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.seekTo(0)
            exoPlayer.prepare()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    fun togglePlayPause() {
        if (exoPlayer.isPlaying) exoPlayer.pause() else exoPlayer.play()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
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
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                VerticalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { pageIndex ->
                    key(videos[pageIndex].id) {
                        ShowcasePlayerCard(
                            item = videos[pageIndex],
                            exoPlayer = exoPlayer,
                            isVisible = (pageIndex == pagerState.currentPage),
                            onTogglePlayPause = { togglePlayPause() },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}