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
        const val ID = "id"
        const val IMAGE = "image"
        const val NBPTS = "nbPts"
    }

    fun saveToken(token: String,id:String,image:String,nbPts: Int, username:String, role:String, email: String, isActivated: Boolean) {
        val editor = prefs.edit()
        editor.putString(TOKEN, token)
        editor.putString(USERNAME, username)
        editor.putString(ROLE, role)
        editor.putString(EMAIL, email)
        editor.putBoolean(ISACTIVATED, isActivated)
        editor.putString(ID, id)
        editor.putString(IMAGE, image)
        editor.putInt(NBPTS, nbPts)
        editor.apply()
    }

    fun clear(){
        prefs.edit()
            .remove(TOKEN)
            .remove(USERNAME)
            .remove(ROLE)
            .remove(EMAIL)
            .remove(ID)
            .remove(IMAGE)
            .remove(NBPTS)
            .remove(ISACTIVATED).apply()
    }

    fun updateUser(image:String,username: String, email:String)
    {
        val editor = prefs.edit()
        editor.putString(USERNAME, username)
        editor.putString(EMAIL, email)
        editor.putString(IMAGE, image)
        editor.apply()
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
    fun getId(): String? {
        return prefs.getString(ID, null)
    }
    fun getImage(): String? {
        return prefs.getString(IMAGE,null)
    }
    fun getNbPts(): Int? {
        return prefs.getInt(NBPTS, 0)
    }
    fun getUsername(): String? {
        return prefs.getString(USERNAME, null)
    }
    fun getIsActivated(): Boolean? {
        return prefs.getBoolean(ISACTIVATED,false)
    }

}