package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.models.Track

interface TrackSearchInteractor {
    suspend fun getAllTracks(): List<Track>
}
