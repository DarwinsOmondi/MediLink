package com.example.medilinkapp.utils

import android.content.Context

class SharedPreferenceHelper (private val context: Context){

    companion object{
        private const val MY_PREF_KEY = "my_pref"
    }

    fun saveStringData(key: String, data: String?){
        val sharedPreference = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedPreference.edit().putString(key, data).apply()
    }

    fun getStringData(key: String): String?{
        val sharedPreference = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        return sharedPreference.getString(key, null)
    }
}