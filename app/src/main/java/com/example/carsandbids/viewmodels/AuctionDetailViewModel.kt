package com.example.carsandbids.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsandbids.data.models.Auction
import com.example.carsandbids.data.models.AuctionDetailState
import com.example.carsandbids.data.network.AuctionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuctionDetailsViewModel @Inject constructor(
    private val auctionRepository: AuctionRepository
) : ViewModel() {
    private val _state = mutableStateOf(AuctionDetailState())
    val state: State<AuctionDetailState> = _state

    fun loadAuctionById(auctionId: String) {
        viewModelScope.launch {
            _state.value = AuctionDetailState(isLoading = true)
            try {
                val auction: Auction = auctionRepository.getAuctionById(auctionId)
                _state.value = AuctionDetailState(auction = auction, isLoading = false)
            } catch (e: Exception) {
                _state.value = AuctionDetailState(
                    error = e.localizedMessage ?: "Error",
                    isLoading = false
                )
            }
        }
    }
}
