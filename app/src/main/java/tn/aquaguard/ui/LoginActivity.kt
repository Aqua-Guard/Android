package tn.aquaguard.ui

import android.app.AlertDialog
import android.content.Intent
import android.graphics.PorterDuff
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import tn.aquaguard.R
import tn.aquaguard.databinding.ActivityLoginBinding
import tn.aquaguard.models.GoogleSignInRequest
import tn.aquaguard.models.LoginRequest
import tn.aquaguard.network.SessionManager
import tn.aquaguard.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var binding: ActivityLoginBinding
    private var passwordVisible: Boolean = false
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        val btnRegister = findViewById<Button>(R.id.btnRegister)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val mediaPlayer = MediaPlayer.create(this, R.raw.sound_effect)

        val buttonForgotPassword = findViewById<Button>(R.id.buttonForgotPassword)
        val googleBtn = findViewById<ImageView>(R.id.btnGoogle)

        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("814706189623-tpef5desfloi00q2ae3atl7ji2ljhlqi.apps.googleusercontent.com")
                .requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            navigateToSecondActivity()
        }

        googleBtn.setOnClickListener {
            signIn()
        }

        val textInputLayout = findViewById<TextInputLayout>(R.id.eye);

        textInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        val toggleDrawable = resources.getDrawable(R.drawable.ic_eye, theme)
        textInputLayout.setEndIconDrawable(toggleDrawable)

        textInputLayout.setEndIconOnClickListener {
            passwordVisible = !passwordVisible
            if (passwordVisible) {
                toggleDrawable.setColorFilter(
                    resources.getColor(R.color.babyBlue), PorterDuff.Mode.SRC_ATOP
                )
            } else {
                toggleDrawable.setColorFilter(
                    resources.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP
                )
            }
            textInputLayout.setEndIconDrawable(toggleDrawable)
            textInputLayout.editText?.transformationMethod =
                if (passwordVisible) null else android.text.method.PasswordTransformationMethod.getInstance()
        }

        btnRegister.setOnClickListener {
            try {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("RegisterActivity", "Error starting RegisterActivity", e)
            }
        }

        btnSubmit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            try {
                viewModel.viewModelScope.launch {

                    viewModel.login(
                        LoginRequest(
                            findViewById<EditText>(R.id.editTextUsername).text.toString(),
                            findViewById<EditText>(R.id.editTextPassword).text.toString()
                        ), SessionManager(applicationContext)
                    )

                    if (viewModel.response!!.isSuccessful) {
                        mediaPlayer.start()

                        startActivity(intent)
                    } else if (viewModel.response!!.code() == 400) {
                        Toast.makeText(
                            applicationContext, "Invalid Credentials!", Toast.LENGTH_SHORT
                        ).show()
                    } else if (viewModel.response!!.code() == 402) {

                        val builder = AlertDialog.Builder(this@LoginActivity)
                        builder.setTitle("Access Denied")
                        builder.setMessage("Your account has been suspended for 7 days!!")

                        builder.setPositiveButton("OK") { _, _ ->
                        }

                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.show()

                    } else if (viewModel.response!!.code() == 500) {
                        Toast.makeText(
                            applicationContext, "Input required!!", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(applicationContext, "server issue", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            } catch (e: Exception) {
                Log.e("LoginActivity", "Login failed")
                println("error")
                println(e.message)
            }

        }

        buttonForgotPassword.setOnClickListener {
            try {
                val intent = Intent(this, ForgotEmailActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("ForgotEmailActivity", "Error starting ForgotEmailActivity", e)
            }
        }
    }

    private fun signIn() {
        val signInIntent = gsc!!.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val intent = Intent(this, CompleteGoogleSignin::class.java)

        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                viewModel.viewModelScope.launch {
                    task.getResult(ApiException::class.java)

                    viewModel.googleSignIn(
                        GoogleSignInRequest(
                            acct?.idToken ?: "",
                            acct?.displayName ?: ""
                        ), SessionManager(applicationContext)
                    )
                    if (viewModel.response?.isSuccessful == true) {
                        startActivity(intent)
                    } else if (viewModel.response!!.code() == 401) {
                        Toast.makeText(
                            applicationContext,
                            "Server issue!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (viewModel.response!!.code() == 400) {
                        Toast.makeText(
                            applicationContext,
                            "Sorry, this username already exists!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(applicationContext, "Sorry! try again later", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                navigateToSecondActivity()
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun navigateToSecondActivity() {
        val acct = GoogleSignIn.getLastSignedInAccount(this)

        Log.e("idToken--------", acct?.idToken ?: "idToken")
        Log.e("id------------", acct?.id ?: "id")
        Log.e("photoUrl------------", acct?.photoUrl?.path.toString())

    }
}