package tn.aquaguard.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import tn.aquaguard.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        val btnChangePassword = findViewById<Button>(R.id.btnChangePassword)

        btnChangePassword.setOnClickListener {
            try {
                val intent = Intent(this, ChangePasswordActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("ChangePasswordActivity", "Error starting ChangePasswordActivity", e)
            }
        }

    }
}