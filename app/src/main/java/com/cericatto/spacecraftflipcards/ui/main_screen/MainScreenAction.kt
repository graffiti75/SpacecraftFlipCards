package com.cericatto.spacecraftflipcards.ui.main_screen

sealed interface MainScreenAction {
	data object FlipCard : MainScreenAction
}