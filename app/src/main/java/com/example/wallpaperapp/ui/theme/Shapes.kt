package com.example.wallpaperapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val WallpaperShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

// Custom shapes for specific components
val PremiumCardShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
    bottomStart = 4.dp,
    bottomEnd = 4.dp
)

val PremiumButtonShape = RoundedCornerShape(12.dp)
val PremiumDialogShape = RoundedCornerShape(20.dp)
val PremiumChipShape = RoundedCornerShape(50.dp)
val PremiumImageShape = RoundedCornerShape(8.dp)

// Special asymmetric shapes
val BottomSheetShape = RoundedCornerShape(
    topStart = 24.dp,
    topEnd = 24.dp
)

val FloatingActionButtonShape = RoundedCornerShape(16.dp)