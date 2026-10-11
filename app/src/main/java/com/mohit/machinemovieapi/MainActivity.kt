package com.mohit.machinemovieapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mohit.machinemovieapi.navigation.AppNavigation
import com.mohit.machinemovieapi.ui.theme.MachineMovieApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MachineMovieApiTheme {
                AppNavigation()
            }
        }
    }
}
