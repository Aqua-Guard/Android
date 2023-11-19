package tn.aquaguard.splashscreens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import tn.aquaguard.R
import tn.aquaguard.ui.ProfileActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_SCREEN_DELAY = 2000 // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        Handler().postDelayed({
            // Start the main activity after the splash screen duration
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_DELAY.toLong())


    }
}