package tn.aquaguard.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import tn.aquaguard.R
import tn.aquaguard.databinding.ActivationCodeBinding

class ActivationCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivationCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_email)
        binding = ActivationCodeBinding.inflate(layoutInflater)

        binding.btnForgotCode.setOnClickListener {
            try {
                val intent = Intent(this, ResetPasswordActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("ResetPasswordActivity", "Error starting ResetPasswordActivity", e)
            }
        }

    }

}


