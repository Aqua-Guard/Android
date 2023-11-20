package tn.aquaguard.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tn.aquaguard.R
import tn.aquaguard.models.SendActivationCode
import tn.aquaguard.network.SessionManager
import tn.aquaguard.viewmodel.UserViewModel

class ProfileActivity : AppCompatActivity() {
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        val btnChangePassword = findViewById<Button>(R.id.btnChangePassword)
        val btnDeleteAccount = findViewById<Button>(R.id.btnDeleteAccount)

        btnChangePassword.setOnClickListener {
            try {
                val intent = Intent(this, ChangePasswordActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("ChangePasswordActivity", "Error starting ChangePasswordActivity", e)
            }
        }


        btnDeleteAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            try {
                viewModel.viewModelScope.launch {
                    viewModel.deleteAccount(SessionManager(applicationContext).getEmail())
                        startActivity(intent)
                }
            } catch (e: Exception) {
                Log.e("LoginActivity", "Error starting LoginActivity", e)
            }
        }

    }
}