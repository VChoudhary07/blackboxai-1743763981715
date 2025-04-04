package com.example.wallpaperapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.model.Wallpaper
import com.example.wallpaperapp.data.repository.WallpaperRepository
import com.example.wallpaperapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WallpaperRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadWallpapers()
    }

    fun loadWallpapers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            when (val result = repository.getWallpapers()) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            wallpapers = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun refreshWallpapers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            
            when (val result = repository.refreshWallpapers()) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            wallpapers = result.data ?: emptyList(),
                            isRefreshing = false,
                            error = null
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isRefreshing = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun loadMoreWallpapers() {
        if (_uiState.value.isLoadingMore || _uiState.value.endReached) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }
            
            when (val result = repository.loadMoreWallpapers()) {
                is Resource.Success -> {
                    val newWallpapers = result.data ?: emptyList()
                    _uiState.update {
                        it.copy(
                            wallpapers = it.wallpapers + newWallpapers,
                            isLoadingMore = false,
                            endReached = newWallpapers.isEmpty()
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoadingMore = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }
}

data class HomeUiState(
    val wallpapers: List<Wallpaper> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val endReached: Boolean = false,
    val error: String? = null
)