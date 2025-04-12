package com.cericatto.spacecraftflipcards.ui.main_screen

import com.cericatto.spacecraftflipcards.data.model.CraftName
import com.cericatto.spacecraftflipcards.data.model.initCraftNames

data class MainScreenState(
	val loading : Boolean = true,
	var craftNames : List<CraftName> = initCraftNames(),
	var isConnected : Boolean = true,
	val performAnimation : Boolean = false
)