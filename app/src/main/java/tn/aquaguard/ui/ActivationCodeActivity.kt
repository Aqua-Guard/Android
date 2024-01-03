package tn.aquaguard.ui

import android.R.attr.value
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
import tn.aquaguard.databinding.ResetPasswordBinding
import tn.aquaguard.models.ActivationCodeResponse
import tn.aquaguard.network.SessionManager
import tn.aquaguard.viewmodel.ForgotPasswordViewModel


class ActivationCodeActivity : AppCompatActivity() {
    private lateinit var binding: ResetPasswordBinding
    private val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activation_code)

        binding = ResetPasswordBinding.inflate(layoutInflater)

        val btnForgotCode = findViewById<Button>(R.id.btnForgotCode)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed()
        }
        btnForgotCode.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)

            try {
                viewModel.viewModelScope.launch {

                    viewModel.verifyCode(
                        ActivationCodeResponse(
                            SessionManager(applicationContext).getEmail(),
                            Integer.valueOf(findViewById<EditText>(R.id.editActivationCode).text.toString())
                        )
                    )

                    if (viewModel.response!!.isSuccessful) {
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


