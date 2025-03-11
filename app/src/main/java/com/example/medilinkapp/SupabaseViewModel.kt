package com.example.medilinkapp

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medilinkapp.data.model.UserState
import com.example.medilinkapp.data.network.SupabaseClient.client
import com.example.medilinkapp.utils.SharedPreferenceHelper
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SupabaseViewModel : ViewModel() {
    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    fun signUpPatient(context: Context, userEmail: String, phone: String?, userPassword: String) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.gotrue.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                    data = buildJsonObject {
                        if (!phone.isNullOrBlank()) put("phone", phone)
                    }
                }
                saveToken(context)
                _userState.value = UserState.Success("Registration Successful")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Registration Failed: ${e.message}")
            }
        }
    }

    fun signUpDoctor(
        context: Context,
        userEmail: String,
        phone: String?,
        userPassword: String,
        fullName: String,
        medicalReg: String,
        hospital: String,
        yearsExperience: String?,
        nationalId: String,
        category: String,
        specialization: String,
        country: String
    ) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.gotrue.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                    data = buildJsonObject {
                        put("full_name", fullName)
                        if (!phone.isNullOrBlank()) put("phone", phone)
                        put("medical_registration", medicalReg)
                        put("hospital", hospital)
                        if (!yearsExperience.isNullOrBlank()) put("years_experience", yearsExperience.toIntOrNull() ?: 0)
                        put("national_id", nationalId)
                        put("category", category)
                        put("specialization", specialization)
                        put("country", country)
                    }
                }

                val session = client.gotrue.currentSessionOrNull()
                val userId = session?.user?.id

                if (userId.isNullOrBlank()) {
                    _userState.value = UserState.Error("User not logged in")
                } else {
                    _userState.value = UserState.Success("User ID: $userId")
                }

                // ✅ Fix: Use `postgrest.from("doctors")` instead of `from("doctors")`
                client.postgrest.from("doctors")
                    .insert(
                        listOf( // ✅ Wrap the map inside a list
                            mapOf(
                                "id" to userId,
                                "full_name" to fullName,
                                "email" to userEmail,
                                "phone" to (phone ?: ""),
                                "medical_registration" to medicalReg,
                                "hospital" to hospital,
                                "years_experience" to (yearsExperience?.toIntOrNull() ?: 0),
                                "national_id" to nationalId,
                                "category" to category,
                                "specialization" to specialization,
                                "country" to country
                            )
                        )
                    )
                saveToken(context)
                _userState.value = UserState.Success("User registered successfully!")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Registration Failed: ${e.message}")
            }
        }
    }


    private fun saveToken(context: Context) {
        viewModelScope.launch {
            val accessToken = client.gotrue.currentAccessTokenOrNull()
            if (accessToken != null) {
                val sharedPref = SharedPreferenceHelper(context)
                sharedPref.saveStringData("accessToken", accessToken)
            }
        }
    }

    private fun getToken(context: Context): String? {
        val sharedPref = SharedPreferenceHelper(context)
        return sharedPref.getStringData("accessToken")
    }

    fun logIn(context: Context, userEmail: String, userPassword: String) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.gotrue.loginWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Logged in Successfully")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Log in Failed: ${e.message}")
            }
        }
    }

    fun logOut(context: Context) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.gotrue.logout()
                val sharedPref = SharedPreferenceHelper(context)
                sharedPref.saveStringData("accessToken", null)
                _userState.value = UserState.Success("Logged out Successfully")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Log out Failed: ${e.message}")
            }
        }
    }

    fun checkGoogleLoginStatus(context: Context, result: NativeSignInResult) {
        _userState.value = UserState.Loading
        when (result) {
            is NativeSignInResult.Success -> {
                saveToken(context)
                _userState.value = UserState.Success("Logged in with Google")
            }
            is NativeSignInResult.ClosedByUser -> {
                _userState.value = UserState.Success("Google login closed by user")
            }
            is NativeSignInResult.Error -> {
                _userState.value = UserState.Error("Google login error: ${result.message}")
            }
            is NativeSignInResult.NetworkError -> {
                _userState.value = UserState.Error("Google login network error: ${result.message}")
            }
        }
    }

    fun checkUserLoggedIn(context: Context) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                val token = getToken(context)
                if (token.isNullOrEmpty()) {
                    _userState.value = UserState.Error("User not logged in")
                } else {
                    client.gotrue.refreshCurrentSession()
                    saveToken(context)
                    _userState.value = UserState.Success("User logged in")
                }
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error checking login status: ${e.message}")
            }
        }
    }
}