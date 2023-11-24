package tn.aquaguard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import tn.aquaguard.R
import tn.aquaguard.models.Avis
import tn.aquaguard.network.SessionManager
import tn.aquaguard.viewmodel.ActualiteViewModel


class DetailActualite() : AppCompatActivity() {
    private val viewModel by viewModels<ActualiteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_actualite)



        /////////////
        var userid = SessionManager(applicationContext).getId()
        var idatualite = intent.getStringExtra("ACTUALITEID")
        //set up the toolbar
        var titre =intent.getStringExtra("ACTUALITETITLE")
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        var namefragment : TextView = findViewById(R.id.nameofcurentFragment)
        namefragment.text = titre

//        testing the avis
        val btn = findViewById<Button>(R.id.ActualiteShareButton)
        btn.setOnClickListener {


}

        //trying to call user_id



        //set up of the dropdown menu
        val items = listOf("true","not true","i dont think so","may be")

        val autoComplete :AutoCompleteTextView = findViewById(R.id.auto_complete_txt)
        val adapter = ArrayAdapter(this,R.layout.list_item,items)
        autoComplete.setAdapter(adapter)





        //add an action while clicking on the items of the dropdown menu
// ui/DetailActualite.kt

// ... (existing code)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            val itemSelected = adapter.getItem(i)
            val actualiteID = idatualite

            if (actualiteID != null) {
                val avis = userid?.let { Avis(it, actualiteID, itemSelected.toString()) }
                if (avis != null) {
                    viewModel.addOrUpdateAvis(avis)
                }
            }
        }

// ... (existing code)

        viewModel.addOrUpdateAvisResult.observe(this, Observer { result ->
            if (result == "ok") {
                Toast.makeText(
                    applicationContext,
                    "Avis added/updated successfully",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Error adding/updating avis",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

// ... (existing code)



        //declaring the containers in the ui
        var image : ImageView = findViewById(R.id.ActualiteDetailimage)
        var title : TextView = findViewById(R.id.ActualiteDetailTitle)
        var description : TextView = findViewById(R.id.ActualiteDetailDescription)
        var text : TextView = findViewById(R.id.ActualiteDetailtext)
        var last : TextView =findViewById(R.id.lastrating)


        // intent get extra
        var actualiteImage = intent.getStringExtra("ACTUAITEIMAGE")
        title.text=intent.getStringExtra("ACTUALITETITLE")
        description.text=intent.getStringExtra("ACTUAITEDESCRIPTION")
        text.text=intent.getStringExtra("ACTUALITETEXT")
        Picasso.with(this).load("http://10.0.2.2:9090/image/actualite/"+actualiteImage).fit().centerInside().into(image)

 ////////////////////////////////////////////test//////////////////

        viewModel.avisByIds.observe(this, Observer { avis ->
            if (avis != null) {
                Toast.makeText(
                    applicationContext,
                    "Avis exists",
                    Toast.LENGTH_SHORT
                ).show()

                last.text="u have all rady rate this news as ${avis.avis}"
            } else {
                Toast.makeText(
                    applicationContext,
                    "u have rating this n",
                    Toast.LENGTH_SHORT
                ).show()
                last.text=""
            }
        })
        val userId = "6554092c88519a44716228d4"
        if (idatualite != null) {
            viewModel.getAvisByIds(userId, idatualite)
        }

    }
}