package com.example.carsandbids.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsandbids.data.models.AuctionListState
import com.example.carsandbids.data.network.AuctionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuctionListViewModel @Inject constructor(
    private val auctionRepository: AuctionRepository
) : ViewModel() {
    private val _state = mutableStateOf(AuctionListState())
    val state: State<AuctionListState> = _state

    init {
        loadAuctions()
    }

    private fun loadAuctions() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val response = auctionRepository.getAllAuctions()
                _state.value = _state.value.copy(
                    auctions = response,
                    isLoading = false,
                    error = ""
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.localizedMessage ?: "Error",
                    isLoading = false
                )
            }
        }
    }

}
