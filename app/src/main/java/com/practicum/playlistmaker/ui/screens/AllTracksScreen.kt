package com.practicum.playlistmaker.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            is SearchState.Loading -> {
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

            is SearchState.Error -> {
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
