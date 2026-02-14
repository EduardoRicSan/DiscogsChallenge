package com.tech.design_system.di

import android.content.Context
import android.util.Log
import coil3.ImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.svg.SvgDecoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DesignSystemModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor { message ->
            Log.d("CoilHttpDebug", message) // Logs de cada request/response
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val debugInterceptor = Interceptor { chain ->
            val request = chain.request()
            Log.d("CoilHttpDebug", "Request URL: ${request.url}")
            Log.d("CoilHttpDebug", "Request headers: ${request.headers}")

            val response = chain.proceed(request)

            Log.d("CoilHttpDebug", "Response code: ${response.code}")
            Log.d("CoilHttpDebug", "Response message: ${response.message}")
            response
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(debugInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
                add(OkHttpNetworkFetcherFactory(okHttpClient))
            }
            .build()
    }
}
