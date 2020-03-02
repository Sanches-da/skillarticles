package ru.skillbranch.skillarticles.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PrefManager(cont: Context): PreferenceManager(cont){
    val preferences : SharedPreferences by lazy { PreferenceManager(cont).sharedPreferences }


    fun clearAll(){
        val edPref = preferences.edit()
        edPref.clear()
        edPref.apply()
    }
}