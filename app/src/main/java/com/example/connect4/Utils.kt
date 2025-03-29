package com.example.connect4

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Utils {
    fun calculateInitialPosition(boardBounds: Rect, col: Int, padding: Float): Offset {
        val cellSize = boardBounds.width / 7
        val x = (boardBounds.left + (col * cellSize)) + cellSize / 2
        val y = boardBounds.top + padding
        return Offset(x, y)
    }

    fun calculateTargetY(
        boardBounds: Rect,
        board: Array<IntArray>,
        col: Int,
        rows: Int,
        cellSize: Dp,
        cellPadding: Float
    ): Dp {
        val boardRow = getNextEmptyRow(board, col, rows)
        if (boardRow == -1) return boardBounds.top.dp

        val y = boardBounds.bottom - (boardRow * cellSize.value) + cellPadding
        return y.dp
    }

    fun getNextEmptyRow(board: Array<IntArray>, col: Int, rows: Int): Int {
        for (row in rows - 1 downTo 0) {
            if (board[row][col] == 0) {
                return row
            }
        }
        return -1
    }
}