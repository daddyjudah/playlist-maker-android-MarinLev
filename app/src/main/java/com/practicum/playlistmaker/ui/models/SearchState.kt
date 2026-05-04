package com.practicum.playlistmaker.ui.models

import com.practicum.playlistmaker.domain.models.Track

sealed class SearchState {
    object Initial : SearchState()
    object Searching : SearchState()
    data class Success(val foundList: List<Track>) : SearchState()
    data class Fail(val error: String) : SearchState()
}
