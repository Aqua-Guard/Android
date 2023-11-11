package tn.aquaguard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import tn.aquaguard.R

class DetailEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        var namefragment : TextView = findViewById(R.id.nameofcurentFragment)
        namefragment.text = "Detail Event"


        // Enable the back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val whiteArrow = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
        supportActionBar?.setHomeAsUpIndicator(whiteArrow)


        // Set the back arrow click listener
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}