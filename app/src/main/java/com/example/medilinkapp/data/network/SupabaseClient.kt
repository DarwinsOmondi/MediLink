package com.example.medilinkapp.data.network

import com.example.medilinkapp.data.constants.SupabasePro
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = SupabasePro.supabaseUrl,
        supabaseKey = SupabasePro.supabaseKey
    ){
        install(GoTrue)
        install(ComposeAuth){
            googleNativeLogin(serverClientId = "112811611329-6s9p28ehv0o6lc5pgt8kdoi285c7j1it.apps.googleusercontent.com")
        }
    }
}