package com.practicum.playlistmaker.ui.models

import com.practicum.playlistmaker.domain.models.Track

sealed class SearchState {
    object Initial : SearchState()
    object Loading : SearchState()
    data class Success(val foundList: List<Track>) : SearchState()
    data class Error(val error: String) : SearchState()
}
