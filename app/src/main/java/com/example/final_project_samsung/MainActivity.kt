package com.example.final_project_samsung

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainTheme { Surface { HomeScreen() } }
        }
    }
}


@Preview
@Composable
fun HomeScreen() {
    val dates = remember { mutableListOf(EventData()).toMutableStateList() }
    Scaffold(
        floatingActionButton = {
            LargeFloatingActionButton(onClick = { dates.add(EventData()) }) {
                Icon(
                    Icons.Filled.Add,
                    "add time stamp"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LazyColumn {
                items(dates) { Text(text = "${it.name}, ${it.endTime}", Modifier.clickable { it.endTime = Calendar.getInstance().time}) }
            }
        }
    }
}

class EventData {
    var name: String = "NoName"
    var startTime: Date = Calendar.getInstance().time
    var endTime: Date = Calendar.getInstance().time
}