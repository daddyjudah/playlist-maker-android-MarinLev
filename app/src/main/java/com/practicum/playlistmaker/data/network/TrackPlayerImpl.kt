package com.practicum.playlistmaker.data.network

import android.os.Handler
import android.os.Looper
import com.practicum.playlistmaker.domain.api.TrackPlayer

class TrackPlayerImpl : TrackPlayer {
    private val handler = Handler(Looper.getMainLooper())
    private var isPlaying = false
    private var progress = 0f
    private var currentObserver: TrackPlayer.StatusObserver? = null

    private val updateProgressRunnable = object : Runnable {
        override fun run() {
            if (isPlaying && progress < 1f) {
                progress += 0.05f
                currentObserver?.onProgress(progress)
                handler.postDelayed(this, 500)
            } else if (progress >= 1f) {
                isPlaying = false
                currentObserver?.onStop()
            }
        }
    }

    override fun play(trackId: String, statusObserver: TrackPlayer.StatusObserver) {
        currentObserver = statusObserver
        isPlaying = true
        statusObserver.onPlay()
        handler.post(updateProgressRunnable)
    }

    override fun pause(trackId: String) {
        isPlaying = false
        currentObserver?.onStop()
        handler.removeCallbacks(updateProgressRunnable)
    }

    override fun seek(trackId: String, position: Float) {
        progress = position
        currentObserver?.onProgress(progress)
    }

    override fun release(trackId: String) {
        pause(trackId)
        currentObserver = null
    }
}
