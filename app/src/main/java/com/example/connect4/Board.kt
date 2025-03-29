package com.example.connect4

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.asAndroidPath

@Composable
fun Board(
    rows: Int,
    cols: Int,
    onDiscDropped: (Int) -> Unit,
    content: @Composable (boardBounds: Rect) -> Unit
) {
    var boardBounds by remember { mutableStateOf(Rect.Zero) }
    val padding = 5.dp
    val paddingPx = with(LocalDensity.current) { padding.toPx() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .onGloballyPositioned { coordinates ->
                boardBounds = coordinates.boundsInParent()
            }
    ) {
        content(boardBounds)
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            drawBoardBackground(rows, cols, paddingPx)
        }
    }
}

fun DrawScope.drawBoardBackground(rows: Int, cols: Int, padding: Float) {
    val cellSize = (size.width - padding * 2) / cols
    val boardHeight = rows * cellSize

    // Create a path for the circles
    val path = Path().apply {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val x = col * cellSize + cellSize / 2
                val y = row * cellSize + cellSize / 2
                addOval(androidx.compose.ui.geometry.Rect(x - cellSize / 2 * 0.8f, y - cellSize / 2 * 0.8f, x + cellSize / 2 * 0.8f, y + cellSize / 2 * 0.8f))
            }
        }
    }

    // Draw the blue background
    drawRect(
        color = Color.Blue,
        size = Size(size.width, boardHeight)
    )

    // Draw the transparent circles
    drawIntoCanvas { canvas ->
        canvas.nativeCanvas.drawPath(path.asAndroidPath(), androidx.compose.ui.graphics.Paint().apply {
            color = Color.Transparent
            blendMode = androidx.compose.ui.graphics.BlendMode.Clear
        }.asFrameworkPaint())
    }
}