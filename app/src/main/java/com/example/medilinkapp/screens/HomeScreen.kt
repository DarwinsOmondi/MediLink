package com.example.medilinkapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController){

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Home",style = MaterialTheme.typography.titleLarge)},
            )
        }
    ){paddingValue ->
        Column(modifier = Modifier.padding(paddingValue)){
            Text("How are you feeling today ?",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(paddingValue)
                    .align(Alignment.Start))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen(navController = NavHostController(LocalContext.current))
}