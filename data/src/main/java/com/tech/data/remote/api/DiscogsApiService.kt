package com.tech.data.remote.api

import com.tech.data.remote.dto.getAlbumsByArtist.GetAlbumsByArtistResponseDTO
import com.tech.data.remote.dto.getArtistInfo.GetArtistInfoResponseDTO
import com.tech.data.remote.dto.search.DiscogsArtistResponseDTO

interface DiscogsApiService {
    suspend fun searchArtist(
        query: String,
        page: Int,
    ): DiscogsArtistResponseDTO

    suspend fun getArtistInfo(
        artistId: Int,
    ): GetArtistInfoResponseDTO

    suspend fun getAlbumsByArtist(
        artistId: Int,
        page: Int,
    ): GetAlbumsByArtistResponseDTO

}
