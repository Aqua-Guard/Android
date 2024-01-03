package tn.aquaguard.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tn.aquaguard.R
import tn.aquaguard.databinding.ForgotEmailBinding
import tn.aquaguard.models.LoginRequest
import tn.aquaguard.models.SendActivationCode
import tn.aquaguard.network.SessionManager
import tn.aquaguard.viewmodel.ForgotPasswordViewModel
import tn.aquaguard.viewmodel.UserViewModel

class ForgotEmailActivity : AppCompatActivity() {
    private lateinit var binding: ForgotEmailBinding
    private val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_email)
        binding = ForgotEmailBinding.inflate(layoutInflater)
        val btnForgotEmail = findViewById<Button>(R.id.btnForgotEmail)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed()
        }
        btnForgotEmail.setOnClickListener {
            val intent = Intent(this, ActivationCodeActivity::class.java)

            try {
                viewModel.viewModelScope.launch {

                    viewModel.sendEmail(
                        SendActivationCode(
                            findViewById<EditText>(R.id.editTextForgotEmail).text.toString(),
                        )
                    )

                    if (findViewById<EditText>(R.id.editTextForgotEmail).text.toString().isNotEmpty())
                    {
                        if (viewModel.response!!.isSuccessful) {
                            intent.putExtra("EMAIL_ADDRESS", findViewById<EditText>(R.id.editTextForgotEmail).text.toString())
                            startActivity(intent)
                        }
                    }
                    else
                    {
                        Toast.makeText(applicationContext,"email address can't be empty", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                Log.e("error", "Email not found!", e)
            }
        }

    }

}


