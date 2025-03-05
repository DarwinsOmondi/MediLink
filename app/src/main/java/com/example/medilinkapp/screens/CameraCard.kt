package com.example.medilinkapp.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import java.io.File
import java.util.concurrent.Executors

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