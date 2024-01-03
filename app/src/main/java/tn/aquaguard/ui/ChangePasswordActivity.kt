package tn.aquaguard.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import tn.aquaguard.R
import tn.aquaguard.models.ChangePassword
import tn.aquaguard.network.SessionManager
import tn.aquaguard.viewmodel.UserViewModel

class ChangePasswordActivity : AppCompatActivity() {
    private val viewModel by viewModels<UserViewModel>()
    private var oldPass: Boolean = false
    private var newPass: Boolean = false
    private var confirmPass: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        var image = findViewById<ImageView>(R.id.imageViewProfile)
        var textViewName = findViewById<TextView>(R.id.textViewName)
        Picasso.with(this).load("http://10.0.2.2:9090/images/user/"+ SessionManager(applicationContext).getImage()).fit().centerInside().into(image)
        textViewName.text = SessionManager(applicationContext).getUsername()

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed()
        }

        btnSubmit.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)

            val oldPassword = findViewById<EditText>(R.id.oldPassword).text.toString()
            val newPassword = findViewById<EditText>(R.id.newPassword).text.toString()
            val confirmPassword = findViewById<EditText>(R.id.confirmPassword).text.toString()
            try {
                if (oldPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmPassword.isNotEmpty() && newPassword == confirmPassword) {
                    viewModel.viewModelScope.launch {

                        viewModel.changePassword(
                            ChangePassword(
                                SessionManager(applicationContext).getEmail(),
                                oldPassword,
                                newPassword,
                                confirmPassword
                            )
                        )
                        if (viewModel.responsePass?.isSuccessful == true) {
                            startActivity(intent)
                        } else if (viewModel.responsePass!!.code() == 400) {
                            Toast.makeText(
                                applicationContext,
                                "Wrong Information",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(applicationContext, "server issue", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else if (newPassword != confirmPassword) {
                    Toast.makeText(
                        applicationContext,
                        "password and confirm password are different",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Fields can't be empty",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } catch (e: Exception) {
                Log.e("ProfileActivity", "Error starting ProfileActivity", e)
            }
        }


        val textInputLayout = findViewById<TextInputLayout>(R.id.oldEye);

        textInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        val toggleDrawable = resources.getDrawable(R.drawable.ic_eye, theme)
        textInputLayout.setEndIconDrawable(toggleDrawable)

        textInputLayout.setEndIconOnClickListener {
            oldPass = !oldPass
            if (oldPass) {
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
                if (oldPass) null else android.text.method.PasswordTransformationMethod.getInstance()
        }



        val textInputLayout2 = findViewById<TextInputLayout>(R.id.newEye);

        textInputLayout2.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        val toggleDrawable2 = resources.getDrawable(R.drawable.ic_eye, theme)
        textInputLayout2.setEndIconDrawable(toggleDrawable2)

        textInputLayout2.setEndIconOnClickListener {
            newPass = !newPass
            if (newPass) {
                toggleDrawable2.setColorFilter(
                    resources.getColor(R.color.babyBlue), PorterDuff.Mode.SRC_ATOP
                )
            } else {
                toggleDrawable2.setColorFilter(
                    resources.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP
                )
            }
            textInputLayout2.setEndIconDrawable(toggleDrawable2)
            textInputLayout2.editText?.transformationMethod =
                if (newPass) null else android.text.method.PasswordTransformationMethod.getInstance()
        }




        val textInputLayout3 = findViewById<TextInputLayout>(R.id.confirmEye);

        textInputLayout3.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        val toggleDrawable3 = resources.getDrawable(R.drawable.ic_eye, theme)
        textInputLayout3.setEndIconDrawable(toggleDrawable3)

        textInputLayout3.setEndIconOnClickListener {
            confirmPass = !confirmPass
            if (confirmPass) {
                toggleDrawable3.setColorFilter(
                    resources.getColor(R.color.babyBlue), PorterDuff.Mode.SRC_ATOP
                )
            } else {
                toggleDrawable3.setColorFilter(
                    resources.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP
                )
            }
            textInputLayout3.setEndIconDrawable(toggleDrawable3)
            textInputLayout3.editText?.transformationMethod =
                if (confirmPass) null else android.text.method.PasswordTransformationMethod.getInstance()
        }
    }
}