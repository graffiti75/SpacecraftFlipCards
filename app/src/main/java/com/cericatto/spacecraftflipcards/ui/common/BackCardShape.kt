package com.cericatto.spacecraftflipcards.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cericatto.spacecraftflipcards.ui.theme.backSideBackground

fun backCardShape(
	color: Color = backSideBackground
): Brush {
	return object : ShaderBrush() {
		override fun createShader(size: Size): Shader {
			val path = createHexagonPath(size)
			val bitmap = ImageBitmap(size.width.toInt(), size.height.toInt())
			val canvas = Canvas(bitmap)
			canvas.drawPath(
				path,
				Paint().apply {
					this.color = color
				}
			)
			return ImageShader(bitmap, TileMode.Clamp)
		}
	}
}

private fun createHexagonPath(size: Size): Path {
	val width = size.width
	val height = size.height
	val path = Path()

	val widthSmall = width / 12
	val widthBig = 11 * widthSmall
	val heightSmall = height / 12
	val heightBig = 11 * heightSmall

	path.moveTo(widthSmall, 0f)
	path.lineTo(width, 0f)
	path.lineTo(width, heightBig)
	path.lineTo(widthBig, height)
	path.lineTo(0f, height)
	path.lineTo(0f, heightSmall)

	path.close()

	return path
}

@Preview
@Composable
private fun BackCardShapePreview() {
	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier
			.width(200.dp)
			.height(300.dp)
			.background(
				backCardShape()
			)
			.padding(10.dp)
	) {}
}