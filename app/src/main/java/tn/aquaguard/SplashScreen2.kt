package tn.aquaguard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton


class SplashScreen2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen2)
        val rootLayout = findViewById<View>(R.id.splash_screen2)
        val btnSkip = findViewById<Button>(R.id.button)
        val btnsp1 = findViewById<ImageButton>(R.id.btnsp1)
        val btnsp2 = findViewById<ImageButton>(R.id.btnsp2)
        val btnsp3 = findViewById<ImageButton>(R.id.btnsp3)

        btnSkip.setOnClickListener {
            val intent = Intent(this, SplashScreen4::class.java)
            startActivity(intent)
        }

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

        rootLayout.setOnClickListener {
            val intent = Intent(this, SplashScreen3::class.java)
            startActivity(intent)
        }
    }
}