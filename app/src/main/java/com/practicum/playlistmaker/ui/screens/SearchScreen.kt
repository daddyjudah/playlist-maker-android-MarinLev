package com.practicum.playlistmaker.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.models.Track
import com.practicum.playlistmaker.ui.models.SearchState
import com.practicum.playlistmaker.ui.view_model.SearchViewModel

@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
) {
    val screenState by viewModel.searchScreenState.collectAsState()
    var text by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button),
                    tint = Color.Black
                )
            }
            Text(
                text = stringResource(R.string.menu_search),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(start = 12.dp)
            )
        }

        TextField(
            value = text,
            onValueChange = {
                text = it
                viewModel.search(it)
            },
            placeholder = { Text("Поиск", color = Color(0xFFAEAFB4)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color(0xFFAEAFB4)
                )
            },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(onClick = {
                        text = ""
                        viewModel.search("")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color(0xFFAEAFB4)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF0F0F0),
                unfocusedContainerColor = Color(0xFFF0F0F0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(52.dp)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            when (val state = screenState) {
                is SearchState.Initial -> { }

                is SearchState.Searching -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFF3772E7)
                    )
                }

                is SearchState.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.foundList.size) { index ->
                            TrackListItem(track = state.foundList[index])
                        }
                    }
                }

                is SearchState.Fail -> {
                    Text(
                        text = "Ошибка: ${state.error}",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center).padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TrackListItem(track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .clip(RoundedCornerShape(2.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = track.trackName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${track.artistName} • ${track.trackTime}",
                fontSize = 11.sp,
                color = Color(0xFFAEAFB4),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xFFAEAFB4),
            modifier = Modifier.size(24.dp)
        )
    }
}
