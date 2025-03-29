package com.example.connect4

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Board(rows: Int, cols: Int, content: @Composable (Rect) -> Unit) {
    // Example Board Implementation (replace with a proper UI later)
    Box(
        modifier = Modifier
            .background(Color.Blue) // Background of the board
            .padding(8.dp)
    ) {
        //Calculates the bounds of the Board
        val cellSize = 50.dp // Define the size of each cell
        val cellPadding = 5.dp
        val boardWidth = (cellSize + cellPadding) * cols - cellPadding
        val boardHeight = (cellSize + cellPadding) * rows - cellPadding
        val boardRect = Rect(Offset(0f, 0f), Offset(boardWidth.value, boardHeight.value))
        Canvas(modifier = Modifier.size(boardWidth, boardHeight)) {
            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    val x = col * (cellSize.toPx() + cellPadding.toPx())
                    val y = row * (cellSize.toPx() + cellPadding.toPx())
                    drawCircle(
                        color = Color.White,
                        radius = cellSize.toPx() / 2,
                        center = Offset(x + cellSize.toPx() / 2, y + cellSize.toPx() / 2),
                    )
                }
            }
        }
        //Calling the disc function to display the current game state
        content(boardRect)
    }

}