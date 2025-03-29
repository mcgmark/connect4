package com.example.connect4

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp

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
            .onGloballyPositioned { coordinates ->
                boardBounds = coordinates.boundsInParent()
                println("boardBounds: $boardBounds")
            }
    ) {
        Canvas(
            modifier = Modifier
                .size(calculateBoardSize(rows, cols))
                .padding(padding)
        ) {
            drawBoardBackground(rows, cols, paddingPx)
        }
        content(boardBounds)
    }
}

fun calculateBoardSize(rows: Int, cols: Int) = (50.dp * cols).coerceAtMost(50.dp * rows)

fun DrawScope.drawBoardBackground(rows: Int, cols: Int, padding: Float) {
    val cellSize = size.width / cols
    val boardHeight = rows * cellSize
    drawRect(
        color = Color.Blue,
        size = Size(size.width, boardHeight)
    )
    for (row in 0 until rows) {
        for (col in 0 until cols) {
            val x = col * cellSize
            val y = row * cellSize
            drawCircle(
                color = Color.White,
                radius = cellSize / 2 * 0.8f,
                center = Offset(x + cellSize / 2, y + cellSize / 2)
            )
        }
    }
}