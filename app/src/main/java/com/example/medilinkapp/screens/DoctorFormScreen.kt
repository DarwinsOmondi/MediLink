package com.example.medilinkapp.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.launch
import java.io.File
import java.util.Locale
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorFormScreen(modifier: Modifier = Modifier) {
    val medicalRegistrationNumber = remember { mutableStateOf("") }
    val hospital = remember { mutableStateOf("") }
    val yearsOfExperience = remember { mutableStateOf("") }
    val NationalID = remember { mutableStateOf("") }
    val countryCodes = Locale.getISOCountries()
    val countries = countryCodes.map { code -> Locale("", code).displayCountry }
    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf(countries.firstOrNull() ?: "Select Country") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedSpecialization by remember { mutableStateOf<String?>(null) }
    var isCategoryDropdownExpanded by remember { mutableStateOf(false) }
    var isSpecializationDropdownExpanded by remember { mutableStateOf(false) }
    var showCamera by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Fill the form below to complete the registration",
                modifier = Modifier
                    .padding(innerPadding)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = medicalRegistrationNumber.value,
                onValueChange = { medicalRegistrationNumber.value = it },
                label = { Text("Medical Registration Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                value = hospital.value,
                onValueChange = { hospital.value = it },
                label = { Text("Hospital") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
                value = yearsOfExperience.value,
                onValueChange = { yearsOfExperience.value = it },
                label = { Text("Years Of Experience") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                value = NationalID.value,
                onValueChange = { NationalID.value = it },
                label = { Text("National ID") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xff57CACA),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xff57CACA)
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Specialization Category Dropdown
            Box {
                Button(
                    onClick = { isCategoryDropdownExpanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xff57CACA)),
                ) {
                    Text(
                        text = selectedCategory ?: "Select Specialization Category",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
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
            }

            Spacer(modifier = Modifier.height(16.dp))

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

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color(0xff57CACA)),
            ) {
                Text(selectedCountry,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.White)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(10.dp))
            ) {
                countries.forEach { country ->
                    DropdownMenuItem(
                        text = { Text(country,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black) },
                        onClick = {
                            selectedCountry = country
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (selectedCategory != null && selectedSpecialization != null) {
                Text(
                    text = "Selected: $selectedSpecialization ($selectedCategory)",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            Button(
                onClick = {
                    showCamera = true
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color(0xff57CACA)),
            ) {
                Text(text = "Take a photo of your national id both sides",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black)
            }
            if (showCamera) {
                CameraCard(
                    onImageCaptured = { uri ->
                        imageUri = uri
                        showCamera = false
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Image captured successfully!")
                        }
                    },
                    onClose = { showCamera = false }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    showCamera = true
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color(0xff57CACA)),
            ) {
                Text(text = "Take a selfie",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black)
            }
        }
    }
}

@Composable
fun CameraCard(onImageCaptured: (Uri) -> Unit, onClose: () -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val executor = remember { Executors.newSingleThreadExecutor() }
    val previewView = remember { PreviewView(context) }
    val imageCapture = remember { ImageCapture.Builder().build() }
    var hasCameraPermission by remember { mutableStateOf(false) }

    // Permission handling
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasCameraPermission = granted }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(android.Manifest.permission.CAMERA)
    }

    // If permission is granted, initialize CameraX
    if (hasCameraPermission) {
        LaunchedEffect(Unit) {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = androidx.camera.core.Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageCapture
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(context))
        }
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Close Button
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Camera",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            if (!hasCameraPermission) {
                Text(
                    "Camera permission is required",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Red
                )
            } else {
                // Camera Preview
                AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
            }

            // Capture Button
            Button(
                onClick = {
                    val photoFile = File(context.cacheDir, "captured_image.jpg")
                    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
                    imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                            onImageCaptured(Uri.fromFile(photoFile))
                        }

                        override fun onError(exception: ImageCaptureException) {
                            exception.printStackTrace()
                        }
                    })
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Capture")
            }
        }
    }
}


val specializationCategories = mapOf(
    "General & Primary Care" to listOf("General Practitioner (GP)", "Family Medicine Physician", "Internal Medicine Specialist"),
    "Specialized Medical Fields" to listOf("Cardiologist", "Neurologist", "Pulmonologist", "Endocrinologist"),
    "Surgical Specialties" to listOf("General Surgeon", "Orthopedic Surgeon", "Neurosurgeon"),
    "Mental Health & Well-Being" to listOf("Psychiatrist", "Psychologist", "Addiction Specialist"),
    "Women & Child Health" to listOf("Obstetrician & Gynecologist (OB/GYN)", "Pediatrician", "Neonatologist"),
    "Eye, Skin, and Dental Health" to listOf("Ophthalmologist", "Dermatologist", "Dentist"),
    "Emergency & Critical Care" to listOf("Emergency Medicine Specialist", "Anesthesiologist", "Critical Care Specialist"),
    "Others" to listOf("Urologist", "Allergist/Immunologist", "Infectious Disease Specialist", "Geriatrician"),
    "Alternative Medicine & Therapy" to listOf("Chiropractor", "Physiotherapist", "Dietitian/Nutritionist")
)