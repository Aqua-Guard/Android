package tn.aquaguard.ui


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.ViewGroup
import android.view.Window
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import tn.aquaguard.R
import tn.aquaguard.databinding.ActivityMainBinding
import tn.aquaguard.fragments.EventFragment
import tn.aquaguard.fragments.ForumFragment
import tn.aquaguard.fragments.HomeFragment
import tn.aquaguard.fragments.StoreFragment
import tn.aquaguard.models.AddEventRequest
import tn.aquaguard.models.Event
import tn.aquaguard.utils.ImagePickerCallback
import tn.aquaguard.utils.ImagePickerUtils
import tn.aquaguard.viewmodel.EventViewModel


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var binding: ActivityMainBinding
    private lateinit var imagePickerCallback: ImagePickerCallback
    private lateinit var imagePickerUtils: ImagePickerUtils
    private val eventViewModel: EventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

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

    private fun showBottomDialog() {
        val isEvent = binding.include3.nameofcurentFragment.text == "Event"

        if (isEvent) {
            val onBtnAddImageClick: () -> Unit = {


            }

            val onBtnSubmitClick: (AddEventRequest) -> Unit = { event ->
                lifecycleScope.launch {
                    println("-----------"+event);

                    eventViewModel.addEvent(event)


                    val response = eventViewModel.response

                    // Check the response code after the coroutine completes
                    if (response?.isSuccessful == true) {
                        // Check for the expected 201 status code
                        if (response.code() == 201) {
                            // Successful creation of an event
                            Toast.makeText(this@MainActivity, "Event added successfully!", Toast.LENGTH_SHORT).show()
                        } else {
                            // Handle unexpected status codes
                            Toast.makeText(this@MainActivity, "Unexpected status code: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Handle unsuccessful response
                        Toast.makeText(this@MainActivity, "Error: ${response?.code()}", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            DialogAddEvent(this, onBtnAddImageClick, onBtnSubmitClick)
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_notification -> Toast.makeText(this, "notification!", Toast.LENGTH_SHORT).show()

            R.id.nav_reclamation -> Toast.makeText(this, "reclmation!", Toast.LENGTH_SHORT).show()

            R.id.nav_command ->Toast.makeText(this, "command!", Toast.LENGTH_SHORT).show()

            R.id.nav_logout -> Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }



}


