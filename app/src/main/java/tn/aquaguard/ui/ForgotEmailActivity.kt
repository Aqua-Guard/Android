package tn.aquaguard.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import tn.aquaguard.R
import tn.aquaguard.databinding.ForgotEmailBinding

class ForgotEmailActivity : AppCompatActivity() {
    private lateinit var binding: ForgotEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_email)
        binding = ForgotEmailBinding.inflate(layoutInflater)

        binding.btnForgotEmail.setOnClickListener {
            try {
                val intent = Intent(this, ActivationCodeActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("ActivationCodeActivity", "Error starting ActivationCodeActivity", e)
            }
        }

    }

}


