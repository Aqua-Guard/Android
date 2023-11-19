package tn.aquaguard.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tn.aquaguard.R
import tn.aquaguard.databinding.ActivationCodeBinding
import tn.aquaguard.models.ActivationCodeResponse
import tn.aquaguard.models.SendActivationCode
import tn.aquaguard.viewmodel.ForgotPasswordViewModel

class ActivationCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivationCodeBinding
    private val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activation_code)
        binding = ActivationCodeBinding.inflate(layoutInflater)
        val emailAddress = intent.getStringExtra("EMAIL_ADDRESS")

        binding.btnForgotCode.setOnClickListener {
                val intent = Intent(this, ResetPasswordActivity::class.java)

//            try {
//                viewModel.viewModelScope.launch {
//
//                    viewModel.verifyCode(
//                        ActivationCodeResponse(
//                            emailAddress,
//                            findViewById<EditText>(R.id.editActivationCode)
//
//                            )
//                    )
//
//                    if (viewModel.response!!.isSuccessful) {
//                        startActivity(intent)
//                    }
//                }
//
//            } catch (e: Exception) {
//                Log.e("error", "Email not found!", e)
//            }
        }

    }

}


