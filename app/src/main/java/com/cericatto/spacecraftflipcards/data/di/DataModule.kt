package com.cericatto.spacecraftflipcards.data.di

import android.app.Application
import android.content.Context
import com.cericatto.spacecraftflipcards.data.remote.OpenNotifyApi
import com.cericatto.spacecraftflipcards.data.remote.OpenNotifyApi.Companion.BASE_URL
import com.cericatto.spacecraftflipcards.data.repository.OpenNotifyRepositoryImpl
import com.cericatto.spacecraftflipcards.domain.repository.OpenNotifyRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

	@Provides
	@Singleton
	fun provideContext(
		app: Application
	): Context {
		return app.applicationContext
	}

	@Provides
	@Singleton
	fun provideOkHttpClient(): OkHttpClient {
		val loggingInterceptor = HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY
		}

		return OkHttpClient.Builder()
			.addInterceptor(
				HttpLoggingInterceptor().apply {
					level = HttpLoggingInterceptor.Level.BODY
				}
			)
			.addInterceptor(loggingInterceptor)
			.build()
	}

	@Provides
	@Singleton
	fun provideApi(
		client: OkHttpClient,
		moshi: Moshi
	): OpenNotifyApi {
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.client(client)
			.build()
			.create()
	}

	@Provides
	@Singleton
	fun provideRepository(
		api: OpenNotifyApi
	): OpenNotifyRepository {
		return OpenNotifyRepositoryImpl(
			api = api
		)
	}

	@Provides
	@Singleton
	fun provideMoshi(): Moshi {
		return Moshi.Builder()
			.add(KotlinJsonAdapterFactory())
			.build()
	}
}