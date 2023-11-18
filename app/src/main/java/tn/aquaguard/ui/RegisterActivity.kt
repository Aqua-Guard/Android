package tn.aquaguard.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tn.aquaguard.R
import tn.aquaguard.models.SignupRequest
import tn.aquaguard.viewmodel.UserViewModel

class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)


        btnLogin.setOnClickListener {
            try {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("LoginActivity", "Error starting MainActivity", e)
            }
        }
        btnSubmit.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)

            val username = findViewById<EditText>(R.id.editTextUsername).text.toString()
            val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
            val password = findViewById<EditText>(R.id.editTextPassword).text.toString()
            val confirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword).text.toString()
            val firstName = findViewById<EditText>(R.id.editTextFirstName).text.toString()
            val lastName = findViewById<EditText>(R.id.editTextLastName).text.toString()
            try {
                if (username.isNotEmpty() && email.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword) {
                    viewModel.viewModelScope.launch {

                        viewModel.signup(
                            SignupRequest(
                                username,
                                password,
                                email,
                                firstName,
                                lastName,
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
                } else if (password != confirmPassword) {
                    Toast.makeText(
                        applicationContext,
                        "password and confirm password are different",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                else {
                    Toast.makeText(
                        applicationContext,
                        "Fields can't be empty",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            } catch (e: Exception) {
                e.message?.let { it1 -> Log.e("error", it1) }
                println ("error")
                println(e.message)
            }
        }

    }
}