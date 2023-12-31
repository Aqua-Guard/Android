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
import retrofit2.HttpException
import tn.aquaguard.R
import tn.aquaguard.databinding.ResetPasswordBinding
import tn.aquaguard.models.ActivationCodeResponse
import tn.aquaguard.models.ResetPasswordRequest
import tn.aquaguard.network.SessionManager
import tn.aquaguard.viewmodel.ForgotPasswordViewModel
import java.io.IOException

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ResetPasswordBinding
    private val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reset_password)
        binding = ResetPasswordBinding.inflate(layoutInflater)
        val btnResetPassword = findViewById<Button>(R.id.btnResetPassword)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed()
        }
        btnResetPassword.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)

            try {
                viewModel.viewModelScope.launch {

                    viewModel.resetPassword(
                        ResetPasswordRequest(
                            SessionManager(applicationContext).getEmail(),
                            findViewById<EditText>(R.id.editPassword).text.toString(),
                            findViewById<EditText>(R.id.editConfirmPassword).text.toString(),
                        )
                    )
                    if (findViewById<EditText>(R.id.editPassword).text.toString()
                            .isNotEmpty() && findViewById<EditText>(R.id.editConfirmPassword).text.toString()
                            .isNotEmpty()
                    ) {
                        if (viewModel.resetResponse?.isSuccessful == true) {
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "passwords can't be empty",
                            Toast.LENGTH_SHORT
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("error", "Email not found!", e)
            }
        }

    }

}


