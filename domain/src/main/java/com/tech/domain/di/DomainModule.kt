package com.tech.domain.di

import com.tech.data.remote.dataSource.DiscogsRemoteDataSource
import com.tech.domain.repository.DiscogsRepository
import com.tech.domain.repository.DiscogsRepositoryImpl
import com.tech.domain.useCase.getAlbumsByArtist.GetAlbumsByArtistUseCase
import com.tech.domain.useCase.getArtistInfo.GetArtistInfoUseCase
import com.tech.domain.useCase.searchArtist.SearchArtistUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module responsible for providing domain-layer dependencies.
 *
 * This module exposes repository implementations and use cases as
 * singleton instances, enabling dependency injection across the app
 * while keeping layers decoupled following Clean Architecture.
 */
@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    /** Provides the Discogs repository implementation. */
    @Provides
    @Singleton
    fun provideDiscogsRepository(
        discogsRemoteDataSource: DiscogsRemoteDataSource,
    ): DiscogsRepository =
        DiscogsRepositoryImpl(discogsRemoteDataSource)

    /** Provides the use case for searching artists. */
    @Provides
    @Singleton
    fun provideSearchArtistUseCase(
        discogsRepository: DiscogsRepository,
    ): SearchArtistUseCase =
        SearchArtistUseCase(discogsRepository)

    /** Provides the use case for retrieving artist details. */
    @Provides
    @Singleton
    fun provideGetArtistInfoUseCase(
        discogsRepository: DiscogsRepository,
    ): GetArtistInfoUseCase =
        GetArtistInfoUseCase(discogsRepository)

    /** Provides the use case for retrieving albums by artist. */
    @Provides
    @Singleton
    fun provideGetAlbumsByArtistUseCase(
        discogsRepository: DiscogsRepository,
    ): GetAlbumsByArtistUseCase =
        GetAlbumsByArtistUseCase(discogsRepository)
}

