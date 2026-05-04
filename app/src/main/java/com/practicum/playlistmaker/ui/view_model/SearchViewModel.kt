package com.practicum.playlistmaker.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.ui.models.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
