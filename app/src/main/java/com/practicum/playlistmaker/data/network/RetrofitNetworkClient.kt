package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.creator.Storage
import com.practicum.playlistmaker.data.dto.BaseResponse
import com.practicum.playlistmaker.data.dto.TracksSearchRequest
import com.practicum.playlistmaker.data.dto.TracksSearchResponse
import com.practicum.playlistmaker.domain.api.NetworkClient

class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {

    override fun doRequest(dto: Any): BaseResponse {
        return if (dto is TracksSearchRequest) {
            val searchList = storage.search(dto.expression)
            TracksSearchResponse(searchList).apply { resultCode = 200 }
        } else {
            BaseResponse().apply { resultCode = 400 }
        }
    }
}
