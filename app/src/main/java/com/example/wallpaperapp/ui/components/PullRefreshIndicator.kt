package com.example.wallpaperapp.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.wallpaperapp.R
import com.example.wallpaperapp.ui.theme.PremiumAccent
import kotlinx.coroutines.launch

@Composable
fun PullRefreshIndicator(
    refreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val refreshTrigger = 150.dp
    val dragOffset = remember { Animatable(0f) }
    val isRefreshing by rememberUpdatedState(refreshing)
    val coroutineScope = rememberCoroutineScope()

    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.pull_refresh_animation)
    )
    val lottieProgress by animateLottieCompositionAsState(
        composition = lottieComposition,
        speed = 1.5f,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        if (dragAmount.y < 0 && dragOffset.value > 0) {
                            coroutineScope.launch {
                                dragOffset.snapTo(0f)
                            }
                        } else if (!isRefreshing) {
                            coroutineScope.launch {
                                dragOffset.snapTo(dragOffset.value + dragAmount.y)
                            }
                        }
                        change.consume()
                    },
                    onDragEnd = {
                        if (dragOffset.value > refreshTrigger.toPx() && !isRefreshing) {
                            haptic.performHapticFeedback(
                                androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress
                            )
                            onRefresh()
                        }
                        coroutineScope.launch {
                            dragOffset.animateTo(
                                0f,
                                animationSpec = tween(300, easing = LinearEasing)
                            )
                        }
                    }
                )
            }
    ) {
        if (dragOffset.value > 0 || isRefreshing) {
            val progress = (dragOffset.value / refreshTrigger.toPx()).coerceIn(0f, 1f)
            val rotation = progress * 180f

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .offset(y = if (isRefreshing) 0.dp else (-80 + progress * 80).dp),
                contentAlignment = Alignment.Center
            ) {
                if (isRefreshing) {
                    LottieAnimation(
                        composition = lottieComposition,
                        progress = lottieProgress,
                        modifier = Modifier.size(48.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = "Pull to refresh",
                        tint = PremiumAccent,
                        modifier = Modifier
                            .size(32.dp)
                            .rotate(rotation)
                    )
                }
            }
        }
    }
}