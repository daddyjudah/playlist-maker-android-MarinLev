package com.practicum.playlistmaker.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.practicum.playlistmaker.ui.screens.AllTracksScreen
import com.practicum.playlistmaker.ui.view_model.AllTracksViewModel

class AllTracksActivity : ComponentActivity() {
    private val viewModel by viewModels<AllTracksViewModel> {
        AllTracksViewModel.getViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                AllTracksScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel
                )
            }
        }
    }
}
