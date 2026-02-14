package com.tech.data

import app.cash.turbine.test
import com.tech.core.network.NetworkResult
import com.tech.data.remote.api.DiscogsApiService
import com.tech.data.remote.dataSource.DiscogsRemoteDataSource
import com.tech.data.remote.dataSource.DiscogsRemoteDataSourceImpl
import com.tech.data.remote.dto.common.DiscogsPaginationDTO
import com.tech.data.remote.dto.getAlbumsByArtist.GetAlbumsByArtistResponseDTO
import com.tech.data.remote.dto.getAlbumsByArtist.ReleasesDTO
import com.tech.data.remote.dto.getArtistInfo.GetArtistInfoResponseDTO
import com.tech.data.remote.dto.search.DiscogsArtistResponseDTO
import com.tech.data.remote.dto.search.ResultsDTO
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class DiscogsRemoteDataSourceImplTest {

    private val apiService = mockk<DiscogsApiService>()
    private lateinit var remoteDataSource: DiscogsRemoteDataSource

    private val searchArtistDto = DiscogsArtistResponseDTO(
        pagination = DiscogsPaginationDTO(page = 1, pages = 1, perPage = 30, items = 1),
        results = listOf(ResultsDTO(id = 1, title = "Test Artist"))
    )

    private val artistInfoDto = GetArtistInfoResponseDTO(
        name = "Test Artist",
        id = 1,
        resourceUrl = "https://api.discogs.com/artists/1"
    )

    private val albumsDto = GetAlbumsByArtistResponseDTO(
        pagination = DiscogsPaginationDTO(page = 1),
        releases = listOf(ReleasesDTO(id = 101, title = "Test Album", year = 2020))
    )

    @Before
    fun setup() {
        remoteDataSource = DiscogsRemoteDataSourceImpl(apiService)
    }

    // --------- SEARCH ARTIST ---------
    @Test
    fun `searchArtist emits Loading then Success`() = runTest {
        coEvery { apiService.searchArtist("Test", 1) } returns searchArtistDto

        remoteDataSource.searchArtist("Test", 1)
            .test {
                val loading = awaitItem()
                assertTrue(loading is NetworkResult.Loading)

                val success = awaitItem()
                assertTrue(success is NetworkResult.Success)
                assertEquals(searchArtistDto, (success as NetworkResult.Success).data)

                awaitComplete()
            }
    }

    @Test
    fun `searchArtist emits Loading then Error on exception`() = runTest {
        coEvery { apiService.searchArtist("Test", 1) } throws IOException("No internet connection")

        remoteDataSource.searchArtist("Test", 1)
            .test {
                val loading = awaitItem()
                assertTrue(loading is NetworkResult.Loading)

                val error = awaitItem()
                assertTrue(error is NetworkResult.Error)
                assertEquals("No internet connection", (error as NetworkResult.Error).message)

                awaitComplete()
            }
    }

    // --------- GET ARTIST INFO ---------
    @Test
    fun `getArtistInfo emits Loading then Success`() = runTest {
        coEvery { apiService.getArtistInfo(1) } returns artistInfoDto

        remoteDataSource.getArtistInfo(1)
            .test {
                val loading = awaitItem()
                assertTrue(loading is NetworkResult.Loading)

                val success = awaitItem()
                assertTrue(success is NetworkResult.Success)
                assertEquals(artistInfoDto, (success as NetworkResult.Success).data)

                awaitComplete()
            }
    }

    @Test
    fun `getAlbumsByArtist emits Loading then Success`() = runTest {
        coEvery { apiService.getAlbumsByArtist(1, 1) } returns albumsDto

        remoteDataSource.getAlbumsByArtist(1, 1)
            .test {
                val loading = awaitItem()
                assertTrue(loading is NetworkResult.Loading)

                val success = awaitItem()
                assertTrue(success is NetworkResult.Success)
                assertEquals(albumsDto, (success as NetworkResult.Success).data)

                awaitComplete()
            }
    }
}

