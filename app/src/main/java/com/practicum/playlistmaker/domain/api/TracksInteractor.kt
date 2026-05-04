package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.models.Track

interface TracksInteractor {
    fun loadTrackData(trackId: String, onComplete: (Track) -> Unit)
}
