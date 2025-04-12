package com.cericatto.spacecraftflipcards.data.repository

import com.cericatto.spacecraftflipcards.data.model.CraftName
import com.cericatto.spacecraftflipcards.data.remote.OpenNotifyApi
import com.cericatto.spacecraftflipcards.domain.errors.DataError
import com.cericatto.spacecraftflipcards.domain.errors.Result
import com.cericatto.spacecraftflipcards.domain.errors.checkHttpException
import com.cericatto.spacecraftflipcards.domain.repository.OpenNotifyRepository
import retrofit2.HttpException
import java.io.IOException

class OpenNotifyRepositoryImpl(
	private val api: OpenNotifyApi
) : OpenNotifyRepository {

	override suspend fun fetchData(): Result<List<CraftName>, DataError> {
		return try {
			val response = api.fetchData()
			if (response.people.isNotEmpty()) {
				Result.Success(data = response.people)
			} else {
				Result.Success(data = emptyList())
			}
		} catch (e: HttpException) {
			checkHttpException(e.code())
		} catch (e: IOException) {
			Result.Error(DataError.Network.NO_INTERNET)
		}
	}
}