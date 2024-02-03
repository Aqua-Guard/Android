package tn.aquaguard.ui

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tn.aquaguard.R
import tn.aquaguard.network.SessionManager
import tn.aquaguard.viewmodel.UserViewModel
import java.io.File
import java.io.FileOutputStream

class CompleteGoogleSignin : AppCompatActivity() {
    val IMAGE_REQUEST_CODE = 100

    private var imageUri: Uri? = null
    private var passwordVisible: Boolean = false

    private val viewModel by viewModels<UserViewModel>()
    private var selectedImageUri: Uri? = null
    private lateinit var imageViewProfile: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.complete_google_signin)

        imageViewProfile = findViewById(R.id.imageViewProfile)

        val imgPickImage = findViewById<ImageView>(R.id.imgPickImage)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        Picasso.with(this)
            .load("https://aquaguard-tux1.onrender.com/images/user/" + SessionManager(applicationContext).getImage())
            .fit().centerInside().into(imageViewProfile)

        imgPickImage.setOnClickListener {
            pickImage()
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

        btnSubmit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            val password = findViewById<EditText>(R.id.password).text.toString()


            selectedImageUri?.let { uri ->
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    val mimeType = contentResolver.getType(uri) ?: "image/jpeg"
                    val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"
                    val tempFile = File.createTempFile("prefix_", "_image.$extension", cacheDir)
                    FileOutputStream(tempFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }

                    val requestFile = RequestBody.create(MediaType.parse(mimeType), tempFile)
                    val imageBody = MultipartBody.Part.createFormData("image", tempFile.name, requestFile)


                    val passwordBody = RequestBody.create(MediaType.parse("text/plain"), password)
                    Log.e("imageBody", imageBody.toString())

                    Log.d("EditProfile", "Image Content Type: ${imageBody.body()?.contentType()}")
                    Log.d(
                        "EditProfile",
                        "Image Content Length: ${imageBody.body()?.contentLength()}"
                    )

                    viewModel.viewModelScope.launch {
                        if (password.isNotEmpty()
                        ) {
                            viewModel.completeGoogleSignin(
                                SessionManager(applicationContext).getId(),
                                passwordBody,
                                imageBody,
                                SessionManager(applicationContext)
                            )
                            if (viewModel.responseGoogle?.isSuccessful == true) {
                                startActivity(intent)
                            }
                        } else {
                            Snackbar.make(
                                it,
                                "Please fill in all fields",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

        val setupLater = findViewById<Button>(R.id.setupLater)

        setupLater.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            imageViewProfile.setImageURI(selectedImageUri)
            imageUri = selectedImageUri
            Log.e("onActivityResult", "$selectedImageUri")


        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }
}