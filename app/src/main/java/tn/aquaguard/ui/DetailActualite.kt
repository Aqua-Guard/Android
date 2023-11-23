package tn.aquaguard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.squareup.picasso.Picasso
import tn.aquaguard.R
import tn.aquaguard.network.SessionManager

import tn.aquaguard.databinding.ActivityDetailActualiteBinding
import tn.aquaguard.models.Avis
import tn.aquaguard.viewmodel.ActualiteViewModel
import tn.aquaguard.viewmodel.PostViewModel

class DetailActualite() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_actualite)
        val btn :Button = findViewById(R.id.ActualiteShareButton)

btn.setOnClickListener {
    val avis1 = Avis(
        Iduser = "adem11",
        actualiteTitle = "news1",
        avis = "good"
    )



}


        var username =SessionManager(applicationContext).getRole()

        print("heloooo ."+username)
        val items = listOf("good","notgood")
        val autoComplete :AutoCompleteTextView = findViewById(R.id.auto_complete_txt)
        val adapter = ArrayAdapter(this,R.layout.list_item,items)
        autoComplete.setAdapter(adapter)
        var titre =intent.getStringExtra("ACTUALITETITLE")
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)



        var namefragment : TextView = findViewById(R.id.nameofcurentFragment)
        namefragment.text = titre+username
        var image : ImageView = findViewById(R.id.ActualiteDetailimage)

        var title : TextView = findViewById(R.id.ActualiteDetailTitle)
        var description : TextView = findViewById(R.id.ActualiteDetailDescription)
        var text : TextView = findViewById(R.id.ActualiteDetailtext)
        var actualiteImage = intent.getStringExtra("ACTUAITEIMAGE")
        title.text=intent.getStringExtra("ACTUALITETITLE")
        description.text=intent.getStringExtra("ACTUAITEDESCRIPTION")
        text.text=intent.getStringExtra("ACTUALITETEXT")
        Picasso.with(this).load("http://10.0.2.2:9090/image/actualite/"+actualiteImage).fit().centerInside().into(image)

//



    }
}