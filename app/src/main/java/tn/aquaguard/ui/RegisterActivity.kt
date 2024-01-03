package tn.aquaguard.ui

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tn.aquaguard.R
import tn.aquaguard.models.SignupRequest
import tn.aquaguard.network.SessionManager
import tn.aquaguard.viewmodel.UserViewModel
import java.io.File
import java.io.FileOutputStream

class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<UserViewModel>()
    private var passwordVisible: Boolean = false
    private var confirmVisible: Boolean = false
    val IMAGE_REQUEST_CODE = 100


    private lateinit var image: ImageView
    private var imageUri: Uri? = null
    private var selectedImageUri: Uri? = null
    private lateinit var imageViewProfile: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        imageViewProfile = findViewById(R.id.imageViewProfile)

        val imgPickImage = findViewById<ImageView>(R.id.imgPickImage)

        val registerEye = findViewById<TextInputLayout>(R.id.registerEye);
        val confirmEye = findViewById<TextInputLayout>(R.id.confirmEye);

        registerEye.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        val toggleDrawable = resources.getDrawable(R.drawable.ic_eye, theme)
        registerEye.setEndIconDrawable(toggleDrawable)

        registerEye.setEndIconOnClickListener {
            passwordVisible = !passwordVisible
            if (passwordVisible) {
                toggleDrawable.setColorFilter(
                    resources.getColor(R.color.babyBlue),
                    PorterDuff.Mode.SRC_ATOP
                )
            } else {
                toggleDrawable.setColorFilter(
                    resources.getColor(R.color.black),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
            registerEye.setEndIconDrawable(toggleDrawable)
            registerEye.editText?.transformationMethod =
                if (passwordVisible) null else android.text.method.PasswordTransformationMethod.getInstance()
        }

        confirmEye.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        val toggleDrawable2 = resources.getDrawable(R.drawable.ic_eye, theme)
        confirmEye.setEndIconDrawable(toggleDrawable2)

        confirmEye.setEndIconOnClickListener {
            confirmVisible = !confirmVisible
            if (confirmVisible) {
                toggleDrawable2.setColorFilter(
                    resources.getColor(R.color.babyBlue),
                    PorterDuff.Mode.SRC_ATOP
                )
            } else {
                toggleDrawable2.setColorFilter(
                    resources.getColor(R.color.black),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
            confirmEye.setEndIconDrawable(toggleDrawable2)
            confirmEye.editText?.transformationMethod =
                if (confirmVisible) null else android.text.method.PasswordTransformationMethod.getInstance()
        }


        imgPickImage.setOnClickListener {
            pickImage()
        }

        btnLogin.setOnClickListener {
            try {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("LoginActivity", "Error starting LoginActivity", e)
            }
        }
        btnSubmit.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)

            val username = findViewById<EditText>(R.id.editTextUsername).text.toString()
            val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
            val password = findViewById<EditText>(R.id.editTextPassword).text.toString()
            val confirmPassword =
                findViewById<EditText>(R.id.editTextConfirmPassword).text.toString()
            val firstName = findViewById<EditText>(R.id.editTextFirstName).text.toString()
            val lastName = findViewById<EditText>(R.id.editTextLastName).text.toString()

            selectedImageUri?.let { uri ->
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    val mimeType = contentResolver.getType(uri) ?: "image/jpeg"
                    val extension =
                        MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"
                    val tempFile = File.createTempFile("prefix_", "_image.$extension", cacheDir)
                    FileOutputStream(tempFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }

                    val requestFile = RequestBody.create(MediaType.parse(mimeType), tempFile)
                    val imageBody =
                        MultipartBody.Part.createFormData("image", tempFile.name, requestFile)


                    val passwordBody = RequestBody.create(MediaType.parse("text/plain"), password)
                    val lastNameBody = RequestBody.create(MediaType.parse("text/plain"), lastName)
                    val firstNameBody = RequestBody.create(MediaType.parse("text/plain"), firstName)
                    val emailBody = RequestBody.create(MediaType.parse("text/plain"), email)
                    val usernameBody = RequestBody.create(MediaType.parse("text/plain"), username)
                    Log.e("imageBody", imageBody.toString())

                    Log.d("EditProfile", "Image Content Type: ${imageBody.body()?.contentType()}")
                    Log.d(
                        "EditProfile",
                        "Image Content Length: ${imageBody.body()?.contentLength()}"
                    )

                    viewModel.viewModelScope.launch {
                        if (username.isNotEmpty() && isEmailValid(email) && email.isNotEmpty()
                            && firstName.isNotEmpty() && lastName.isNotEmpty() && password.isNotEmpty()
                            && confirmPassword.isNotEmpty() && password == confirmPassword
                        ) {
                            viewModel.viewModelScope.launch {

                                viewModel.signup(
                                    usernameBody,
                                    passwordBody,
                                    emailBody,
                                    firstNameBody,
                                    lastNameBody,
                                    imageBody
                                )
                                if (viewModel.response?.isSuccessful == true) {
                                    startActivity(intent)
                                } else if (viewModel.response!!.code() == 400) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Username already exists!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        applicationContext,
                                        "server issue",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        } else if (password != confirmPassword) {
                            Toast.makeText(
                                applicationContext,
                                "password and confirm password are different",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else if (!isEmailValid(email)) {
                            Toast.makeText(
                                applicationContext,
                                "Enter a valid email address",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Fields can't be empty",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }


//            try {
//                if (username.isNotEmpty() && isEmailValid(email) && email.isNotEmpty()
//                    && firstName.isNotEmpty() && lastName.isNotEmpty() && password.isNotEmpty()
//                    && confirmPassword.isNotEmpty() && password == confirmPassword) {
//                    viewModel.viewModelScope.launch {
//
//                        viewModel.signup(
//                            SignupRequest(
//                                username,
//                                password,
//                                email,
//                                firstName,
//                                lastName,
//                                )
//                        )
//                        if (viewModel.response?.isSuccessful == true) {
//                            startActivity(intent)
//                        } else if (viewModel.response!!.code() == 400) {
//                            Toast.makeText(
//                                applicationContext,
//                                "Username already exists!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        } else {
//                            Toast.makeText(applicationContext, "server issue", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    }
//                }
//                else if (password != confirmPassword) {
//                    Toast.makeText(
//                        applicationContext,
//                        "password and confirm password are different",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                } else if (!isEmailValid(email)) {
//                    Toast.makeText(
//                        applicationContext,
//                        "Enter a valid email address",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                else {
//                    Toast.makeText(
//                        applicationContext,
//                        "Fields can't be empty",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                }
//
//            } catch (e: Exception) {
//                e.message?.let { it1 -> Log.e("error", it1) }
//                println ("error")
//                println(e.message)
//            }
        }

    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            imageViewProfile.setImageURI(selectedImageUri)
            imageUri = selectedImageUri
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }
}