package com.cericatto.spacecraftflipcards.ui.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cericatto.spacecraftflipcards.R
import com.cericatto.spacecraftflipcards.data.model.initCraftNames
import com.cericatto.spacecraftflipcards.ui.common.backCardShape
import com.cericatto.spacecraftflipcards.ui.common.frontCardShape
import com.cericatto.spacecraftflipcards.ui.common.getWidthSize
import com.cericatto.spacecraftflipcards.ui.theme.backSideBackground
import com.cericatto.spacecraftflipcards.ui.theme.backSideText
import com.cericatto.spacecraftflipcards.ui.theme.chivoMonoFont
import com.cericatto.spacecraftflipcards.ui.theme.darkContours
import com.cericatto.spacecraftflipcards.ui.theme.frontSideBackgroundFirst
import com.cericatto.spacecraftflipcards.ui.theme.frontSideBackgroundLast
import com.cericatto.spacecraftflipcards.ui.theme.frontSidePrimaryText
import com.cericatto.spacecraftflipcards.ui.theme.frontSideSecondaryText
import com.cericatto.spacecraftflipcards.ui.theme.lightContours

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
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = Modifier
			.fillMaxSize()
			.background(
				brush = Brush.verticalGradient(
					colors = listOf(
						frontSideBackgroundFirst,
						frontSideBackgroundLast
					)
				)
			)
	) {
		if (state.performAnimation) {
			BackCard(
				onAction = onAction,
				modifier = modifier,
				state = state
			)
		} else {
			FrontCard()
		}
	}
}

@Composable
private fun FrontCard() {
	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier
			.clipToBounds()
			.background(
				frontCardShape()
			)
			.padding(10.dp)
	) {
		ArrowIcon()
		Image(
			painter = painterResource(R.drawable.border),
			contentDescription = "Border",
			colorFilter = ColorFilter.tint(darkContours)
		)
		Image(
			painter = painterResource(R.drawable.rocket),
			contentDescription = "Rocket",
			colorFilter = ColorFilter.tint(darkContours),
			modifier = Modifier.align(Alignment.BottomEnd)
		)
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center,
		) {
			Text(
				text = "ISS Spacecraft",
				style = TextStyle(
					color = frontSidePrimaryText,
					fontSize = 28.sp,
					fontFamily = chivoMonoFont
				)
			)
			Text(
				text = "12 crew members",
				style = TextStyle(
					color = frontSideSecondaryText,
					fontSize = 16.sp,
					fontFamily = chivoMonoFont
				)
			)
		}
	}
}

@Composable
private fun BackCard(
	modifier: Modifier = Modifier,
	onAction: (MainScreenAction) -> Unit,
	state: MainScreenState
) {
	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier
			.clipToBounds()
			.background(
				backCardShape()
			)
			.padding(10.dp)
	) {
		ArrowIcon(
			alignment = Alignment.TopEnd,
			tintColor = lightContours
		)
		Image(
			painter = painterResource(R.drawable.border),
			contentDescription = "Border",
			colorFilter = ColorFilter.tint(lightContours),
			modifier = Modifier
				.graphicsLayer {
					scaleX = -1f
				}
		)
		Image(
			painter = painterResource(R.drawable.rocket),
			contentDescription = "Rocket",
			colorFilter = ColorFilter.tint(lightContours),
			modifier = Modifier
				.align(Alignment.BottomStart)
				.graphicsLayer {
					scaleX = -1f
				}
		)
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center,
		) {
			CraftList(
				modifier = Modifier,
				onAction = onAction,
				state = state
			)
		}
	}
}

@Composable
private fun CraftList(
	modifier: Modifier = Modifier,
	onAction: (MainScreenAction) -> Unit,
	state: MainScreenState
) {
	LazyColumn(
		horizontalAlignment = Alignment.Start,
		verticalArrangement = Arrangement.Center,
		modifier = modifier
			.wrapContentHeight()
	) {
		itemsIndexed(state.craftNames) { index, item ->
			Text(
				text = "${index + 1}. ${item.name}",
				style = TextStyle(
					color = backSideText,
					fontSize = 16.sp,
					fontFamily = chivoMonoFont,
					textAlign = TextAlign.Start
				)
			)
		}
	}
}

@Composable
private fun BoxScope.ArrowIcon(
	alignment: Alignment = Alignment.TopStart,
	tintColor: Color = darkContours
) {
	Icon(
		painter = painterResource(R.drawable.arrow),
		contentDescription = "Arrow",
		tint = tintColor,
		modifier = Modifier
			.padding(5.dp)
			.size(24.dp)
			.align(alignment)
	)
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
private fun MainScreenContentPreview() {
	MainScreenContent(
		onAction = {},
		state = MainScreenState().copy(
			loading = false,
			craftNames = initCraftNames()
		)
	)
}

@Preview(showBackground = true)
@Composable
private fun FrontCardPreview() {
	FrontCard()
}

@Preview(showBackground = true)
@Composable
private fun BackCardPreview() {
	BackCard(
		onAction = {},
		modifier = Modifier,
		state = MainScreenState()
			.copy(
				loading = false,
				craftNames = initCraftNames()
			)
	)
}