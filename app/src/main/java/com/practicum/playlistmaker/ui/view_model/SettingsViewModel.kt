package com.practicum.playlistmaker.ui.view_model

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.creator.Creator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class SettingsViewModel(
    private val settingsInteractor: String 
) : ViewModel() {

    private val _loadingStateFlow = MutableStateFlow(true)
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1500)
            _loadingStateFlow.value = false
        }
    }

    fun getLoadingStateFlow(): StateFlow<Boolean> = _loadingStateFlow.asStateFlow()

    fun onThemeChanged(isChecked: Boolean) {
        _isDarkTheme.value = isChecked
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(settingsInteractor = Creator.getSettingsInteractor())
            }
        }
    }
}
