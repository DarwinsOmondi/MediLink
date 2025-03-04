package com.example.medilinkapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medilinkapp.SupabaseViewModel
import com.example.medilinkapp.data.model.UserState
import com.example.medilinkapp.data.network.SupabaseClient.client
import io.github.jan.supabase.compose.auth.composable.rememberLoginWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorSignUpScreen(modifier: Modifier) {
    //onSignUpSuccess: () -> Unit, onNavigateSignIn: () -> Unit,onNavigateToDoctorsForm: () -> Unit
    val userEmail = remember { mutableStateOf("") }
    val userPassword = remember { mutableStateOf("") }
    val context = LocalContext.current
    val togglePasswordVisibility = remember { mutableStateOf(false) }
    val authViewModel: SupabaseViewModel = viewModel()
    val action = client.composeAuth.rememberLoginWithGoogle(
        onResult = {result ->
            authViewModel.checkGoogleLoginStatus(context,result)
        },
        fallback ={}
    )

    LaunchedEffect(Unit) {
        authViewModel.isUserLoggedIn(context)
    }

    LaunchedEffect(authViewModel.userState.value) {
        when (val state = authViewModel.userState.value) {
            is UserState.Success -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is UserState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            UserState.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.5f))

            Text(
                text = "Create An Account",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Create your  Doctor's account in less than a minute.",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.weight(0.5f))

            OutlinedTextField(
                value = userEmail.value,
                onValueChange = { userEmail.value = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xff57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xff57CACA)
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = userEmail.value,
                onValueChange = { userEmail.value = it },
                label = { Text("Phone") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xff57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xff57CACA)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = userPassword.value,
                onValueChange = { userPassword.value = it },
                label = { Text("Password") },
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (togglePasswordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xff57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xff57CACA)
                ),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        togglePasswordVisibility.value = !togglePasswordVisibility.value
                    }) {
                        if (togglePasswordVisibility.value){
                            Icon(imageVector = Icons.Filled.Visibility, contentDescription = "Toggle Password Visibility")
                        }else{
                            Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = "Toggle Password Visibility")
                        }
                    }
                }
            )

            Spacer(Modifier.height(32.dp))

            Row {
                Text("To complete registration ", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(top = 13.dp))
                Spacer(Modifier.height(4.dp))
                TextButton(
                    onClick = {
                       // onNavigateToDoctorsForm()
                    }
                ) {
                    Text("Click here", color = Color(0xff57CACA), style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.weight(0.5f))

            Button(
                onClick = {
                    if (userEmail.value.isNotEmpty() && userPassword.value.isNotEmpty()) {
                        authViewModel.signUp(context, userEmail.value, userPassword.value)
                    } else {
                        Toast.makeText(context, "Please enter email ,password and phone number", Toast.LENGTH_SHORT).show()
                    }
                 //   onSignUpSuccess()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xff57CACA)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Create An Account", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(20.dp))

            Spacer(modifier = Modifier.weight(0.5f))
            Row {
                Text("Already have an account ?", modifier = Modifier.padding(top = 13.dp), style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.width(2.dp))
                TextButton(onClick ={}
                    // onNavigateSignIn
                ) {
                    Text("Sign In", color = Color(0xff57CACA),style = MaterialTheme.typography.titleMedium)
                }
            }
            Spacer(modifier = Modifier.weight(0.5f))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorSignUpScreenPreview() {
    DoctorSignUpScreen(modifier = Modifier)
}
