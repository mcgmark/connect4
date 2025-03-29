package com.example.connect4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connect4.ui.theme.Connect4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Connect4App()
        }
    }
}

@Composable
fun Connect4App() {
    Connect4Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Connect4Screen()
        }
    }
}

@Composable
fun Connect4Screen() {
    // Define the board
    val rows = 6
    val cols = 7
    // Define the initial board state. 0 means empty, 1 for player one and -1 for player two
    val board = remember { Array(rows) { IntArray(cols) { 0 } } }
    var draggedDisc: Pair<Color, Int>? by remember { mutableStateOf(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Reduced padding for better spacing
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Displaying a title or message is a good practice
        Text("Connect 4", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.padding(16.dp)) // Added space between the title and the board
        Board(rows = rows, cols = cols, onDiscDropped = { col: Int ->
            println("Disc dropped in column $col")
            val row = Utils.getNextEmptyRow(board, col, rows)
            if (row != -1) {
                board[row][col] = 1 // Assuming it is player 1
            }
        }) { boardBounds: Rect -> // Changed the cols number to be more adequate with the game
            // Example usage of Disc within Board (remove later)
            val padding = 5.dp
            with(LocalDensity.current) {
                val paddingPx = padding.toPx()
                Disc(Color.Red, boardBounds, isDragging = true, col = 0, board = board, rows = rows, padding = paddingPx) { row, column ->
                    board[row][column] = 1 // Now disc is placed
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Connect4AppPreview() {
    Connect4App()
}