package com.tech.discogschallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tech.design_system.theme.DiscogsChallengeTheme
import com.tech.discogschallenge.presentation.navigation.navHost.DiscogsNavHost
import dagger.hilt.android.AndroidEntryPoint
// Main activity that hosts the Compose UI and navigation graph
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge layout drawing behind system bars
        enableEdgeToEdge()

        setContent {
            // Applies app theme and launches navigation host
            DiscogsChallengeTheme {
                DiscogsNavHost()
            }
        }
    }
}

