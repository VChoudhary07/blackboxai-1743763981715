package com.example.wallpaperapp.ui.detail

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.wallpaperapp.R
import com.example.wallpaperapp.ui.components.PremiumIconButton
import com.example.wallpaperapp.ui.theme.PremiumAccent
import com.example.wallpaperapp.ui.theme.PremiumDarkAccent
import com.example.wallpaperapp.util.WallpaperUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    wallpaperId: String,
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val wallpaper by viewModel.wallpaper.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    LaunchedEffect(wallpaperId) {
        viewModel.loadWallpaper(wallpaperId)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            wallpaper?.let { wallpaper ->
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context)
                            .data(wallpaper.src.original)
                            .crossfade(true)
                            .build()
                    ),
                    contentDescription = wallpaper.photographer,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Gradient overlay at top for back button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.6f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Back button
                PremiumIconButton(
                    onClick = onBackClick,
                    icon = Icons.Default.ArrowBack,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                )

                // Bottom bar with actions
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    // Photographer attribution
                    Text(
                        text = stringResource(R.string.photographer_by, wallpaper.photographer),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Action buttons
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                WallpaperUtils.setAsWallpaper(context, wallpaper.src.original)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PremiumAccent,
                                contentColor = Color.White
                            ),
                            shape = CircleShape
                        ) {
                            Text(stringResource(R.string.set_as_wallpaper))
                        }

                        IconButton(
                            onClick = { viewModel.toggleFavorite() },
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(PremiumDarkAccent)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (isFavorite) stringResource(R.string.remove_from_favorites) 
                                else stringResource(R.string.add_to_favorites),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}