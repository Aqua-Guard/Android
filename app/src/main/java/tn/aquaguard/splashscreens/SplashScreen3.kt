package tn.aquaguard.splashscreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import tn.aquaguard.R

class SplashScreen3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen3)
        val btnSkip = findViewById<Button>(R.id.button)
        val btnsp1 = findViewById<ImageButton>(R.id.btnsp1)
        val btnsp2 = findViewById<ImageButton>(R.id.btnsp2)
        val btnsp3 = findViewById<ImageButton>(R.id.btnsp3)

        btnsp1.setOnClickListener {
            val intent = Intent(this, SplashScreen1::class.java)
            startActivity(intent)
        }
        btnsp2.setOnClickListener {
            val intent = Intent(this, SplashScreen2::class.java)
            startActivity(intent)
        }
        btnsp3.setOnClickListener {
            val intent = Intent(this, SplashScreen3::class.java)
            startActivity(intent)
        }

        btnSkip.setOnClickListener {
            val intent = Intent(this, SplashScreen4::class.java)
            startActivity(intent)
        }


    }
}