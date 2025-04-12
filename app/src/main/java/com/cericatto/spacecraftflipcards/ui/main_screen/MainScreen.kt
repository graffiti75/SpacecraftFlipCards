package com.cericatto.spacecraftflipcards.ui.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cericatto.spacecraftflipcards.data.model.initCraftNames

@Composable
fun MainScreenRoot(
	modifier: Modifier = Modifier,
	viewModel: MainScreenViewModel = hiltViewModel()
) {
	val state by viewModel.state.collectAsStateWithLifecycle()
	MainScreen(
		modifier = modifier,
		onAction = viewModel::onAction,
		state = state
	)
}

@Composable
private fun MainScreen(
	modifier: Modifier = Modifier,
	onAction: (MainScreenAction) -> Unit,
	state: MainScreenState
) {
	if (state.loading) {
		Box(
			modifier = Modifier
				.padding(vertical = 20.dp)
				.fillMaxSize(),
			contentAlignment = Alignment.Center
		) {
			CircularProgressIndicator(
				color = MaterialTheme.colorScheme.primary,
				strokeWidth = 4.dp,
				modifier = Modifier.size(64.dp)
			)
		}
	} else {
		if (state.craftNames.isNotEmpty()) {
			MainScreenContent(
				modifier = modifier,
				onAction = onAction,
				state = state
			)
		}
	}
}

@Composable
private fun MainScreenContent(
	modifier: Modifier = Modifier,
	onAction: (MainScreenAction) -> Unit,
	state: MainScreenState
) {
	LazyColumn(
		modifier = modifier.fillMaxSize()
			.background(Color.Red),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		itemsIndexed(state.craftNames) { index, item ->
			Text(
				text = item.name
			)
		}
	}
}

@Composable
private fun FrontCard() {

}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
	MainScreen(
		onAction = {},
		state = MainScreenState().copy(
			loading = false,
			craftNames = initCraftNames()
		)
	)
}

@Preview(showBackground = true)
@Composable
private fun MainContentContentPreview() {
	MainScreenContent(
		onAction = {},
		modifier = Modifier,
		state = MainScreenState()
			.copy(
				loading = false,
				craftNames = initCraftNames()
			)
	)
}