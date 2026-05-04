package com.practicum.playlistmaker.creator

import com.practicum.playlistmaker.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker.data.network.TrackPlayerImpl
import com.practicum.playlistmaker.data.network.TracksRepositoryImpl
import com.practicum.playlistmaker.domain.api.TrackPlayer
import com.practicum.playlistmaker.domain.api.TracksInteractor
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.domain.models.Track

object Creator {
    private val storage: Storage by lazy { Storage() }

    private val networkClient: RetrofitNetworkClient by lazy {
        RetrofitNetworkClient(storage)
    }

    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(networkClient)
    }

    fun getTrackPlayer(): TrackPlayer {
        return TrackPlayerImpl()
    }

    fun getTracksInteractor(): TracksInteractor {
        return object : TracksInteractor {
            override fun loadTrackData(trackId: String, onComplete: (Track) -> Unit) {
                onComplete(Track("Track", "Artist", "0:00"))
            }
        }
    }

    fun getSettingsInteractor(): String {
        return "Settings Interactor"
    }
}
