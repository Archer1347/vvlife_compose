package com.vv.life.compose.refresh

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A class to encapsulate details of different indicator sizes.
 *
 * @param size The overall size of the indicator.
 * @param arcRadius The radius of the arc.
 * @param strokeWidth The width of the arc stroke.
 * @param arrowWidth The width of the arrow.
 * @param arrowHeight The height of the arrow.
 */
@Immutable
private data class SwipeRefreshIndicatorSizes(
    val size: Dp,
    val arcRadius: Dp,
    val strokeWidth: Dp,
    val arrowWidth: Dp,
    val arrowHeight: Dp,
)

/**
 * The default/normal size values for [SwipeRefreshIndicator].
 */
private val DefaultSizes = SwipeRefreshIndicatorSizes(
    size = 40.dp,
    arcRadius = 7.5.dp,
    strokeWidth = 2.5.dp,
    arrowWidth = 10.dp,
    arrowHeight = 5.dp,
)

/**
 * The 'large' size values for [SwipeRefreshIndicator].
 */
private val LargeSizes = SwipeRefreshIndicatorSizes(
    size = 56.dp,
    arcRadius = 11.dp,
    strokeWidth = 3.dp,
    arrowWidth = 12.dp,
    arrowHeight = 6.dp,
)

/**
 * Indicator composable which is typically used in conjunction with [SwipeRefresh].
 *
 * @param state The [SwipeRefreshState] passed into the [SwipeRefresh] `indicator` block.
 * @param modifier The modifier to apply to this layout.
 * @param fade Whether the arrow should fade in/out as it is scrolled in. Defaults to true.
 * @param scale Whether the indicator should scale up/down as it is scrolled in. Defaults to false.
 * @param arrowEnabled Whether an arrow should be drawn on the indicator. Defaults to true.
 * @param backgroundColor The color of the indicator background surface.
 * @param contentColor The color for the indicator's contents.
 * @param shape The shape of the indicator background surface. Defaults to [CircleShape].
 * @param largeIndication Whether the indicator should be 'large' or not. Defaults to false.
 * @param elevation The size of the shadow below the indicator.
 */
@Composable
fun SwipeRefreshIndicator(
    state: SwipeRefreshState,
    refreshTriggerDistance: Dp,
    offsetChange: (offset: Float) -> Unit,
    modifier: Modifier = Modifier,
    fade: Boolean = true,
    scale: Boolean = false,
    arrowEnabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    refreshingOffset: Dp = 16.dp,
    largeIndication: Boolean = false,
    elevation: Dp = 6.dp,
) {
    val adjustedElevation = when {
        state.isRefreshing -> elevation
        state.indicatorOffset > 0.5f -> elevation
        else -> 0.dp
    }
    val sizes = if (largeIndication) LargeSizes else DefaultSizes

    val indicatorRefreshTrigger = with(LocalDensity.current) { refreshTriggerDistance.toPx() }

    val indicatorHeight = with(LocalDensity.current) { sizes.size.roundToPx() }
    val refreshingOffsetPx = with(LocalDensity.current) { refreshingOffset.toPx() }

    val slingshot = rememberUpdatedSlingshot(
        offsetY = state.indicatorOffset,
        maxOffsetY = indicatorRefreshTrigger,
        height = indicatorHeight,
    )

    var offset by remember { mutableStateOf(0f) }

    // If the user is currently swiping, we use the 'slingshot' offset directly
    if (state.isSwipeInProgress) {
        offset = slingshot.offset.toFloat()
    }

    LaunchedEffect(state.isSwipeInProgress, state.isRefreshing) {
        // If there's no swipe currently in progress, animate to the correct resting position
        if (!state.isSwipeInProgress) {
            animate(
                initialValue = offset,
                targetValue = when {
                    state.isRefreshing -> indicatorHeight + refreshingOffsetPx
                    else -> 0f
                }
            ) { value, _ ->
                offset = value
            }
        }
    }

    Box(modifier = Modifier.background(Color.Blue)) {
        Surface(
            modifier = modifier
                .size(size = sizes.size)
                .graphicsLayer {
                    // Translate the indicator according to the slingshot
                    translationY = offset - indicatorHeight
                    offsetChange.invoke(offset)
                    val scaleFraction = if (scale && !state.isRefreshing) {
                        val progress = offset / indicatorRefreshTrigger.coerceAtLeast(1f)

                        // We use LinearOutSlowInEasing to speed up the scale in
                        LinearOutSlowInEasing
                            .transform(progress)
                            .coerceIn(0f, 1f)
                    } else 1f

                    scaleX = scaleFraction
                    scaleY = scaleFraction
                }
                .background(Color.Green),
            shape = shape,
            color = backgroundColor,
            elevation = adjustedElevation
        ) {
            val painter = remember { CircularProgressPainter() }
            painter.arcRadius = sizes.arcRadius
            painter.strokeWidth = sizes.strokeWidth
            painter.arrowWidth = sizes.arrowWidth
            painter.arrowHeight = sizes.arrowHeight
            painter.arrowEnabled = arrowEnabled && !state.isRefreshing
            painter.color = contentColor
            val alpha = if (fade) {
                (state.indicatorOffset / indicatorRefreshTrigger).coerceIn(0f, 1f)
            } else {
                1f
            }
            painter.alpha = alpha

            painter.startTrim = slingshot.startTrim
            painter.endTrim = slingshot.endTrim
            painter.rotation = slingshot.rotation
            painter.arrowScale = slingshot.arrowScale

            // This shows either an Image with CircularProgressPainter or a CircularProgressIndicator,
            // depending on refresh state
            Crossfade(
                targetState = state.isRefreshing,
                animationSpec = tween(durationMillis = CrossfadeDurationMs)
            ) { refreshing ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (refreshing) {
                        val circleSize = (sizes.arcRadius + sizes.strokeWidth) * 2
                        CircularProgressIndicator(
                            color = contentColor,
                            strokeWidth = sizes.strokeWidth,
                            modifier = Modifier
                                .size(circleSize)
                                .background(Color.Gray),
                        )
                    } else {
                        Image(
                            painter = painter,
                            contentDescription = "Refreshing",
                        )
                    }
                    Text(text = "${offset - indicatorHeight}")
                }
            }
        }
    }

}

private const val CrossfadeDurationMs = 100