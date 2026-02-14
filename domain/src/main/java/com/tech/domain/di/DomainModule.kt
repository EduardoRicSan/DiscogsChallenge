package com.tech.domain.di

import com.tech.data.remote.dataSource.DiscogsRemoteDataSource
import com.tech.domain.repository.DiscogsRepository
import com.tech.domain.repository.DiscogsRepositoryImpl
import com.tech.domain.useCase.GetAlbumsByArtistUseCase
import com.tech.domain.useCase.GetArtistInfoUseCase
import com.tech.domain.useCase.SearchArtistUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideDiscogsRepository(
        discogsRemoteDataSource: DiscogsRemoteDataSource,
    ): DiscogsRepository = DiscogsRepositoryImpl(discogsRemoteDataSource)

    @Provides
    @Singleton
    fun provideSearchArtistUseCase(
        discogsRepository: DiscogsRepository,
    ): SearchArtistUseCase = SearchArtistUseCase(discogsRepository)

    @Provides
    @Singleton
    fun provideGetArtistInfoUseCase(
        discogsRepository: DiscogsRepository,
    ): GetArtistInfoUseCase = GetArtistInfoUseCase(discogsRepository)

    @Provides
    @Singleton
    fun provideGetAlbumsByArtistUseCase(
        discogsRepository: DiscogsRepository,
    ): GetAlbumsByArtistUseCase = GetAlbumsByArtistUseCase(discogsRepository)

}
