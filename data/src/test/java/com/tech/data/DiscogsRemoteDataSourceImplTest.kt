package com.tech.data

import app.cash.turbine.test
import com.tech.core.network.NetworkResult
import com.tech.data.remote.api.DiscogsApiService
import com.tech.data.remote.dataSource.DiscogsRemoteDataSource
import com.tech.data.remote.dataSource.DiscogsRemoteDataSourceImpl
import com.tech.data.remote.dto.DiscogsArtistResponseDTO
import com.tech.data.remote.dto.DiscogsPaginationDTO
import com.tech.data.remote.dto.ResultsDTO
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

    private val testDto = DiscogsArtistResponseDTO(
        pagination = DiscogsPaginationDTO(page = 1, pages = 1, perPage = 30, items = 1),
        results = listOf(ResultsDTO(id = 1, title = "Test Artist"))
    )

    @Before
    fun setup() {
        remoteDataSource = DiscogsRemoteDataSourceImpl(apiService)
    }

    @Test
    fun `searchArtist emits Loading then Success`() = runTest {
        coEvery { apiService.searchArtist("Test", 1) } returns testDto

        remoteDataSource.searchArtist("Test", 1)
            .test {  // Turbine test
                val loading = awaitItem()
                assertTrue(loading is NetworkResult.Loading)

                val success = awaitItem()
                assertTrue(success is NetworkResult.Success)
                assertEquals(testDto, (success as NetworkResult.Success).data)

                awaitComplete() // aseguramos que el flujo termina
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
}

