package com.vv.life.compose.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.vv.life.mvvmhabit.utils.DpUtil
import kotlin.math.max

/**
 * Desc: 转菊花
 * <p>
 * Date: 2021/8/5
 * Copyright: Copyright (c) 2010-2020
 * Company: @微微科技有限公司
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @author: linjiaqiang
 */
@Composable
fun ProgressPainter(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition()
    val currentRotation by transition.animateValue(
        30,
        3600,
        Int.VectorConverter,
        infiniteRepeatable(
            animation = tween(
                durationMillis = 10000,
                easing = LinearEasing
            )
        )
    )
    val width = DpUtil.dp2px(16F).toFloat()
    val path = remember {
        Path().apply {
            val r = max(1f, DpUtil.dp2px(16F) / 22f)
            addRoundRect(
                RoundRect(
                    left = width - r * 2,
                    top = width / 2f - r,
                    right = width,
                    bottom = width / 2f + r,
                    radiusX = 8F,
                    radiusY = 8F,
                )
            )
            addRect(Rect(width - 5 * r, width / 2f - r, width - r, width / 2f + r))
            addRoundRect(
                RoundRect(
                    left = width - 6 * r,
                    top = width / 2f - r,
                    right = width - 4,
                    bottom = width / 2f + r,
                    radiusX = 8F,
                    radiusY = 8F,
                )
            )
        }
    }
    val color = Color(0xFF99999A)
    Canvas(
        modifier
            .size(16.dp)
            .focusable()
    ) {
        val degree = 30F * (currentRotation / 30)
        rotate(degree) {
            repeat(12) {
                rotate(30F * it) {
                    drawPath(
                        path = path,
                        color = color,
                        alpha = minOf((it + 5F) * 0x11 / 255, 1F),
                    )
                }
            }
        }
    }
}