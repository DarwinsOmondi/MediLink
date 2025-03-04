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
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medilinkapp.screens.DoctorFormScreen
import com.example.medilinkapp.screens.DoctorSignUpScreen
import com.example.medilinkapp.screens.PatientSignUpScreen
import com.example.medilinkapp.screens.SignInScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
               DoctorFormScreen(Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
fun SupaBaseApp(navController: NavHostController, modifier: Modifier){
    NavHost(
        navController = navController,
        startDestination = "signin"
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
    }
}