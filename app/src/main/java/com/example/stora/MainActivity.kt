package com.example.stora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.stora.ui.theme.StoraBlueDark
import com.example.stora.ui.theme.StoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Mengatur status bar agar transparan dan warnanya sesuai
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT, // Transparan
                android.graphics.Color.TRANSPARENT
            )
        )

        super.onCreate(savedInstanceState)
        setContent {
            StoraTheme {
                // Atur warna background utama (termasuk status bar)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = StoraBlueDark // Warna latar belakang default
                ) {
                    AuthScreen()
                }
            }
        }
    }
}