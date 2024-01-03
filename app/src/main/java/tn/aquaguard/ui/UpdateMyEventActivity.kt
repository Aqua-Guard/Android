package tn.aquaguard.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tn.aquaguard.R
import tn.aquaguard.viewmodel.EventViewModel
import java.io.File
import java.io.FileOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UpdateMyEventActivity : AppCompatActivity() {
    companion object {
        const val IMAGE_REQUEST_CODE = 100
    }
    private lateinit var image: ImageView
    private var selectedImageUri: Uri? = null
    private var  imageUri: Uri? = null
    private val viewModel: EventViewModel by viewModels()
    private lateinit var eventId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_my_event)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)



        var namefragment : TextView = findViewById(R.id.nameofcurentFragment)
        namefragment.text = "Update Event"


        var eventImage = intent.getStringExtra("eventImage")
        //print( "Event Image: $eventImage")



        val startDateLayout = findViewById<TextInputLayout>(R.id.startDateLayout)
        val endDateLayout = findViewById<TextInputLayout>(R.id.endDateLayout)
        val nameLayout = findViewById<TextInputLayout>(R.id.nameLayout)
        val descriptionLayout = findViewById<TextInputLayout>(R.id.descriptionLayout)
        val locationLayout = findViewById<TextInputLayout>(R.id.locationLayout)

        var nametext: TextInputEditText = findViewById(R.id.eventname)
        var descriptionevent: TextInputEditText = findViewById(R.id.eventdescription)
        var lieu: TextInputEditText = findViewById(R.id.eventlocation)
        var dateD: TextInputEditText = findViewById(R.id.editTextStartDate)
        var dateF: TextInputEditText = findViewById(R.id.editTextEndDate)
         image = findViewById(R.id.event_image)

        var btnSubmit :Button = findViewById(R.id.btnsubmitEvent)


        nametext.setText(intent.getStringExtra("EventName"))
        descriptionevent.setText(intent.getStringExtra("description"))
        lieu.setText(intent.getStringExtra("lieu"))
        dateD.setText(intent.getStringExtra("DateDebut"))
        dateF.setText(intent.getStringExtra("DateFin"))
       // Picasso.with(this).load("https://aquaguard-tux1.onrender.com/images/event/" + eventImage).fit().centerInside().into(image)


        var btnUpdateImage: Button = findViewById(R.id.addEventImage)

        fun pickImageGallery() {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            this.startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }


        btnUpdateImage.setOnClickListener {
            pickImageGallery()
            Toast.makeText(this, "Image Picked successfully!", Toast.LENGTH_SHORT).show()

        }

        btnSubmit.setOnClickListener {


// Assuming you have the necessary fields available
            val eventId = intent.getStringExtra("eventId").toString()
            val nametxt = nametext.text.toString()
            val descriptiontxt = descriptionevent.text.toString()
            val locationtxt = lieu.text.toString()
            val startDatetxt = dateD.text.toString()
            val endDatetxt = dateF.text.toString()

            // Validate input fields
            var isValid = true

            if (nametxt.isEmpty()) {
                nameLayout.error = "Please enter a name"
                isValid = false
            }else if (nametxt.length < 3 || nametxt.length > 30) {
                nameLayout.error = "Name must be between 3 and 30 characters"
                isValid = false
            } else {
                nameLayout.error = null
            }

            if (descriptiontxt.isEmpty()) {
                descriptionLayout.error = "Please enter a description"
                isValid = false
            }else if (descriptiontxt.length < 10 || descriptiontxt.length > 500) {
                descriptionLayout.error = "Description must be between 10 and 500 characters"
                isValid = false
            } else {
                descriptionLayout.error = null
            }

            if (locationtxt.isEmpty()) {
                locationLayout.error = "Please enter a location"
                isValid = false
            } else if (locationtxt.length < 3 || locationtxt.length > 50) {
                locationLayout.error = "Location must be between 3 and 50 characters"
                isValid = false
            } else {
                locationLayout.error = null
            }

            // Additional date checks

            val currentDate = Calendar.getInstance()
            val currentDateString = formatDate(
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH) + 1,
                currentDate.get(Calendar.DAY_OF_MONTH)
            )

            if (startDatetxt >= endDatetxt) {
                endDateLayout.error = "End date must be greater than start date"
                isValid = false
            } else {
                endDateLayout.error = null
            }

            if (startDatetxt < currentDateString) {
                startDateLayout.error =
                    "Start date must be equal to or greater than the current date"
                isValid = false
            } else {
                startDateLayout.error = null
            }

            if (endDatetxt <= startDatetxt) {
                endDateLayout.error = "End date must be greater than start date"
                isValid = false
            } else {
                endDateLayout.error = null
            }


            fun formatDateForRequest(inputDate: String): String {
                try {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)

                    val date = inputFormat.parse(inputDate)
                    return outputFormat.format(date)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                return inputDate // Return the original date in case of any error
            }

            selectedImageUri?.let { uri ->
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    val mimeType = contentResolver.getType(uri)
                        ?: "image/jpeg" // Default to "image/jpeg" if MIME type is null

                    val extension =
                        MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"


                    val tempFile = File.createTempFile("prefix_", "_image.$extension", cacheDir)
                    FileOutputStream(tempFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }

                    val requestFile = RequestBody.create(MediaType.parse(mimeType), tempFile)
                    val imageBody =
                        MultipartBody.Part.createFormData("image", "image_$extension", requestFile)

                    val startDatereq = formatDateForRequest(startDatetxt)
                    val endDatereq = formatDateForRequest(endDatetxt)

                    val description = RequestBody.create(MediaType.parse("text/plain"), descriptiontxt)
                    val name = RequestBody.create(MediaType.parse("text/plain"), nametxt)
                    val location = RequestBody.create(MediaType.parse("text/plain"), locationtxt)
                    val startDate = RequestBody.create(MediaType.parse("text/plain"), startDatereq)
                    val endDate = RequestBody.create(MediaType.parse("text/plain"), endDatereq)

                    if (isValid) {
                        viewModel.updateEvent(
                            eventId,
                            imageBody,
                            name,
                            description,
                            location,
                            startDate,
                            endDate
                        )
                        // Observe the updateEventResult LiveData to get updates on the API call
                        viewModel.updateEventResult.observe(this) { resource ->

                            if (resource.isSuccessful) {
                                Snackbar.make(
                                    it,
                                    "Event Updated successfully.",
                                    Snackbar.LENGTH_LONG
                                ).setBackgroundTint(Color.parseColor("#90EE90")).show()
                                finish()


                            }else {
                                Snackbar.make(
                                    it,
                                    "Please fill in all fields",
                                    Snackbar.LENGTH_LONG
                                ).setBackgroundTint(Color.parseColor("#B0FF0000")).show()
                            }
                    }
                }
            }
        }}

                // Enable the back arrow
                supportActionBar?.setDisplayHomeAsUpEnabled(true)

                val whiteArrow = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
                supportActionBar?.setHomeAsUpIndicator(whiteArrow)


                // Set the back arrow click listener
                toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }
            }
            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)
                if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                    selectedImageUri = data?.data
                    image.setImageURI(selectedImageUri)
                    imageUri = selectedImageUri
                    Log.e("UpdateMyEventActivity", "Selected Image URI: $selectedImageUri")


                }
            }
    fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day) // Note: month is zero-based, so subtract 1
        val dateFormat =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

            fun showStartDatePickerDialog(view: View) {
                val editTextStartDate = findViewById<EditText>(R.id.editTextStartDate)

                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        // Note: monthOfYear is zero-based, so add 1 to get the correct month
                        val selectedDate = formatDate(year, monthOfYear + 1, dayOfMonth)
                        editTextStartDate.setText(selectedDate)
                    },
                    year,
                    month,
                    day
                )

                datePickerDialog.show()
            }


            fun showEndDatePickerDialog(view: View) {
                val editTextStartDate = findViewById<EditText>(R.id.editTextEndDate)

                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        // Note: monthOfYear is zero-based, so add 1 to get the correct month
                        val selectedDate = formatDate(year, monthOfYear + 1, dayOfMonth)
                        editTextStartDate.setText(selectedDate)
                    },
                    year,
                    month,
                    day
                )

                datePickerDialog.show()
            }



}