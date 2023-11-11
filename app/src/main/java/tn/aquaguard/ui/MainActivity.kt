package tn.aquaguard.ui


import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import tn.aquaguard.R
import tn.aquaguard.databinding.ActivityMainBinding
import tn.aquaguard.fragments.EventFragment
import tn.aquaguard.fragments.ForumFragment
import tn.aquaguard.fragments.HomeFragment
import tn.aquaguard.fragments.StoreFragment


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var binding: ActivityMainBinding

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
                }
                R.id.events -> {
                    println("event")
                    replaceFragment(EventFragment())
                }
                R.id.forum -> {
                    println("forum")
                    replaceFragment(ForumFragment())
                }
                R.id.store -> {
                    println("store")
                    replaceFragment(StoreFragment())
                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }


}