package com.example.medilinkapp

import android.content.Context
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medilinkapp.screens.*


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val healthViewModel: HealthViewModel by viewModels()
        val supabaseViewModel: SupabaseViewModel by viewModels()



        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.background
            ) { innerPadding ->
                LaunchedEffect(Unit) {
                    supabaseViewModel.checkUserLoggedIn(this@MainActivity)
                }
                SuparBaseApp(
                    modifier = Modifier.padding(innerPadding),
                    healthViewModel = healthViewModel,
                    supabaseViewModel = supabaseViewModel
                )
            }
        }
    }
}

@Composable
fun SuparBaseApp(
    modifier: Modifier = Modifier,
    healthViewModel: HealthViewModel,
    supabaseViewModel: SupabaseViewModel
) {
    val navController = rememberNavController()


    val startDestination = "signin"

    NavHost(
        navController = navController,
        startDestination = startDestination
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
            HomeScreen(supabaseViewModel, onNavigateToSignIn = {
                navController.navigate("signin")
            })
        }
    }
}