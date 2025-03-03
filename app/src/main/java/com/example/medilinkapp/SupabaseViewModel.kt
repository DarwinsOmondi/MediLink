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
import kotlinx.coroutines.launch

class SupabaseViewModel:ViewModel() {
    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    fun signUp(
        context: Context,
        userEmail: String,
        userPassword: String,
    ){
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.gotrue.signUpWith(Email){
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Registration Successful")
            }catch (e:Exception){
                _userState.value = UserState.Error("Registration Failed : ${e.message}")
            }
        }
    }

    private fun saveToken(context: Context) {
        viewModelScope.launch {
            val accessToken = client.gotrue.currentAccessTokenOrNull()
            val sharedPref = SharedPreferenceHelper(context)
            sharedPref.saveStringData("accessToken", accessToken)
        }
    }

    private fun getToken(context: Context): String?{
        val sharedPref = SharedPreferenceHelper(context)
        return sharedPref.getStringData("accessToken")
    }

    fun logIn(
        context: Context,
        userEmail: String,
        userPassword: String,
    ){
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.gotrue.loginWith(Email){
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Logged in Successful")
            }catch (e:Exception){
                _userState.value = UserState.Error("Log in Failed : ${e.message}")
            }
        }
    }

    fun logOut(){
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.gotrue.logout()
                _userState.value = UserState.Success("Logged out Successful")
            }catch (e:Exception){
                _userState.value = UserState.Error("Log out Failed : ${e.message}")
            }
        }
    }

    fun checkGoogleLoginStatus(context:Context,result: NativeSignInResult) {
        _userState.value = UserState.Loading
        when (result) {
            is NativeSignInResult.Success -> {
                saveToken(context)
                _userState.value = UserState.Success("Logged in with google Google")
            }
            is NativeSignInResult.ClosedByUser -> {}
            is NativeSignInResult.Error -> {
                val message = result.message
                _userState.value = UserState.Error(message)
            }
            is NativeSignInResult.NetworkError -> {
                val message = result.message
                _userState.value = UserState.Error(message)
            }
        }
    }

    fun isUserLoggedIn(
        context: Context
    ){
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                val token = getToken(context)
                if (token.isNullOrEmpty()){
                    _userState.value = UserState.Success("User not logged in")
                }else{
                    client.gotrue.retrieveUser(token)
                    client.gotrue.refreshCurrentSession()
                    saveToken(context)
                    _userState.value = UserState.Success("User logged in")
                }
            }catch (e:Exception){
                _userState.value = UserState.Error("Error checking login status : ${e.message}")
            }
        }
    }

}