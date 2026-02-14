package com.tech.domain

import app.cash.turbine.test
import com.tech.core.network.NetworkResult
import com.tech.data.remote.dataSource.DiscogsRemoteDataSource
import com.tech.data.remote.dto.getAlbumsByArtist.GetAlbumsByArtistResponseDTO
import com.tech.data.remote.dto.getAlbumsByArtist.ReleasesDTO
import com.tech.data.remote.dto.getArtistInfo.GetArtistInfoResponseDTO
import com.tech.data.remote.dto.search.DiscogsArtistResponseDTO
import com.tech.data.remote.dto.search.ResultsDTO
import com.tech.domain.repository.DiscogsRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DiscogsRepositoryImplTest {

    private val remoteDataSource: DiscogsRemoteDataSource = mockk()
    private lateinit var repository: DiscogsRepositoryImpl

    @Before
    fun setup() {
        repository = DiscogsRepositoryImpl(remoteDataSource)
    }

    // searchArtist Tests

    @Test
    fun `searchArtist should map single result correctly`() = runTest {
        val dto = DiscogsArtistResponseDTO(
            results = listOf(
                ResultsDTO(id = 1, title = "Artist1", type = "artist", thumb = "thumb1")
            )
        )
        coEvery { remoteDataSource.searchArtist("query", 1) } returns flowOf(NetworkResult.Success(dto))

        val flow = repository.searchArtist("query", 1)

        flow.test {
            val item = awaitItem()
            assert(item is NetworkResult.Success)
            val data = (item as NetworkResult.Success).data
            assertEquals(1, data.size)
            assertEquals("Artist1", data.first().title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchArtist should map multiple results correctly`() = runTest {
        val dto = DiscogsArtistResponseDTO(
            results = listOf(
                ResultsDTO(id = 1, title = "Artist1"),
                ResultsDTO(id = 2, title = "Artist2"),
                ResultsDTO(id = 3, title = "Artist3")
            )
        )
        coEvery { remoteDataSource.searchArtist("query", 1) } returns flowOf(NetworkResult.Success(dto))

        val flow = repository.searchArtist("query", 1)

        flow.test {
            val item = awaitItem()
            assert(item is NetworkResult.Success)
            val data = (item as NetworkResult.Success).data
            assertEquals(3, data.size)
            assertEquals("Artist1", data[0].title)
            assertEquals("Artist2", data[1].title)
            assertEquals("Artist3", data[2].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchArtist with empty results should return empty list`() = runTest {
        val dto = DiscogsArtistResponseDTO(results = emptyList())
        coEvery { remoteDataSource.searchArtist("empty", 1) } returns flowOf(NetworkResult.Success(dto))

        val flow = repository.searchArtist("empty", 1)

        flow.test {
            val item = awaitItem()
            assert(item is NetworkResult.Success)
            val data = (item as NetworkResult.Success).data
            assertTrue(data.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchArtist should propagate Error`() = runTest {
        val errorMessage = "Network failed"
        coEvery {
            remoteDataSource.searchArtist(any(), any())
        } returns flowOf(NetworkResult.Error(message = errorMessage))

        val flow = repository.searchArtist("any", 1)

        flow.test {
            val item = awaitItem()
            assert(item is NetworkResult.Error)
            assertEquals(errorMessage, (item as NetworkResult.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchArtist should propagate Loading`() = runTest {
        coEvery { remoteDataSource.searchArtist(any(), any()) } returns flowOf(NetworkResult.Loading)

        val flow = repository.searchArtist("any", 1)

        flow.test {
            val item = awaitItem()
            assert(item is NetworkResult.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    // getArtistInfo Tests

    @Test
    fun `getArtistInfo should map DTO to domain model correctly`() = runTest {
        val dto = GetArtistInfoResponseDTO(
            id = 10,
            name = "Test Artist",
            images = listOf(),
            members = listOf(),
            urls = listOf(),
            namevariations = listOf()
        )
        coEvery { remoteDataSource.getArtistInfo(10) } returns flowOf(NetworkResult.Success(dto))

        val flow = repository.getArtistInfo(10)

        flow.test {
            val item = awaitItem()
            assert(item is NetworkResult.Success)
            val artist = (item as NetworkResult.Success).data
            assertEquals(10, artist.id)
            assertEquals("Test Artist", artist.name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getArtistInfo should propagate Error with code`() = runTest {
        val errorCode = 404
        val errorMessage = "Not Found"
        coEvery { remoteDataSource.getArtistInfo(999) } returns flowOf(
            NetworkResult.Error(message = errorMessage, code = errorCode)
        )

        val flow = repository.getArtistInfo(999)

        flow.test {
            val item = awaitItem()
            assert(item is NetworkResult.Error)
            val error = item as NetworkResult.Error
            assertEquals(errorCode, error.code)
            assertEquals(errorMessage, error.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getArtistInfo should propagate Loading`() = runTest {
        coEvery { remoteDataSource.getArtistInfo(10) } returns flowOf(NetworkResult.Loading)

        val flow = repository.getArtistInfo(10)

        flow.test {
            val item = awaitItem()
            assert(item is NetworkResult.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    // getAlbumsByArtist Tests

    @Test
    fun `getAlbumsByArtist should map single album correctly`() = runTest {
        val dto = GetAlbumsByArtistResponseDTO(
            releases = listOf(
                ReleasesDTO(id = 100, title = "Album1", artist = "Artist1", year = 2020)
            )
        )
        coEvery { remoteDataSource.getAlbumsByArtist(1, 1) } returns flowOf(NetworkResult.Success(dto))

        val flow = repository.getAlbumsByArtist(1, 1)

        flow.test {
            val item = awaitItem()
            assert(item is NetworkResult.Success)
            val albums = (item as NetworkResult.Success).data
            assertEquals(1, albums.size)
            assertEquals("Album1", albums.first().title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAlbumsByArtist should map multiple albums correctly`() = runTest {
        val dto = GetAlbumsByArtistResponseDTO(
            releases = listOf(
                ReleasesDTO(id = 101, title = "Album1"),
                ReleasesDTO(id = 102, title = "Album2")
            )
        )
        coEvery { remoteDataSource.getAlbumsByArtist(1, 1) } returns flowOf(NetworkResult.Success(dto))

        val flow = repository.getAlbumsByArtist(1, 1)

        flow.test {
            val item = awaitItem()
            assert(item is NetworkResult.Success)
            val albums = (item as NetworkResult.Success).data
            assertEquals(2, albums.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAlbumsByArtist with partial null fields should map correctly`() = runTest {
        val dto = GetAlbumsByArtistResponseDTO(
            releases = listOf(
                ReleasesDTO(id = null, title = null, year = null, artist = null)
            )
        )
        coEvery { remoteDataSource.getAlbumsByArtist(1, 1) } returns flowOf(NetworkResult.Success(dto))

        val flow = repository.getAlbumsByArtist(1, 1)

        flow.test {
            val item = awaitItem()
            assert(item is NetworkResult.Success)
            val album = (item as NetworkResult.Success).data.first()
            assertEquals(0, album.id)
            assertNull(album.title)
            assertNull(album.artist)
            assertNull(album.year)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAlbumsByArtist should propagate Error`() = runTest {
        val errorMessage = "Network error"
        coEvery { remoteDataSource.getAlbumsByArtist(1, 1) } returns flowOf(NetworkResult.Error(message = errorMessage))

        val flow = repository.getAlbumsByArtist(1, 1)

        flow.test {
            val item = awaitItem()
            assert(item is NetworkResult.Error)
            assertEquals(errorMessage, (item as NetworkResult.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAlbumsByArtist should propagate Loading`() = runTest {
        coEvery { remoteDataSource.getAlbumsByArtist(1, 1) } returns flowOf(NetworkResult.Loading)

        val flow = repository.getAlbumsByArtist(1, 1)

        flow.test {
            val item = awaitItem()
            assert(item is NetworkResult.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
