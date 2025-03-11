package com.example.medilinkapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.medilinkapp.SupabaseViewModel
import com.example.medilinkapp.data.model.UserState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(authViewModel: SupabaseViewModel, onNavigateToSignIn: () -> Unit) {
    val userState = authViewModel.userState.value
    val context = LocalContext.current



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text(text = "Hi,")
                        Spacer(modifier = Modifier.width(8.dp))

                        when (userState) {
                            is UserState.Loading -> Text("Loading...")
                            is UserState.Success -> Text(
                                text = userState.message,  // ✅ Use userState.message
                                style = MaterialTheme.typography.titleMedium
                            )
                            is UserState.Error -> Text(
                                text = "Guest",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Button(
                onClick = {
                    authViewModel.logOut(context)
                    onNavigateToSignIn()
                }
            ) {
                Text("Log out")
            }
        }
    }
}
