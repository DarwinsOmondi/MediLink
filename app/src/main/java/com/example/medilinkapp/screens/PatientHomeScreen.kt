package com.example.medilinkapp.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medilinkapp.HealthViewModel

@Composable
fun HomeScreen(viewModel: HealthViewModel, activity: ComponentActivity) {
    val heartRate by viewModel.heartRate
    val steps by viewModel.steps


    // Fetch Google Fit data and location when HomeScreen loads
    LaunchedEffect(Unit) {
        viewModel.startTracking()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "📊 HealthBridge Telehealth App", style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "❤️ Heart Rate: $heartRate BPM")
        Text(text = "👣 Steps Taken: $steps")
        Button(onClick = {
            // Future camera-based heart rate detection
        }) {
            Text("📸 Measure Heart Rate")
        }
    }
}
