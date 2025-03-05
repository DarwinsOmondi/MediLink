package com.example.medilinkapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.medilinkapp.R

@Composable
fun IllustrationScreen2(
    navController: NavHostController,
    onNavigateToPatientSignUp: () -> Unit,
    onNavigateToDoctorSignUp: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF57CACA), Color(0xFFFFFFFF))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.emeragencyimage),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    Box(Modifier.padding(16.dp)) {
                        Text(
                            text = "Get Help When You Need It Most!",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(Modifier.padding(16.dp)) {
                        Text(
                            text = "Quickly access emergency medical guidance and urgent contacts.",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF555555)
                        )
                    }

                    Text(
                        text = "Continue as:",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF333333)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { onNavigateToDoctorSignUp() },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFF57CACA))
                        ) {
                            Text(
                                text = "Doctor",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Black)
                        }

                        Button(
                            onClick = { onNavigateToPatientSignUp() },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFF57CACA))
                        ) {
                            Text(
                                text = "Patient",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Black)
                        }
                    }
                }
    }
}

@Preview(showBackground = true)
@Composable
fun IllustrationScreen2Preview() {
    IllustrationScreen2(
        navController = NavHostController(LocalContext.current),
        onNavigateToPatientSignUp = {},
        onNavigateToDoctorSignUp = {}
    )
}