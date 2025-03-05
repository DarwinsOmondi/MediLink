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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medilinkapp.R
import kotlinx.coroutines.delay

@Composable
fun IllustrationScreen1(onNavigateToIllustration2: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(5000)
        onNavigateToIllustration2()
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF57CACA), Color(0xFFFFFFFF))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.videocallillustraion),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(32.dp))
            Box(Modifier.padding(16.dp)) {
                Text(
                    text = "Consult Doctors Anytime, Anywhere!",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
            }
                Spacer(modifier = Modifier.height(16.dp))
            Box(Modifier.padding(16.dp)) {
                Text(
                    text = "Book free consultations with certified doctors and mental health professionals.",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF555555)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IllustrationScreen1Preview() {
    IllustrationScreen1 {}
}