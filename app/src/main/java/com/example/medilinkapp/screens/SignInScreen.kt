package com.example.medilinkapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(onSignInSuccess: () -> Unit, onNavigateToSignUp: () -> Unit) {
    val userEmail = remember { mutableStateOf("") }
    val userPassword = remember { mutableStateOf("") }
    val context = LocalContext.current
    val togglePasswordVisibility = remember { mutableStateOf(false) }
    val authViewModel: SupabaseViewModel = viewModel()
    val userState by authViewModel.userState

    LaunchedEffect(userState) {
        when (val state = userState) {
            is UserState.Success -> Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            is UserState.Error -> Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            UserState.Loading -> Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.5f))

            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "We are happy to see you back. Enter your Email and Password",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.weight(0.5f))

            OutlinedTextField(
                value = userEmail.value,
                onValueChange = { userEmail.value = it },
                label = { Text("Email",style = MaterialTheme.typography.bodyLarge) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xff57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xff57CACA),
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = userPassword.value,
                onValueChange = { userPassword.value = it },
                label = { Text("Password",style = MaterialTheme.typography.bodyLarge) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(10.dp),
                visualTransformation = if (togglePasswordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xff57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xff57CACA),
                ),
                trailingIcon = {
                    IconButton(onClick = { togglePasswordVisibility.value = !togglePasswordVisibility.value }) {
                        if (togglePasswordVisibility.value){
                            Icon(imageVector = Icons.Filled.Visibility, contentDescription = "Toggle Password Visibility")
                        }else{
                            Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = "Toggle Password Visibility")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.weight(0.5f))

            Button(
                onClick = {
                    if (userEmail.value.isNotEmpty() && userPassword.value.isNotEmpty()) {
                        authViewModel.logIn(context, userEmail.value, userPassword.value)
                        onSignInSuccess()
                    } else {
                        Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xff57CACA)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Log into Account", style = MaterialTheme.typography.titleMedium, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color.Black)
                Text(" Or ", color = Color.Black, modifier = Modifier.padding(horizontal = 10.dp))
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color.Black)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (userEmail.value.isNotEmpty() && userPassword.value.isNotEmpty()) {
                        Toast.makeText(context, "Sign in successful", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Black),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Sign in with Google", style = MaterialTheme.typography.titleMedium, color = Color.White)
            }

            Spacer(modifier = Modifier.weight(0.5f))

            Row {
                Text("Don’t have an account ?", style = MaterialTheme.typography.titleMedium, color = Color.Black, modifier = Modifier.padding(top = 13.dp))
                Spacer(modifier = Modifier.width(2.dp))
                TextButton(onClick = onNavigateToSignUp) {
                    Text("Sign Up", style = MaterialTheme.typography.titleMedium, color = Color(0xff57CACA))
                }
            }

            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(onSignInSuccess = {}, onNavigateToSignUp = {})
}