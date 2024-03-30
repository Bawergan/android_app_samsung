package com.example.final_project_samsung

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainTheme { Surface { HomeScreen() } }
        }
    }
}

@Composable
fun HomeScreen() {
    val dates = remember { mutableListOf(Calendar.getInstance().time).toMutableStateList() }
    Column {
        Button(onClick = { dates.add(Calendar.getInstance().time) }) {
            Text(text = "add time stamp")
        }
        Text(text = "dates")
        LazyColumn {
            items(dates) { Text(text = "$it") }
        }
    }
}