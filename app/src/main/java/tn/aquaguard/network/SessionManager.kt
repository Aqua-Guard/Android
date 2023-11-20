package tn.aquaguard.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import tn.aquaguard.R

class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val TOKEN = "token"
        const val USERNAME = "username"
        const val ROLE = "role"
        const val EMAIL = "email"
        const val ISACTIVATED = "isActivated"
    }

    fun saveToken(token: String, username:String, role:String, email: String, isActivated: Boolean) {
        val editor = prefs.edit()
        editor.putString(TOKEN, token)
        editor.putString(USERNAME, username)
        editor.putString(ROLE, role)
        editor.putString(EMAIL, email)
        editor.putBoolean(ISACTIVATED, isActivated)
        editor.apply()
    }

    fun clear(){
        prefs.edit()
            .remove(TOKEN)
            .remove(USERNAME)
            .remove(ROLE)
            .remove(EMAIL)
            .remove(ISACTIVATED).apply()
    }

    fun getToken(): String? {
        return prefs.getString(TOKEN, null)
    }
    fun getRole(): String? {
        return prefs.getString(ROLE, null)
    }
    fun getEmail(): String? {
        return prefs.getString(EMAIL, null)
    }
    fun getUsername(): String? {
        return prefs.getString(USERNAME, null)
    }
    fun getIsActivated(): Boolean? {
        return prefs.getBoolean(ISACTIVATED,false)
    }

}