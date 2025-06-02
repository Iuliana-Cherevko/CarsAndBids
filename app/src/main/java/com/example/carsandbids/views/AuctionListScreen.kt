package com.example.carsandbids.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.carsandbids.ui.theme.White
import com.example.carsandbids.viewmodels.AuctionListViewModel
import com.example.carsandbids.views.components.AuctionListItem
import com.example.carsandbids.views.components.BottomNavBar
import com.example.carsandbids.views.components.TopNavBar

@Composable
fun AuctionListScreen(
    viewModel: AuctionListViewModel = hiltViewModel(),
    onAuctionClick: (String) -> Unit
) {
    val state by viewModel.state

    Box(
        modifier = Modifier
            .background(White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Scaffold (
            topBar = {
                TopNavBar()
            },
            bottomBar = {
                BottomNavBar()
            }
        ) { innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .background(White)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = innerPadding
                ) {
                    items(state.auctions) { auction ->
                        AuctionListItem(
                            auction = auction,
                            onClick = {onAuctionClick(auction.auctionId)}
                        )
                    }
                }
            }

            if(state.error.isNotBlank()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 20.dp)
                    )
                }
            }
            if(state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            }
        }
    }
}
