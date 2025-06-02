package com.example.carsandbids.views

import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.carsandbids.ui.theme.White
import com.example.carsandbids.viewmodels.AuctionDetailsViewModel
import com.example.carsandbids.views.components.TopBackBar

@Composable
fun AuctionDetailsScreen (
    viewModel: AuctionDetailsViewModel = hiltViewModel(),
    auctionId: String,
    onBackClick: () -> Unit,
) {
    LaunchedEffect(auctionId) {
        viewModel.loadAuctionById(auctionId)
    }

    val state by viewModel.state
    val auctionUrl = state.auction?.auctionUrl

    Box(
        modifier = Modifier
            .background(White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Scaffold(
            topBar = {
                Column {
                    TopBackBar(
                        title = state.auction?.title ?: "Loading",
                        onBackClick = onBackClick
                    )
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(White)
            ) {
                auctionUrl?.let { url ->
                    AndroidView(
                        factory = { context ->
                            WebView(context).apply {
                                settings.javaScriptEnabled = true
                                settings.domStorageEnabled = true
                                settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                                webViewClient = android.webkit.WebViewClient()
                                webChromeClient = android.webkit.WebChromeClient()
                                loadUrl(url)
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                    )
                } ?: run {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Loading page...",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}