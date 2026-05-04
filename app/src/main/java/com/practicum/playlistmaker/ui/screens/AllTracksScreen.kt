package com.practicum.playlistmaker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.models.SearchState
import com.practicum.playlistmaker.ui.view_model.AllTracksViewModel

@Composable
fun AllTracksScreen(
    modifier: Modifier = Modifier,
    viewModel: AllTracksViewModel
) {
    val state by viewModel.allTracksScreenState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val currentState = state) {
            is SearchState.Searching -> {
                CircularProgressIndicator(color = Color(0xFF3772E7))
            }

            is SearchState.Success -> {
                if (currentState.foundList.isEmpty()) {
                    Text(
                        text = stringResource(R.string.nothing_found),
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(currentState.foundList.size) { index ->
                            TrackListItem(track = currentState.foundList[index])
                            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                        }
                    }
                }
            }

            is SearchState.Fail -> {
                Text(
                    text = currentState.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Gray
                )
            }

            is SearchState.Initial -> {
            }
        }
    }
}
