package com.practicum.playlistmaker.ui.view_model

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.ui.models.SearchState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class SearchViewModel(
    private val tracksRepository: TracksRepository,
) : ViewModel() {

    private val _searchState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchState = _searchState.asStateFlow()

    fun search(query: String) {
        if (query.isBlank()) {
            _searchState.update { SearchState.Initial }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchState.update { SearchState.Loading }
                val list = tracksRepository.searchTracks(query)
                if (list.isEmpty()) {
                    _searchState.update { SearchState.Error("Ничего не найдено") }
                } else {
                    _searchState.update { SearchState.Success(foundList = list) }
                }
            } catch (e: Exception) {
                _searchState.update { SearchState.Error(e.message ?: "Ошибка") }
            }
        }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(Creator.getTracksRepository())
            }
        }
    }
}
