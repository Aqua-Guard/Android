package tn.aquaguard.ui


import ReclamationFragment
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tn.aquaguard.R
import tn.aquaguard.databinding.ActivityMainBinding
import tn.aquaguard.fragments.EventFragment
import tn.aquaguard.fragments.ForumFragment
import tn.aquaguard.fragments.HomeFragment
import tn.aquaguard.fragments.MyCalenderFragment
import tn.aquaguard.fragments.MyEventFragment
import tn.aquaguard.fragments.MyPostFrament

import tn.aquaguard.fragments.StoreFragment
import tn.aquaguard.network.SessionManager
import tn.aquaguard.viewmodel.EventViewModel
import tn.aquaguard.viewmodel.PostViewModel
import tn.aquaguard.viewmodel.ReclamationViewModel
import java.io.File
import java.io.FileOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var postViewModel: PostViewModel

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var binding: ActivityMainBinding
    private val reclamationViewModel: ReclamationViewModel by viewModels()
    private val eventViewModel: EventViewModel by viewModels()
    private lateinit var selectedImage: ImageView
    private lateinit var selectedImageEvent: ImageView
    private var selectedImageUri: Uri? = null
    private var selectedImageEventUri: Uri? = null
    val bundle = Bundle()

    companion object {
        val IMAGE_REQUEST_CODE = 100;
        val IMAGE_EVENT_REQUEST_CODE_ = 150;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        println("-------------------------------------+"+SessionManager(applicationContext).getRole())
        println("-------------------------------------+"+SessionManager(applicationContext).getId())
        bundle.putString("userid", SessionManager(applicationContext).getId())

        // Initialize ViewModel
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar) //Ignore red line errors
        setSupportActionBar(toolbar)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        bottomAppBar = findViewById(R.id.bottomAppBar)
        fab = findViewById(R.id.fab)

        // Set the BottomAppBar to act as the ActionBar
        setSupportActionBar(bottomAppBar)

        // Handle the FAB click event
        fab.setOnClickListener {
            // Add your FAB click action here
        }

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, tn.aquaguard.R.string.open_nav,
            tn.aquaguard.R.string.close_nav
        )

        // Set the color of the menu icon
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)

        val myEventsItem: MenuItem = binding.navView.menu.findItem(R.id.nav_my_events)
        if (SessionManager(applicationContext).getRole() == "partenaire") {
            myEventsItem.isVisible = true
        }


        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    println("home")
                    replaceFragment(HomeFragment())
                    binding.include3.nameofcurentFragment.text = "Home"
                }

                R.id.events -> {
                    println("event")
                    replaceFragment(EventFragment())
                    binding.include3.nameofcurentFragment.text = "Event"
                }

                R.id.forum -> {
//                    Toast.makeText(applicationContext, username, Toast.LENGTH_SHORT)
//                        .show()
                    println("forum")
                    replaceFragment(ForumFragment())
                    binding.include3.nameofcurentFragment.text = "Forum"
                }

                R.id.store -> {
                    println("store")
                    replaceFragment(StoreFragment())
                    binding.include3.nameofcurentFragment.text = "Store"
                }
            }
            true
        }
        binding.fab.setOnClickListener {
            showBottomDialog()
        }

    }





    @SuppressLint("SuspiciousIndentation")
    private fun showBottomDialog() {

        val isEvent = binding.include3.nameofcurentFragment.text == "Event"|| binding.include3.nameofcurentFragment.text == "My Events"
        val isPartenaire = SessionManager(applicationContext).getRole() == "partenaire"
        val isreclamation = binding.include3.nameofcurentFragment.text == "Reclamations"




        val isPost = binding.include3.nameofcurentFragment.text == "Forum" || binding.include3.nameofcurentFragment.text == "My Posts"
        if (isEvent && isPartenaire) {

            val inflater = LayoutInflater.from(this)
            val dialogViewEvent = inflater.inflate(R.layout.add_event, null)

            selectedImageEvent = dialogViewEvent.findViewById(R.id.event_image)
            selectedImageEvent.setImageDrawable(null)

            val addImage =  dialogViewEvent.findViewById<Button>(R.id.addEventImage)
            val closeButton = dialogViewEvent.findViewById<ImageView>(R.id.closeButton)
            val btnSubmit = dialogViewEvent.findViewById<Button>(R.id.btnsubmitEvent)
            val btncancel = dialogViewEvent.findViewById<Button>(R.id.btncancelEvent)

            val eventName = dialogViewEvent.findViewById<TextInputEditText>(R.id.eventname)
            val eventdescription = dialogViewEvent.findViewById<TextInputEditText>(R.id.eventdescription)
            val editTextStartDate = dialogViewEvent.findViewById<TextInputEditText>(R.id.editTextStartDate)
            val editTextEndDate = dialogViewEvent.findViewById<TextInputEditText>(R.id.editTextEndDate)
            val eventlocation = dialogViewEvent.findViewById<TextInputEditText>(R.id.eventlocation)

            fun formatDate(year: Int, month: Int, day: Int): String {
                val calendar = Calendar.getInstance()
                calendar.set(year, month - 1, day) // Note: month is zero-based, so subtract 1
                val dateFormat =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                return dateFormat.format(calendar.time)
            }


             fun showStartDatePickerDialog(view: View) {
                val editTextStartDate = dialogViewEvent.findViewById<EditText>(R.id.editTextStartDate)

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
                val editTextEndDate = dialogViewEvent.findViewById<EditText>(R.id.editTextEndDate)

                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        // Note: monthOfYear is zero-based, so add 1 to get the correct month
                        val selectedDate = formatDate(year, monthOfYear + 1, dayOfMonth)
                        editTextEndDate.setText(selectedDate)
                    },
                    year,
                    month,
                    day
                )

                datePickerDialog.show()
            }

            editTextStartDate.setOnClickListener {
                showStartDatePickerDialog(editTextStartDate)
            }

            editTextEndDate.setOnClickListener {
                showEndDatePickerDialog(editTextEndDate)
            }



            val dialogBuilderEvent = AlertDialog.Builder(this)
                .setView(dialogViewEvent)
                .setCancelable(false)

            val dialogEvent = dialogBuilderEvent.create()


            if (closeButton != null) {
                closeButton.setOnClickListener {
                    dialogEvent.dismiss()
                }
            }

            fun pickImageFromGallery() {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, IMAGE_EVENT_REQUEST_CODE_)
            }


            if (addImage != null) {
                addImage.setOnClickListener {
                    pickImageFromGallery()
                }
            }

            if (btncancel != null) {
                btncancel.setOnClickListener {
                    eventlocation.text = Editable.Factory.getInstance().newEditable("")
                    editTextStartDate.text = Editable.Factory.getInstance().newEditable("")
                    editTextEndDate.text = Editable.Factory.getInstance().newEditable("")
                    eventName.text = Editable.Factory.getInstance().newEditable("")
                    eventdescription.text = Editable.Factory.getInstance().newEditable("")
                    selectedImageEvent.setImageDrawable(null)
                }
            }


            if (btnSubmit != null) {
                btnSubmit.setOnClickListener {
                    println("submit event")
                    val nameText = eventName.text.toString()
                    val descriptionText = eventdescription.text.toString()
                    val eventlocationText = eventlocation.text.toString()
                    val startDateText = editTextStartDate.text.toString()
                    val endDateText = editTextEndDate.text.toString()


//                    fun isValidDateFormat(dateString: String): Boolean {
//                        val regexPattern = """^\d{4}-\d{2}-\d{2}$""".toRegex()
//                        return regexPattern.matches(dateString)
//                    }

                    // Validate input fields
                    var isValid = true

                    if (nameText.isEmpty()) {
                        eventName.error = "Please enter a name"
                        isValid = false
                    }else if (nameText.length < 3 || nameText.length > 30) {
                        eventName.error = "Name must be between 3 and 30 characters"
                        isValid = false
                    }

                    else {
                        eventName.error = null
                    }

                    if (descriptionText.isEmpty()) {
                        eventdescription.error = "Please enter a description"
                        isValid = false
                    }else if (descriptionText.length < 10 || descriptionText.length > 100) {
                        eventdescription.error = "Description must be between 3 and 30 characters"
                        isValid = false
                    }
                    else {
                        eventdescription.error = null
                    }

                    if (eventlocationText.isEmpty()) {
                        eventlocation.error = "Please enter a location"
                        isValid = false
                    }
                    else if (eventlocationText.length < 3 || eventlocationText.length > 30) {
                        eventlocation.error = "Location must be between 3 and 30 characters"
                        isValid = false
                    }else {
                        eventlocation.error = null
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


                    // Additional date checks

                    val currentDate = Calendar.getInstance()
                    val currentDateString = formatDate(
                        currentDate.get(Calendar.YEAR),
                        currentDate.get(Calendar.MONTH) + 1,
                        currentDate.get(Calendar.DAY_OF_MONTH)
                    )

                        if (startDateText >= endDateText) {
                            editTextEndDate.error = "End date must be greater than start date"
                            isValid = false
                        } else {
                            editTextEndDate.error = null
                        }

                        if (startDateText < currentDateString) {
                            editTextStartDate.error =
                                "Start date must be equal to or greater than the current date"
                            isValid = false
                        } else {
                            editTextStartDate.error = null
                        }

                        if (endDateText <= startDateText) {
                            editTextEndDate.error = "End date must be greater than start date"
                            isValid = false
                        } else {
                            editTextEndDate.error = null
                        }


                   // val startDatereq = formatDateForRequest(startDateText)
                  //  val endDatereq = formatDateForRequest(endDateText)

                    val description = RequestBody.create(MediaType.parse("text/plain"), descriptionText)
                    val name = RequestBody.create(MediaType.parse("text/plain"), nameText)
                    val startDate = RequestBody.create(MediaType.parse("text/plain"), startDateText)
                    val endDate = RequestBody.create(MediaType.parse("text/plain"), endDateText)
                    val lieu = RequestBody.create(MediaType.parse("text/plain"), eventlocationText)


                    selectedImageEventUri?.let { uri ->
                        contentResolver.openInputStream(uri)?.use { inputStream ->
                            val mimeType = contentResolver.getType(uri) ?: "image/jpeg" // Default to "image/jpeg" if MIME type is null

                            val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"



                            val tempFile = File.createTempFile("prefix_", "_image.$extension", cacheDir)
                            FileOutputStream(tempFile).use { outputStream ->
                                inputStream.copyTo(outputStream)
                            }

                            val requestFile = RequestBody.create(MediaType.parse(mimeType), tempFile)
                            val imageBody = MultipartBody.Part.createFormData("image", "image_$extension", requestFile)

                            eventViewModel.addEvent(imageBody,name,description, lieu,startDate,endDate)

                            eventViewModel.addEventResult.observe(this, { resource ->

                                if (resource.isSuccessful) {
                                    Snackbar.make(
                                        it,
                                        "Event Created successfully.",
                                        Snackbar.LENGTH_LONG
                                    ).setBackgroundTint(Color.parseColor("#90EE90")).show()
                                    dialogEvent.dismiss()

                                }else {
                                    Snackbar.make(
                                        it,
                                        "Error fileds.",
                                        Snackbar.LENGTH_LONG
                                    ).setBackgroundTint(Color.parseColor("#90EE90")).show()
                                }


                            })


                            dialogEvent.show()
                        } ?: run {
                            Log.e("MainActivity", "Failed to open InputStream from URI")
                        }
                    }
                }
            }

            dialogEvent.show()
        }
        if (isPost) {

            val inflater = LayoutInflater.from(this)
            val dialogView = inflater.inflate(R.layout.custom_add_post_dialog, null)
            val closeButton = dialogView.findViewById<ImageView>(R.id.closeButton)

            selectedImage = dialogView.findViewById<ImageView>(R.id.imageSelected)
            selectedImage.setImageDrawable(null)

            val descriptionEditText = dialogView.findViewById<TextInputEditText>(R.id.description)


            val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
            val addEventImage = dialogView.findViewById<Button>(R.id.addEventImage)
            val okButton = dialogView.findViewById<Button>(R.id.submit)

            val dialogBuilder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)

            val dialog = dialogBuilder.create()

            fun pickImageFromGallery() {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, IMAGE_REQUEST_CODE)
            }


            if (closeButton != null) {
                closeButton.setOnClickListener {
                    dialog.dismiss()
                }
            }
            if (cancelButton != null) {
                cancelButton.setOnClickListener {
                    descriptionEditText.text = Editable.Factory.getInstance().newEditable("")
                    selectedImage.setImageDrawable(null)
                }
            }

            if (addEventImage != null) {
                addEventImage.setOnClickListener {
                    pickImageFromGallery()
                }
            }
            if (okButton != null) {
                okButton.setOnClickListener {
                    println("Button clicked")
                    val descriptionText = descriptionEditText.text.toString()

                    if (descriptionText.isBlank() || selectedImageUri == null) {
                        // Show Snackbar when validation fails
                        Snackbar.make(
                            it,
                            "Please provide both a description and an image.",
                            Snackbar.LENGTH_LONG
                        ).setBackgroundTint(Color.parseColor("#FF0000")).show()
                        return@setOnClickListener
                    }

                    val description = RequestBody.create(MediaType.parse("text/plain"), descriptionText)

                    selectedImageUri?.let { uri ->
                        contentResolver.openInputStream(uri)?.use { inputStream ->
                            val mimeType = contentResolver.getType(uri) ?: "image/jpeg" // Default to "image/jpeg" if MIME type is null

                            val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"



                            val tempFile = File.createTempFile("prefix_", "_image.$extension", cacheDir)
                            FileOutputStream(tempFile).use { outputStream ->
                                inputStream.copyTo(outputStream)
                            }

                            val requestFile = RequestBody.create(MediaType.parse(mimeType), tempFile)
                            val imageBody = MultipartBody.Part.createFormData("image", "image_$extension", requestFile)

                            postViewModel.addPost(description, imageBody)
                            postViewModel.addPostStatus.observe(this, { resource ->
                              if (resource.isSuccessful) {
                                  Snackbar.make(
                                      it,
                                      "Post Created successfully.",
                                      Snackbar.LENGTH_LONG
                                  ).setBackgroundTint(Color.parseColor("#90EE90")).show()
                                dialog.dismiss()
                              }
                                else {
                                  Snackbar.make(
                                      it,
                                      "The description contains inappropriate language..",
                                      Snackbar.LENGTH_LONG
                                  ).setBackgroundTint(Color.parseColor("#FF0000")).show()
                              }

                            })


                            dialog.show()
                        } ?: run {
                            Log.e("MainActivity", "Failed to open InputStream from URI")
                        }
                    }
                }
            }



            dialog.show()
        }
        if (isreclamation) {

            val inflater = LayoutInflater.from(this)
            val dialogView = inflater.inflate(R.layout.costom_add_reclamation_dialog, null)
            val closeButton = dialogView.findViewById<Button>(R.id.cancelButton)
            val submitButton = dialogView.findViewById<Button>(R.id.submit)
            val descriptionEditText = dialogView.findViewById<TextInputEditText>(R.id.reclamationtext)
            val aboutEditText = dialogView.findViewById<TextInputEditText>(R.id.reclamationdescription)
            val addReclamationImage = dialogView.findViewById<Button>(R.id.addReclamationImage)
            selectedImage = dialogView.findViewById<ImageView>(R.id.imageSelected)
            selectedImage.setImageDrawable(null)
            val dialogBuilder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)

            val dialog = dialogBuilder.create()

            fun pickImageFromGallery() {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, IMAGE_REQUEST_CODE)
            }

            if (closeButton != null) {
                closeButton.setOnClickListener {
                    dialog.dismiss()
                }
            }

            if (addReclamationImage != null) {
                addReclamationImage.setOnClickListener {
                    pickImageFromGallery()
                }
            }

            if (submitButton != null) {
                submitButton.setOnClickListener {
                    val descriptionText = descriptionEditText.text.toString()
                    val aboutText = aboutEditText.text.toString()

                    if (descriptionText.isBlank() || selectedImageUri == null) {
                        Snackbar.make(
                            it,
                            "Please provide both a description and an image.",
                            Snackbar.LENGTH_LONG
                        ).setBackgroundTint(Color.parseColor("#FF0000")).show()
                        return@setOnClickListener
                    }

                    val description = if (descriptionText != null) {
                        RequestBody.create(MediaType.parse("text/plain"), descriptionText)
                    } else {
                        // Handle the case when descriptionText is null
                        // For example, provide a default value or show an error
                        // For now, I'll assume an empty string as the default value
                        RequestBody.create(MediaType.parse("text/plain"), "desc")
                    }

                    val title = if (aboutText != null) {
                        RequestBody.create(MediaType.parse("text/plain"), aboutText)
                    } else {
                        // Handle the case when aboutText is null
                        // For example, provide a default value or show an error
                        // For now, I'll assume an empty string as the default value
                        RequestBody.create(MediaType.parse("text/plain"), "title")
                    }

                    selectedImageUri?.let { uri ->
                        contentResolver.openInputStream(uri)?.use { inputStream ->
                            val mimeType = contentResolver.getType(uri) ?: "image/jpeg"
                            val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"
                            val tempFile = File.createTempFile("prefix_", "_image.$extension", cacheDir)

                            FileOutputStream(tempFile).use { outputStream ->
                                inputStream.copyTo(outputStream)
                            }

                            val requestFile = RequestBody.create(MediaType.parse(mimeType), tempFile)
                            val imageBody = MultipartBody.Part.createFormData("image", "image_$extension", requestFile)


                            reclamationViewModel.addReclamation(title, description, imageBody)

                            // Additional handling if needed
                            dialog.dismiss()
                        } ?: run {
                            Log.e("MainActivity", "Failed to open InputStream from URI")
                        }
                    }
                }
            }

            dialog.show()


        }
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.nav_menu, menu)
//        val myEventsMenuItem = menu?.findItem(R.id.nav_my_events)
//
//        // Check the user's role and set the visibility accordingly
//        val userRole = SessionManager(applicationContext).getRole()
//        Log.d("UserRole", "User role: $userRole")
//
//        myEventsMenuItem?.isVisible = userRole == "partenaire"
//
//        return true
//    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.nav_calendar -> {
                replaceFragment(MyCalenderFragment())
                binding.include3.nameofcurentFragment.text = "My calender"
            }

            R.id.nav_profile -> {
                try {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("ProfileActivity", "Error starting ProfileActivity", e)
                }
            }

            R.id.nav_my_posts -> {
                replaceFragment(MyPostFrament())
                binding.include3.nameofcurentFragment.text = "My Posts"
            }

            R.id.nav_my_events -> {
                if (SessionManager(applicationContext).getRole() == "partenaire") {
                    replaceFragment(MyEventFragment())
                    binding.include3.nameofcurentFragment.text = "My Events"
                } else {

                    Toast.makeText(this, "You don't have access to My Events", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.nav_notification -> Toast.makeText(this, "notification!", Toast.LENGTH_SHORT)
                .show()

            R.id.nav_reclamation -> {
                replaceFragment(ReclamationFragment())
                binding.include3.nameofcurentFragment.text = "Reclamations"
            }

            R.id.nav_command -> Toast.makeText(this, "command!", Toast.LENGTH_SHORT).show()

            R.id.nav_logout ->
            {
                try {
                    val intent = Intent(this, LoginActivity::class.java)
                    SessionManager(applicationContext).clear()
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("LoginActivity", "Error starting LoginActivity", e)
                }
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
       override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

           when (requestCode) {
               IMAGE_REQUEST_CODE -> {
                   if (resultCode == RESULT_OK) {
                       selectedImageUri = data?.data // Store the selected image URI
                       selectedImage.setImageURI(selectedImageUri)
                   }
               }
               IMAGE_EVENT_REQUEST_CODE_ -> {
                   if (resultCode == RESULT_OK) {
                       selectedImageEventUri = data?.data // Store the selected image URI
                       selectedImageEvent.setImageURI(selectedImageEventUri)
                   }
               }
               // Add more cases for additional image request codes if needed
           }
    }

}


