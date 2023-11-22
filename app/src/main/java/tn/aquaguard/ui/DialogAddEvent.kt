package tn.aquaguard.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import tn.aquaguard.R
import tn.aquaguard.models.AddEventRequest
import tn.aquaguard.models.Event
import tn.aquaguard.utils.ImagePickerUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun DialogAddEvent (activity: MainActivity,onBtnAddImageClick: () -> Unit,onBtnSubmitClick: (AddEventRequest) -> Unit) {
    val dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.add_event)


    val addImage =  dialog.findViewById<Button>(R.id.addEventImage)
    val imageView =  dialog.findViewById<ImageView>(R.id.event_image)
    val imagePickerUtils = ImagePickerUtils(activity, imageView)

    val cancelButton = dialog.findViewById<ImageView>(R.id.cancelButton)
    val btnSubmit = dialog.findViewById<Button>(R.id.btnsubmitEvent)
    addImage.setOnClickListener {
        onBtnAddImageClick.invoke()
        imagePickerUtils.pickImageGallery()
        Picasso.with(activity).load(imagePickerUtils.getSelectedImageUri())
            .into(imageView)


    }

    val eventName = dialog.findViewById<TextInputEditText>(R.id.eventname)
    val eventdescription = dialog.findViewById<TextInputEditText>(R.id.eventdescription)
    val editTextStartDate = dialog.findViewById<TextInputEditText>(R.id.editTextStartDate).text.toString()
    val editTextEndDate = dialog.findViewById<TextInputEditText>(R.id.editTextEndDate).text.toString()
    val eventlocation = dialog.findViewById<TextInputEditText>(R.id.eventlocation)
    eventName.requestFocus()
    dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)



    btnSubmit.setOnClickListener {

//        // Define the date format pattern
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//        var eventDate: Date? = null
//
//        try {
//            // Parse the string to a Date object
//            eventDate = dateFormat.parse(editTextStartDate)
//        } catch (e: Exception) {
//            // Handle the exception (e.g., invalid date format)
//            e.printStackTrace()
//            // Show an error message to the user
//            Toast.makeText(activity, "Invalid date format", Toast.LENGTH_SHORT).show()
//        }
//
//
//        var eventEndDate: Date? = null
//
//        try {
//            // Parse the string to a Date object
//            eventEndDate = dateFormat.parse(editTextEndDate)
//        } catch (e: Exception) {
//            // Handle the exception (e.g., invalid date format)
//            e.printStackTrace()
//            // Show an error message to the user
//            Toast.makeText(activity, "Invalid date format", Toast.LENGTH_SHORT).show()
//        }


        val event = AddEventRequest(
            "654df18b535ec04efedabb0b",
            eventName.text.toString(),
            Date(),
            Date("Fri Nov 23 00:00:15 GMT 2023"),
            eventdescription.text.toString(),
            eventlocation.text.toString(),
            "1699647358490.jpg",
        )
        onBtnSubmitClick.invoke(event)

    }

    cancelButton.setOnClickListener { dialog.dismiss() }
    dialog.show()
    dialog.window!!.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window!!.setGravity(Gravity.BOTTOM)
}