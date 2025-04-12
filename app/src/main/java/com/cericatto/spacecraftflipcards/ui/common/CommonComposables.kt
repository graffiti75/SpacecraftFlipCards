package com.cericatto.spacecraftflipcards.ui.common

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun getWidthSize(): Dp {
	val configuration = LocalConfiguration.current
	return if (isLandscapeOrientation()) {
		configuration.screenHeightDp.dp
	} else {
		configuration.screenWidthDp.dp
	}
}

@Composable
fun isLandscapeOrientation(): Boolean {
	val context = LocalContext.current
	val orientation = context.resources.configuration.orientation
	return when (orientation) {
		Configuration.ORIENTATION_LANDSCAPE -> true
		else -> false
	}
}