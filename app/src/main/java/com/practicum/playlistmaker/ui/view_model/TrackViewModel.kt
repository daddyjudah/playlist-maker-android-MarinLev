package com.practicum.playlistmaker.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.domain.api.TrackPlayer
import com.practicum.playlistmaker.domain.api.TracksInteractor
import com.practicum.playlistmaker.ui.models.PlayerStatus
import com.practicum.playlistmaker.ui.models.TrackScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrackViewModel(
    private val trackId: String,
    private val tracksInteractor: TracksInteractor,
    private val trackPlayer: TrackPlayer,
) : ViewModel() {

    private val _trackScreenState = MutableStateFlow<TrackScreenState>(TrackScreenState.Loading)
    val trackScreenState: StateFlow<TrackScreenState> = _trackScreenState.asStateFlow()

    private val _playerStatusState = MutableStateFlow(PlayerStatus.Initial)
    val playerStatusState: StateFlow<PlayerStatus> = _playerStatusState.asStateFlow()

    init {
        tracksInteractor.loadTrackData(
            trackId = trackId,
            onComplete = { trackModel ->
                _trackScreenState.value = TrackScreenState.Content(trackModel)
            }
        )
    }

    fun play() {
        trackPlayer.play(
            trackId = trackId,
            statusObserver = object : TrackPlayer.StatusObserver {
                override fun onProgress(progress: Float) {
                    _playerStatusState.value = _playerStatusState.value.copy(progress = progress)
                }

                override fun onStop() {
                    _playerStatusState.value = _playerStatusState.value.copy(isPlaying = false)
                }

                override fun onPlay() {
                    _playerStatusState.value = _playerStatusState.value.copy(isPlaying = true)
                }
            }
        )
    }

    fun pause() {
        trackPlayer.pause(trackId)
        _playerStatusState.value = _playerStatusState.value.copy(isPlaying = false)
    }

    override fun onCleared() {
        trackPlayer.release(trackId)
    }

    companion object {
        fun getViewModelFactory(trackId: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TrackViewModel(
                    trackId = trackId,
                    tracksInteractor = Creator.getTracksInteractor(),
                    trackPlayer = Creator.getTrackPlayer(),
                )
            }
        }
    }
}
