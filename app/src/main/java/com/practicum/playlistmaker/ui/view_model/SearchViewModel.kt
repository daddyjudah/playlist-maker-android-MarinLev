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

class SearchViewModel(
    private val tracksRepository: TracksRepository,
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    fun search(whatSearch: String) {
        if (whatSearch.isBlank()) {
            _searchScreenState.update { SearchState.Initial }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                val list = tracksRepository.searchTracks(whatSearch)
                if (list.isEmpty()) {
                    _searchScreenState.update { SearchState.Fail("Ничего не найдено") }
                } else {
                    _searchScreenState.update { SearchState.Success(foundList = list) }
                }
            } catch (e: IOException) {
                _searchScreenState.update { SearchState.Fail(e.message ?: "Ошибка") }
            } catch (e: Exception) {
                _searchScreenState.update { SearchState.Fail(e.message ?: "Неизвестная ошибка") }
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
