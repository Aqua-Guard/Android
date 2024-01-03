package tn.aquaguard.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import tn.aquaguard.R
import tn.aquaguard.network.SessionManager
import tn.aquaguard.viewmodel.UserViewModel

class ProfileActivity : AppCompatActivity() {
    private val viewModel by viewModels<UserViewModel>()

    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        val btnChangePassword = findViewById<Button>(R.id.btnChangePassword)
        val btnDeleteAccount = findViewById<Button>(R.id.btnDeleteAccount)
        val btnEditProfile = findViewById<Button>(R.id.btnEditProfile)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed()
        }

        var image = findViewById<ImageView>(R.id.imageViewProfile)
        var textViewName = findViewById<TextView>(R.id.textViewName)
        var textViewEmail = findViewById<TextView>(R.id.textViewEmail)
        var textViewRole = findViewById<TextView>(R.id.textViewRole)
        var textViewPts = findViewById<TextView>(R.id.textViewPts)
        Picasso.with(this)
            .load("https://aquaguard-tux1.onrender.com/images/user/" + SessionManager(applicationContext).getImage())
            .fit().centerInside().into(image)
        textViewName.text = SessionManager(applicationContext).getUsername()
        textViewEmail.text = SessionManager(applicationContext).getEmail()

        Log.e("getUsername",SessionManager(applicationContext).getUsername()!!)
        Log.e("getEmail",SessionManager(applicationContext).getEmail()!!)
        Log.e("role",SessionManager(applicationContext).getRole()!!)
        Log.e("getNbPts",SessionManager(applicationContext).getNbPts()!!.toString())

        textViewRole.text = SessionManager(applicationContext).getRole()
        textViewPts.text = SessionManager(applicationContext).getNbPts().toString()


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

                gsc.signOut().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        viewModel.viewModelScope.launch {
                            SessionManager(applicationContext).clear()
//                            val intent = Intent(this, LoginActivity::class.java)
//                            startActivity(intent)

                            viewModel.desactivateAccount(SessionManager(applicationContext).getId())
                            SessionManager(applicationContext).clear()
                            startActivity(intent)
                        }
                    }else {
                        // Handle sign-out failure here
                        Toast.makeText(this, "Sign-out failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("LoginActivity", "Error starting LoginActivity", e)
            }
        }

    }

}