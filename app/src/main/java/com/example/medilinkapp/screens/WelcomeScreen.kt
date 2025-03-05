package com.example.medilinkapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medilinkapp.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(onNavigateToIllustration1: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(5000)
        onNavigateToIllustration1()
    }

    Box(modifier = Modifier
            .fillMaxSize())
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFFD6D6)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.onboardimage),
                        contentDescription = "Welcome Image",
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "MediLink",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333),
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Your Health, Anytime, Anywhere.",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF555555)
                    )
                }
            }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen {}
}