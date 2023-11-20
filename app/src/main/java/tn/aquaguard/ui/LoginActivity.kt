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
import tn.aquaguard.databinding.ActivityLoginBinding
import tn.aquaguard.models.LoginRequest
import tn.aquaguard.network.SessionManager
import tn.aquaguard.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        val btnRegister = findViewById<Button>(R.id.btnRegister)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        val buttonForgotPassword = findViewById<Button>(R.id.buttonForgotPassword)

        btnRegister.setOnClickListener {
            try {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("RegisterActivity", "Error starting RegisterActivity", e)
            }
        }

        btnSubmit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            try {
                viewModel.viewModelScope.launch {

                    viewModel.login(
                        LoginRequest(
                            findViewById<EditText>(R.id.editTextUsername).text.toString(),
                            findViewById<EditText>(R.id.editTextPassword).text.toString()
                        ), SessionManager(applicationContext)
                    )

                    if (viewModel.response!!.isSuccessful) {
                        startActivity(intent)
                    } else if (viewModel.response!!.code() == 400) {
                        Toast.makeText(
                            applicationContext,
                            "Invalid Credentials!",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(applicationContext, "server issue", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            } catch (e: Exception) {
                Log.e("LoginActivity", "Login failed")
                println("error")
                println(e.message)
            }

        }


        buttonForgotPassword.setOnClickListener {
            try {
                val intent = Intent(this, ForgotEmailActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("ForgotEmailActivity", "Error starting ForgotEmailActivity", e)
            }
        }

    }

    }


