package tn.aquaguard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import tn.aquaguard.R
import tn.aquaguard.viewmodel.ParticipationViewModel


class DetailEventActivity : AppCompatActivity() {
    private val participationViewModel: ParticipationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        var namefragment : TextView = findViewById(R.id.nameofcurentFragment)
        namefragment.text = "Detail Event"


        var eventImage = intent.getStringExtra("eventImage")


        var name : TextView = findViewById(R.id.titleEventDetail)
        var descriptionevent : TextView = findViewById(R.id.descriptionEventDetail)
        var lieu : TextView = findViewById(R.id.eventlocation)
        var dateDF : TextView = findViewById(R.id.dateEvent)
        var image : ImageView = findViewById(R.id.eventImage)
        val btnParticipate : Button = findViewById(R.id.btnParticipate)


        name.text = intent.getStringExtra("EventName")
        descriptionevent.text = intent.getStringExtra("description")
        lieu.text = intent.getStringExtra("lieu")
        dateDF.text = intent.getStringExtra("DateDebut") + " to " + intent.getStringExtra("DateFin")
        Picasso.with(this).load("http://10.0.2.2:9090/images/event/"+eventImage).fit().centerInside().into(image)

       val eventId = intent.getStringExtra("eventId")


// Observe changes to _isEventParticipation in your activity
        participationViewModel.isEventParticipation.observe(this) { isParticipating ->
            if (isParticipating) {
                btnParticipate.isEnabled = false
                btnParticipate.text = "You are already Participated in this Event!"
            } else {
                btnParticipate.setOnClickListener {
                    eventId?.let { it1 -> participationViewModel.addParticipation(it1) }
                    Toast.makeText(this, "Participation added successfully!", Toast.LENGTH_SHORT)
                        .show()
                    btnParticipate.isEnabled = false
                    btnParticipate.text = "You are already Participated in this Event!"

                }
            }
        }

// Call the checkIfEventParticip function
        eventId?.let { participationViewModel.checkIfEventParticip(it) }


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