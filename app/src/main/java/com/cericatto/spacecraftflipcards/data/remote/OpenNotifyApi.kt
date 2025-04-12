package com.cericatto.spacecraftflipcards.data.remote

import com.cericatto.spacecraftflipcards.data.model.Astros
import retrofit2.http.GET

interface OpenNotifyApi {

	companion object {
		const val BASE_URL = "http://api.open-notify.org/"
	}

	@GET("astros.json")
	suspend fun fetchData(): Astros
}