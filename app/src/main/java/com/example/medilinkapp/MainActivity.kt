package com.example.medilinkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medilinkapp.screens.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val healthViewModel: HealthViewModel by viewModels()

        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.background
            ) { innerPadding ->
                SupaBaseApp(
                    modifier = Modifier.padding(innerPadding),
                    healthViewModel = healthViewModel
                )
            }
        }
    }
}

@Composable
fun SupaBaseApp(
    modifier: Modifier = Modifier,
    healthViewModel: HealthViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("signin") {
            SignInScreen(
                onSignInSuccess = {
                    navController.navigate("signup") {
                        popUpTo("signin") { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate("signup")
                },
                onNavigateHomeScreen = {
                    navController.navigate("homeScreen") {
                        popUpTo("signin") { inclusive = true }
                    }
                }
            )
        }

        composable("signup") {
            PatientSignUpScreen(
                onSignUpSuccess = {
                    navController.navigate("signin") {
                        popUpTo("signup") { inclusive = true }
                    }
                },
                onNavigateHomeScreen = {
                    navController.navigate("homeScreen") {
                        popUpTo("signup") { inclusive = true }
                    }
                },
                onNavigateSignIn = {
                    navController.navigate("signin")
                }
            )
        }

        composable("doctorsignup") {
            DoctorSignUpScreen()
        }

        composable("welcome") {
            WelcomeScreen(
                onNavigateToIllustration1 = {
                    navController.navigate("illustration1")
                }
            )
        }

        composable("illustration1") {
            IllustrationScreen1(
                onNavigateToIllustration2 = {
                    navController.navigate("illustration2")
                }
            )
        }

        composable("illustration2") {
            IllustrationScreen2(
                navController = navController,
                onNavigateToPatientSignUp = { navController.navigate("signup") },
                onNavigateToDoctorSignUp = { navController.navigate("doctorsignup") }
            )
        }

        composable("homeScreen") {
            HomeScreen(viewModel = healthViewModel, activity = (navController.context as ComponentActivity))
        }
    }
}
