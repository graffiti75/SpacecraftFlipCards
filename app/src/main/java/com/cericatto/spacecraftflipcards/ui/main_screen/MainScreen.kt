package com.cericatto.spacecraftflipcards.ui.main_screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import kotlin.math.PI
import kotlin.math.cos

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
			FlipComposable(
				modifier = modifier,
				onAction = onAction,
				state = state
			)
		}
	}
}

@Composable
private fun FlipComposable(
	modifier: Modifier = Modifier,
	onAction: (MainScreenAction) -> Unit,
	state: MainScreenState
) {
	// Animation state for rotation
	val rotation = remember { Animatable(0f) }

	/*
	val alphaFront = if (rotation.value <= 85f) 1f
	else if (rotation.value >= 95f) 0f
	else 1f - (rotation.value - 85f) / 10f

	val alphaBack = if (rotation.value <= 85f) 0f
	else if (rotation.value >= 95f) 1f
	else (rotation.value - 85f) / 10f
	 */

	val alphaFront = cos(rotation.value * (PI / 180f)).coerceAtLeast(0.0)
	val alphaBack = cos((rotation.value - 180f) * (PI / 180f)).coerceAtLeast(0.0)

	// Launch animation when flip state changes
	LaunchedEffect(state.flip) {
		rotation.animateTo(
			targetValue = if (state.flip) 180f else 0f,
			animationSpec = tween(
				durationMillis = 1500,
				easing = LinearOutSlowInEasing,
//				easing = FastOutSlowInEasing,
//				easing = CubicBezierEasing(0.5f, 0.0f, 1.0f, 0.0f)
			)
		)
	}

	Box(
		contentAlignment = Alignment.Center,
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
			.graphicsLayer {
				// Apply rotation around Y-axis for flip effect.
				rotationY = rotation.value
				// Adjust camera distance for better 3D effect.
				cameraDistance = 12f * density
			}
			.clickable {
				// Trigger flip action.
				onAction(MainScreenAction.FlipCard)
			},
	) {
		if (rotation.value <= 90f) {
			FrontCard(
				modifier = modifier
					.graphicsLayer {
//						alpha = if (rotation.value <= 90f) 1f else 0f
						alpha = alphaFront.toFloat()
						rotationY = 0f // No additional rotation needed
					}
			)
		} else {
			BackCard(
				modifier = modifier
					.graphicsLayer {
//						alpha = if (rotation.value > 90f) 1f else 0f
						alpha = alphaBack.toFloat()
						rotationY = 180f // Corrects orientation when Box is at 180°
					},
				state = state
			)
		}
	}
}

@Composable
private fun FrontCard(
	modifier: Modifier = Modifier
) {
	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
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
	state: MainScreenState
) {
	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
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
				state = state
			)
		}
	}
}

@Composable
private fun CraftList(
	modifier: Modifier = Modifier,
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
	FlipComposable(
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
	FrontCard(
		modifier = Modifier
	)
}

@Preview(showBackground = true)
@Composable
private fun BackCardPreview() {
	BackCard(
		modifier = Modifier,
		state = MainScreenState()
			.copy(
				loading = false,
				craftNames = initCraftNames()
			)
	)
}