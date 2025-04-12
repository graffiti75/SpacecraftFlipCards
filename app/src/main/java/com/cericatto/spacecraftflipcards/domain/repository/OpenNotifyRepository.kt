package com.cericatto.spacecraftflipcards.domain.repository

import com.cericatto.spacecraftflipcards.data.model.CraftName
import com.cericatto.spacecraftflipcards.domain.errors.DataError
import com.cericatto.spacecraftflipcards.domain.errors.Result

interface OpenNotifyRepository {
	suspend fun fetchData(): Result<List<CraftName>, DataError>
}