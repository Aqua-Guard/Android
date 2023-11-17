package tn.aquaguard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import tn.aquaguard.R
import com.squareup.picasso.Picasso

class DetailProduit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produit)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val nameFragment: TextView = findViewById(R.id.nameofcurentFragment)
        nameFragment.text = "Detail Product"

        val eventImage = intent.getStringExtra("productImage")

        val name: TextView = findViewById(R.id.titleproductname)
        val descriptionProduct: TextView = findViewById(R.id.productdescription)
        val disponible: TextView = findViewById(R.id.productdisp)
        val points: TextView = findViewById(R.id.nbpoints)
        val productimage: ImageView = findViewById(R.id.productimage)

        name.text = intent.getStringExtra("productname")
        descriptionProduct.text = intent.getStringExtra("productdescription")
        disponible.text = intent.getStringExtra("IsEnabled")
        points.text = intent.getStringExtra("DateDebut") + " to " + intent.getStringExtra("DateFin")

        // Example of loading image using Picasso library
        Picasso.with(this).load("http://10.0.2.2:9090/images/event/"+eventImage).fit().centerInside().into(productimage)
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
