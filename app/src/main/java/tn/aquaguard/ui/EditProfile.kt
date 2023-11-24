package tn.aquaguard.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
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
import tn.aquaguard.models.EditProfile
import okhttp3.MediaType
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.FileOutputStream


class EditProfile : AppCompatActivity() {
    private val viewModel by viewModels<UserViewModel>()
    private var selectedImageUri: Uri? = null
    private lateinit var imageViewProfile: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)

        imageViewProfile = findViewById(R.id.imageViewProfile)

        val imgPickImage = findViewById<ImageView>(R.id.imgPickImage)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        Picasso.with(this)
            .load("http://10.0.2.2:9090/images/user/" + SessionManager(applicationContext).getImage())
            .fit().centerInside().into(imageViewProfile)

        imgPickImage.setOnClickListener {
            pickImage()

        }

        btnSubmit.setOnClickListener {
            btnSubmitFn()

        }
    }
    @SuppressLint("SuspiciousIndentation")
    private fun btnSubmitFn() {
        try {
            val intent = Intent(this, ProfileActivity::class.java)


                    viewModel.viewModelScope.launch {

                    if (selectedImageUri != null){
//                        val parcelFileDescriptor: ParcelFileDescriptor? =
//                            applicationContext.contentResolver.openFileDescriptor(selectedImageUri!!, "r")
//                        val fileDescriptor: FileDescriptor? =
//                            parcelFileDescriptor?.fileDescriptor
//                        val inputStream = FileInputStream(fileDescriptor)
//                        val byteArray = inputStream.readBytes()
//                        val file = File(applicationContext.cacheDir, "temp.jpg")
//
//                        file.writeBytes(byteArray)
//
//
//                        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
//                        val part = MultipartBody.Part.createFormData(
//                            "file",
//                            file.name,
//                            requestBody
//                        )
//                        Log.e("partUri", part.toString())
                        if (findViewById<EditText>(R.id.firstName).text.toString()
                                .isNotEmpty() && findViewById<EditText>(R.id.lastName).text.toString()
                                .isNotEmpty() && findViewById<EditText>(R.id.email).text.toString()
                                .isNotEmpty() && findViewById<EditText>(R.id.username).text.toString()
                                .isNotEmpty()
                        ) {
                            viewModel.editProfile(
                                SessionManager(applicationContext).getUsername(),
                                findViewById<EditText>(R.id.email).text.toString(),
                                findViewById<EditText>(R.id.firstName).text.toString(),
                                findViewById<EditText>(R.id.lastName).text.toString(),
                                findViewById<EditText>(R.id.username).text.toString(),
                                //part

                            )
                            if (viewModel.responseEdit?.isSuccessful == true) {
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(
                                applicationContext, "fields can't be empty", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    else {
                        Toast.makeText(
                            applicationContext, "image can't be empty", Toast.LENGTH_SHORT
                        ).show()
                    }
                    }

        } catch (e: Exception) {
            Log.e("error", "User not found!", e)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 200 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            selectedImageUri = selectedImage
            Picasso.with(this).load(selectedImageUri).fit().centerInside().into(imageViewProfile)

        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        this@EditProfile.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 200)
    }

}