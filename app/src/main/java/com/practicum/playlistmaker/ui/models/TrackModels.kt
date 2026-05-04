package com.practicum.playlistmaker.ui.models

import com.practicum.playlistmaker.domain.models.Track

data class TrackUIModel(
    val id: String,
    val name: String,
    val author: String,
    val pictureUrl: String,
)

sealed class TrackScreenState {
    object Loading : TrackScreenState()
    data class Content(val trackModel: Track) : TrackScreenState() // Используем Domain Track для простоты
}

data class PlayerStatus(
    val isPlaying: Boolean,
    val progress: Float
) {
    companion object {
        val Initial = PlayerStatus(isPlaying = false, progress = 0f)
    }
}
