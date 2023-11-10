package tn.aquaguard

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout





class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout ;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar) //Ignore red line errors
        setSupportActionBar(toolbar)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_nav,
            R.string.close_nav
        )

        // Set the color of the menu icon
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


    }
}