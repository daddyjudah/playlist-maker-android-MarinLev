package com.practicum.playlistmaker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.practicum.playlistmaker.ui.models.TrackScreenState
import com.practicum.playlistmaker.ui.view_model.TrackViewModel

@Composable
fun TrackScreen(viewModel: TrackViewModel) {
    val screenState by viewModel.trackScreenState.collectAsState()

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        when (val state = screenState) {
            is TrackScreenState.Content -> {
                TrackScreenContent(viewModel, state)
            }
            is TrackScreenState.Loading -> {
                CircularProgressIndicator(color = Color(0xFF3772E7))
            }
        }
    }
}

@Composable
fun TrackScreenContent(viewModel: TrackViewModel, state: TrackScreenState.Content) {
    val playerStatus by viewModel.playerStatusState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = state.trackModel.trackName,
            contentDescription = null,
            modifier = Modifier.size(240.dp).padding(bottom = 24.dp)
        )
        Text(text = state.trackModel.trackName, style = MaterialTheme.typography.headlineMedium)
        Text(text = state.trackModel.artistName, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)

        Spacer(modifier = Modifier.height(40.dp))

        val icon = if (playerStatus.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow
        
        Button(
            onClick = { if (playerStatus.isPlaying) viewModel.pause() else viewModel.play() },
            modifier = Modifier.size(64.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Icon(imageVector = icon, contentDescription = null)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Slider(
            value = playerStatus.progress,
            onValueChange = { },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
