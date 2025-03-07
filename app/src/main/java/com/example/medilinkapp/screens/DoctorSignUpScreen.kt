package com.example.medilinkapp.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorSignUpScreen() {
    val userEmail = remember { mutableStateOf("") }
    val fullname = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val medicalRegistrationNumber = remember { mutableStateOf("") }
    val hospital = remember { mutableStateOf("") }
    val yearsOfExperience = remember { mutableStateOf("") }
    val NationalID = remember { mutableStateOf("") }
    val countryCodes = Locale.getISOCountries()
    val countries = countryCodes.map { code -> Locale("", code).displayCountry }
    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf(countries.firstOrNull() ?: "Select Country") }
    var selectedCategory by remember { mutableStateOf<String?>("General & Primary Care") }
    var selectedSpecialization by remember { mutableStateOf<String?>(null) }
    var isCategoryDropdownExpanded by remember { mutableStateOf(false) }
    var isSpecializationDropdownExpanded by remember { mutableStateOf(false) }
    var showCamera by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val userPassword = remember { mutableStateOf("") }
    val context = LocalContext.current
    val togglePasswordVisibility = remember { mutableStateOf(false) }
    val authViewModel: SupabaseViewModel = viewModel()

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF57CACA), Color(0xFFFFFFFF))
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create An Account",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Create your Doctor's account in less than a minute.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF555555)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email Field
            OutlinedTextField(
                value = userEmail.value,
                onValueChange = { userEmail.value = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF57CACA)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fullname.value,
                onValueChange = { fullname.value = it },
                label = { Text("full name") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF57CACA)
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Phone Field
            OutlinedTextField(
                value = phone.value,
                onValueChange = { phone.value = it },
                label = { Text("Phone") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF57CACA)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Medical Registration Number Field
            OutlinedTextField(
                value = medicalRegistrationNumber.value,
                onValueChange = { medicalRegistrationNumber.value = it },
                label = { Text("Medical Registration Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF57CACA)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Hospital Field
            OutlinedTextField(
                value = hospital.value,
                onValueChange = { hospital.value = it },
                label = { Text("Hospital") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF57CACA)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Years of Experience Field
            OutlinedTextField(
                value = yearsOfExperience.value,
                onValueChange = { yearsOfExperience.value = it },
                label = { Text("Years Of Experience") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF57CACA)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // National ID Field
            OutlinedTextField(
                value = NationalID.value,
                onValueChange = { NationalID.value = it },
                label = { Text("National ID") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF57CACA)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Category Dropdown
            selectedCategory?.let {
                OutlinedTextField(
                    value = "$it,$selectedSpecialization",
                    onValueChange = { selectedCategory = it },
                    label = { Text("Select Category") },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF57CACA),
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color(0xFF57CACA)
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = { isCategoryDropdownExpanded = true }
                        ) {
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            DropdownMenu(
                expanded = isCategoryDropdownExpanded,
                onDismissRequest = { isCategoryDropdownExpanded = false },
            ) {
                specializationCategories.keys.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            selectedSpecialization = null
                            isCategoryDropdownExpanded = false
                            isSpecializationDropdownExpanded = true
                        }
                    )
                }
            }

            if (selectedCategory != null) {
                DropdownMenu(
                    expanded = isSpecializationDropdownExpanded,
                    onDismissRequest = { isSpecializationDropdownExpanded = false },
                ) {
                    specializationCategories[selectedCategory]?.forEach { specialization ->
                        DropdownMenuItem(
                            text = { Text(specialization) },
                            onClick = {
                                selectedSpecialization = specialization
                                isSpecializationDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Country Dropdown
            OutlinedTextField(
                value = selectedCountry,
                onValueChange = { selectedCountry = it },
                label = { Text("Select Country") },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF57CACA)
                ),
                trailingIcon = {
                    IconButton(
                        onClick = { expanded = true }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.White)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(10.dp))
            ) {
                countries.forEach { country ->
                    DropdownMenuItem(
                        text = { Text(country) },
                        onClick = {
                            selectedCountry = country
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Password Field
            OutlinedTextField(
                value = userPassword.value,
                onValueChange = { userPassword.value = it },
                label = { Text("Password") },
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (togglePasswordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF57CACA)
                ),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        togglePasswordVisibility.value = !togglePasswordVisibility.value
                    }) {
                        Icon(
                            imageVector = if (togglePasswordVisibility.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Create Account Button
            Button(
                onClick = {
                    if (userEmail.value.isNotBlank()&& phone.value.isNotBlank() && userPassword.value.isNotBlank()&& fullname.value.isNotBlank()&&medicalRegistrationNumber.value.isNotBlank()
                        &&hospital.value.isNotBlank()&&yearsOfExperience.value.isNotBlank()&&NationalID.value.isNotBlank()&&selectedCategory!=null&&selectedSpecialization!=null&&selectedCountry.isNotBlank()) {
                        coroutineScope.launch {
                            try {
                                authViewModel.signUpDoctor(
                                    context,
                                    userEmail.value,
                                    phone.value,
                                    userPassword.value,
                                    fullname.value,
                                    medicalRegistrationNumber.value,
                                    hospital.value,
                                    yearsOfExperience.value,
                                    NationalID.value,
                                    selectedCategory!!,
                                    selectedSpecialization!!,
                                    selectedCountry
                                )
                            }catch (e:Exception){
                                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                                e.printStackTrace()
                            }
                        }

                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF57CACA))
            ) {
                Text("Create An Account", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign In Link
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Already have an account?", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = { /* Navigate to Sign In */ }) {
                    Text("Sign In", color = Color(0xFF57CACA))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorSignUpScreenPreview() {
    DoctorSignUpScreen()
}