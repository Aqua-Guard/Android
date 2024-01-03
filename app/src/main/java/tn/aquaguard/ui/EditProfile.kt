package tn.aquaguard.ui

import android.app.Activity
import android.content.Intent
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
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tn.aquaguard.R
import tn.aquaguard.network.SessionManager
import tn.aquaguard.viewmodel.UserViewModel
import okhttp3.MediaType
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


class EditProfile : AppCompatActivity() {
    val IMAGE_REQUEST_CODE = 100


    private lateinit var image: ImageView
    private var imageUri: Uri? = null

    private val viewModel by viewModels<UserViewModel>()
    private var selectedImageUri: Uri? = null
    private lateinit var imageViewProfile: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)

        imageViewProfile = findViewById(R.id.imageViewProfile)

        val imgPickImage = findViewById<ImageView>(R.id.imgPickImage)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed()
        }
        Picasso.with(this)
            .load("http://10.0.2.2:9090/images/user/" + SessionManager(applicationContext).getImage())
            .fit().centerInside().into(imageViewProfile)
        findViewById<EditText>(R.id.email).setText(SessionManager(applicationContext).getEmail())
        findViewById<EditText>(R.id.username).setText(SessionManager(applicationContext).getUsername())
        findViewById<EditText>(R.id.firstName).setText(SessionManager(applicationContext).getFirstName())
        findViewById<EditText>(R.id.lastName).setText(SessionManager(applicationContext).getLastName())


        imgPickImage.setOnClickListener {
            pickImage()
        }


        btnSubmit.setOnClickListener {

            val firstName = findViewById<EditText>(R.id.firstName).text.toString()
            val lastName = findViewById<EditText>(R.id.lastName).text.toString()
            val email = findViewById<EditText>(R.id.email).text.toString()
            val username = findViewById<EditText>(R.id.username).text.toString()


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
                        if (firstName.isNotEmpty() && lastName.isNotEmpty() &&
                            email.isNotEmpty() && username.isNotEmpty()
                        ) {
                            viewModel.editProfile(
                                SessionManager(applicationContext).getId(),
                                emailBody,
                                firstNameBody,
                                lastNameBody,
                                usernameBody,
                                imageBody,
                                SessionManager(applicationContext)
                            )
                            if (viewModel.responseEdit?.isSuccessful == true) {
                                val intent = Intent(this@EditProfile, ProfileActivity::class.java)
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