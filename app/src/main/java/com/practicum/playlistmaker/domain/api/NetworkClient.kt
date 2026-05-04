package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.data.dto.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}
