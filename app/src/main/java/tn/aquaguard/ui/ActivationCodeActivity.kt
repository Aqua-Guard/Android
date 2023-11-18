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
import tn.aquaguard.databinding.ResetPasswordBinding
import tn.aquaguard.models.ActivationCodeResponse
import tn.aquaguard.viewmodel.ForgotPasswordViewModel

class ActivationCodeActivity : AppCompatActivity() {
    private lateinit var binding: ResetPasswordBinding
    private val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activation_code)
        binding = ResetPasswordBinding.inflate(layoutInflater)
        val emailAddress = intent.getStringExtra("EMAIL_ADDRESS")
        val btnForgotCode = findViewById<Button>(R.id.btnForgotCode)

        btnForgotCode.setOnClickListener {
                val intent = Intent(this, ResetPasswordActivity::class.java)

            try {
                viewModel.viewModelScope.launch {

                    viewModel.verifyCode(
                        ActivationCodeResponse(
                            emailAddress,
                            Integer.valueOf(findViewById<EditText>(R.id.editActivationCode).text.toString())
                            )
                    )

                    if (viewModel.response!!.isSuccessful) {
                        intent.putExtra("EMAIL",emailAddress)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(applicationContext,"wrong activation code", Toast.LENGTH_SHORT)
                    }
                }

            } catch (e: Exception) {
                Log.e("error", "Email not found!", e)
            }
        }

    }

}


