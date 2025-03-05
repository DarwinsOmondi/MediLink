package com.example.medilinkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medilinkapp.screens.DoctorSignUpScreen
import com.example.medilinkapp.screens.IllustrationScreen1
import com.example.medilinkapp.screens.IllustrationScreen2
import com.example.medilinkapp.screens.PatientSignUpScreen
import com.example.medilinkapp.screens.SignInScreen
import com.example.medilinkapp.screens.WelcomeScreen
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
                SupaBaseApp(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
fun SupaBaseApp( modifier: Modifier){
    val navController =  rememberNavController()
    val supabaseViewModel = SupabaseViewModel()
    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("signin") {
            SignInScreen(
                onSignInSuccess = {
                    navController.navigate("signup")
                },
                onNavigateToSignUp = {
                    navController.navigate("signup")
                }
            )
        }
        composable("signup") {
            PatientSignUpScreen(
                onSignUpSuccess = {
                    navController.navigate("signin")
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

    }
}