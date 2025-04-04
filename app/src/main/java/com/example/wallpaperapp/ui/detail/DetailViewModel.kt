package com.example.wallpaperapp.ui.detail

import androidx.lifecycle.SavedStateHandle
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
class DetailViewModel @Inject constructor(
    private val repository: WallpaperRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _wallpaper = MutableStateFlow<Wallpaper?>(null)
    val wallpaper: StateFlow<Wallpaper?> = _wallpaper.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        val wallpaperId = savedStateHandle.get<String>("wallpaperId")
        wallpaperId?.let { loadWallpaper(it) }
    }

    fun loadWallpaper(wallpaperId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.getWallpaper(wallpaperId)) {
                is Resource.Success -> {
                    _wallpaper.value = result.data
                    result.data?.let { checkFavoriteStatus(it.id) }
                }
                is Resource.Error -> {
                    _error.value = result.message
                }
            }
            _isLoading.value = false
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _wallpaper.value?.let { wallpaper ->
                if (_isFavorite.value) {
                    repository.removeFavorite(wallpaper.id)
                } else {
                    repository.addFavorite(wallpaper)
                }
                _isFavorite.value = !_isFavorite.value
            }
        }
    }

    private fun checkFavoriteStatus(wallpaperId: Int) {
        viewModelScope.launch {
            _isFavorite.value = repository.isFavorite(wallpaperId)
        }
    }
}