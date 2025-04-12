package com.cericatto.spacecraftflipcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.cericatto.spacecraftflipcards.ui.main_screen.MainScreenRoot
import com.cericatto.spacecraftflipcards.ui.theme.SpacecraftFlipCardsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			SpacecraftFlipCardsTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					MainScreenRoot(modifier = Modifier.padding(innerPadding))
				}
			}
		}
	}
}