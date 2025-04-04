package com.example.wallpaperapp.ui.favorites

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
class FavoritesViewModel @Inject constructor(
    private val repository: WallpaperRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Wallpaper>>(emptyList())
    val favorites: StateFlow<List<Wallpaper>> = _favorites.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.getFavorites()) {
                is Resource.Success -> {
                    _favorites.value = result.data ?: emptyList()
                }
                is Resource.Error -> {
                    _error.value = result.message
                }
            }
            _isLoading.value = false
        }
    }

    fun removeFavorite(wallpaperId: Int) {
        viewModelScope.launch {
            repository.removeFavorite(wallpaperId)
            loadFavorites() // Refresh the list
        }
    }
}