package com.tech.data.remote.api

import com.tech.data.remote.dto.DiscogsArtistResponseDTO

interface DiscogsApiService {
    suspend fun searchArtist(
        query: String,
        page: Int,
    ): DiscogsArtistResponseDTO
}
