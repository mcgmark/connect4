package com.example.connect4

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.connect4.Utils.calculateInitialPosition
import com.example.connect4.Utils.calculateTargetY
import com.example.connect4.Utils.getNextEmptyRow
import kotlin.math.roundToInt

@Composable
fun Disc(
    color: Color,
    boardBounds: Rect,
    isDragging: Boolean = false,
    col: Int,
    board: Array<IntArray>,
    rows: Int,
    padding: Float,
    onDiscDropped: (Int, Int) -> Unit,
    sizeFactor: Float = 0.8f // Add a size factor to adjust the disc size
) {
    var currentPosition by remember { mutableStateOf(calculateInitialPosition(boardBounds, col, padding)) }
    var targetY by remember { mutableStateOf(0.dp) }
    var isFalling by remember { mutableStateOf(false) }
    val cellSize = boardBounds.width / board[0].size
    val discRadius = cellSize / 2 * sizeFactor
    val discDiameter = discRadius * 2

    if (isFalling) {
        targetY = calculateTargetY(boardBounds, board, col, rows, cellSize.dp, padding)
    }

    val animatedY: Dp by animateDpAsState(
        targetValue = if (isFalling) targetY else currentPosition.y.dp,
        animationSpec = tween(durationMillis = 500), label = "gravity"
    )

    Box(
        modifier = Modifier
            .size(discDiameter.dp)
            .offset {
                IntOffset(
                    x = currentPosition.x.roundToInt() - discRadius.roundToInt(),
                    y = animatedY.roundToPx() - discRadius.roundToInt()
                )
            }
            .clip(CircleShape)
            .background(color)
            .pointerInput(Unit) {
                if (isDragging) {
                    detectDragGestures(
                        onDragStart = { isFalling = false },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            currentPosition = Offset(
                                x = (currentPosition.x + dragAmount.x).coerceIn(
                                    boardBounds.left + discRadius + padding,
                                    boardBounds.right - discRadius - padding
                                ),
                                y = currentPosition.y
                            )
                        },
                        onDragEnd = {
                            isFalling = true
                            val targetRow = getNextEmptyRow(board, col, rows)
                            if (targetRow != -1) {
                                onDiscDropped(targetRow, col)
                            }
                        }
                    )
                }
            }
    )
}