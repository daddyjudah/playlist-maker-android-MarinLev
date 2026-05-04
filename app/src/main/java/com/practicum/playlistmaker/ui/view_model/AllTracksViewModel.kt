package com.practicum.playlistmaker.ui.view_model

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.ui.models.SearchState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException

class AllTracksViewModel(
    private val tracksRepository: TracksRepository,
) : ViewModel() {

    private val _allTracksScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val allTracksScreenState = _allTracksScreenState.asStateFlow()

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allTracksScreenState.update { SearchState.Loading }
                val list = tracksRepository.searchTracks("")
                _allTracksScreenState.update { SearchState.Success(foundList = list) }
            } catch (e: IOException) {
                _allTracksScreenState.update { SearchState.Error(e.message.toString()) }
            }
        }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AllTracksViewModel(Creator.getTracksRepository())
            }
        }
    }
}
