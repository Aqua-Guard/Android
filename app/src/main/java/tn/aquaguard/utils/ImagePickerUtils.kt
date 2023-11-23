package tn.aquaguard.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.ImageView

interface ImagePickerCallback {
    fun onImageSelected(imageUri: String?)
}

class ImagePickerUtils(
    private val activity: Activity,
    private val imageView: ImageView,
    //private val imagePickerCallback: ImagePickerCallback
) {

    companion object {
        const val IMAGE_REQUEST_CODE = 100
    }

    private var selectedImageUri: Uri? = null

    fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Assuming you want to set the selected image to the ImageView
            imageView.setImageURI(data?.data)
            selectedImageUri = data?.data
            print(selectedImageUri)
           // displaySelectedImage(imageView, selectedImageUri)

        }
    }

    fun getSelectedImageUri(): Uri? {
        return selectedImageUri
    }

    fun displaySelectedImage(imageView: ImageView, selectedImageUri: Uri?) {
        // You can use a library like Glide or Picasso to load and display the image
      /*  Glide.with(this)
            .load(selectedImageUri)
            .into(imageView)*/
    }
}
