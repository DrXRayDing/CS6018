package com.example.marble

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity

@Composable
fun MarbleScreen(viewModel: MarbleViewModel) {
    // Observe the position from the ViewModel
    val position = viewModel.position.observeAsState(initial = Offset.Zero).value

    // Define a box with constraints to limit the ball's movement
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Marble size (e.g., 50dp)
        val ballSize = 50.dp

        // Get the maximum width and height for ball movement
        val maxX = with(LocalDensity.current) { maxWidth.toPx() - ballSize.toPx() }
        val maxY = with(LocalDensity.current) { maxHeight.toPx() - ballSize.toPx() }

        // Constrain the position within screen boundaries
        val constrainedX = position.x.coerceIn(0f, maxX)
        val constrainedY = position.y.coerceIn(0f, maxY)

        // Draw the marble
        Box(
            modifier = Modifier
                .offset { IntOffset(constrainedX.toInt(), constrainedY.toInt()) }
                .size(ballSize)
                .background(Color.Red, shape = CircleShape)
        )
    }
}