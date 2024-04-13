package com.example.final_project_samsung

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.final_project_samsung.ui.TheApp
import com.example.final_project_samsung.ui.theme.MainTheme


class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
                TheApp(widthSizeClass = widthSizeClass)
            }
        }
    }
}