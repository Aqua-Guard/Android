package tn.aquaguard.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import tn.aquaguard.R
import tn.aquaguard.network.SessionManager
import tn.aquaguard.viewmodel.UserViewModel

class ProfileActivity : AppCompatActivity() {
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        val btnChangePassword = findViewById<Button>(R.id.btnChangePassword)
        val btnDeleteAccount = findViewById<Button>(R.id.btnDeleteAccount)
        val btnEditProfile = findViewById<Button>(R.id.btnEditProfile)


        var image = findViewById<ImageView>(R.id.imageViewProfile)
        var textViewName = findViewById<TextView>(R.id.textViewName)
        Picasso.with(this)
            .load("http://10.0.2.2:9090/images/user/" + SessionManager(applicationContext).getImage())
            .fit().centerInside().into(image)
        textViewName.text = SessionManager(applicationContext).getUsername()


        btnEditProfile.setOnClickListener {
            try {
                val intent = Intent(this, EditProfile::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("EditProfile", "Error starting EditProfile", e)
            }
        }
        btnChangePassword.setOnClickListener {
            try {
                val intent = Intent(this, ChangePasswordActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("ChangePasswordActivity", "Error starting ChangePasswordActivity", e)
            }
        }


        btnDeleteAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            try {
                viewModel.viewModelScope.launch {
                    viewModel.deleteAccount(SessionManager(applicationContext).getEmail())
                    SessionManager(applicationContext).clear()
                    startActivity(intent)
                }
            } catch (e: Exception) {
                Log.e("LoginActivity", "Error starting LoginActivity", e)
            }
        }

    }

}