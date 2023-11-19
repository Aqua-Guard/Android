package tn.aquaguard.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tn.aquaguard.R
import tn.aquaguard.models.ChangePassword
import tn.aquaguard.viewmodel.UserViewModel

class ChangePasswordActivity : AppCompatActivity() {
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)

            val oldPassword = findViewById<EditText>(R.id.oldPassword).text.toString()
            val newPassword = findViewById<EditText>(R.id.newPassword).text.toString()
            val confirmPassword = findViewById<EditText>(R.id.confirmPassword).text.toString()
            try {
                if (oldPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmPassword.isNotEmpty() && newPassword == confirmPassword) {
                    viewModel.viewModelScope.launch {

                        viewModel.changePassword(
                            ChangePassword(
                                "amira.benmbarek@esprit.tn",
                                oldPassword,
                                newPassword,
                                confirmPassword
                            )
                        )
                        if (viewModel.response?.isSuccessful == true) {
                            startActivity(intent)
                        } else if (viewModel.response!!.code() == 400) {
                            Toast.makeText(
                                applicationContext,
                                "Wrong Information",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(applicationContext, "server issue", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else if (newPassword != confirmPassword) {
                    Toast.makeText(
                        applicationContext,
                        "password and confirm password are different",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Fields can't be empty",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } catch (e: Exception) {
                Log.e("ProfileActivity", "Error starting ProfileActivity", e)
            }
        }
    }
}