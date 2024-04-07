package com.example.final_project_samsung

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.final_project_samsung.ui.TheApp
import com.example.final_project_samsung.ui.theme.MainTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TheApp()
                }
            }
        }
    }
}