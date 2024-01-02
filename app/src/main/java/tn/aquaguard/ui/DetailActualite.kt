package tn.aquaguard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import tn.aquaguard.R
import tn.aquaguard.databinding.ActivityDetailActualiteBinding
import tn.aquaguard.models.Avis
import tn.aquaguard.network.SessionManager
import tn.aquaguard.repository.ActualiteRepository
import tn.aquaguard.viewmodel.ActualiteViewModel


class DetailActualite() : AppCompatActivity() {
    private val viewModel by viewModels<ActualiteViewModel>()
    private val repository = ActualiteRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
var like =0
        var deslike =0
        setContentView(R.layout.activity_detail_actualite)
     var textiii :EditText=findViewById(R.id.commentdisliketext)
        var textii :EditText=findViewById(R.id.commentliketext)
        var buttonsubmit : Button =findViewById(R.id.btnSubmitact)
        var back1 :LinearLayout=findViewById(R.id.likebackround)
        var back2 :LinearLayout=findViewById(R.id.dislikelikebackround)
        var viewsnumber :TextView=findViewById(R.id.viewsnumber)
        var likesnumber :TextView=findViewById(R.id.likesnumber)
        likesnumber.visibility=View.GONE
var comoentaire =""
        //// invisible parts
        val actualiteViews = intent.getIntExtra("ACTUAITEVIEWS", 0)
        val actualitelikes = intent.getIntExtra("ACTUAITELIKE", 0)
        val actualitedislikes = intent.getIntExtra("ACTUAITEDISLIKE", 0)
        viewsnumber.setText("${actualiteViews} Views")
        var idatualite = intent.getStringExtra("ACTUALITEID")
        viewModel.getlikeandcom(idatualite!!)
        buttonsubmit.visibility=View.GONE
        textii.visibility=View.GONE
        textiii.visibility=View.GONE
        var button2 : Button =findViewById(R.id.dislikebuttonllll)
        button2?.setOnClickListener {
            if (deslike==0) {
                comoentaire=""
                textii.setText(comoentaire)
                textiii.setText(comoentaire)
                textiii.visibility = View.VISIBLE
                buttonsubmit.visibility = View.VISIBLE
                back2.setBackgroundResource(R.drawable.customdislikebackround)
                back1.setBackgroundResource(R.drawable.customlikebackround)
                textii.visibility = View.GONE
                viewModel.viewModelScope.launch {
                    val result = viewModel.addlike(idatualite!!, 2)
                    if (result is ActualiteViewModel.AddLikeResult.Success) {
                        // Handle success
                    } else if (result is ActualiteViewModel.AddLikeResult.Error) {
                        // Handle error
                    }
                }
                deslike=1
                like=0
            }else {
                comoentaire=""
                textiii.setText(comoentaire)
                textii.setText(comoentaire)
                textiii.visibility = View.GONE
                buttonsubmit.visibility = View.GONE
                textiii.visibility = View.GONE
                likesnumber.visibility=View.GONE
                back2.setBackgroundResource(R.drawable.customdislikebackround)
                back1.setBackgroundResource(R.drawable.customlikebackround)
                viewModel.viewModelScope.launch {
                    val result = viewModel.addlike(idatualite!!, 2)
                    if (result is ActualiteViewModel.AddLikeResult.Success) {
                        // Handle success
                    } else if (result is ActualiteViewModel.AddLikeResult.Error) {
                        // Handle error
                    }
                }
                deslike=0
            }

        }
        var button1 : Button =findViewById(R.id.likebuttonllll)
        button1?.setOnClickListener {
            if(like==0) {
                comoentaire=""

                viewModel.viewModelScope.launch {
                    val result = viewModel.addlike(idatualite!!, 1)
                    if (result is ActualiteViewModel.AddLikeResult.Success) {
                        // Handle success
                    } else if (result is ActualiteViewModel.AddLikeResult.Error) {
                        // Handle error
                    }
                }
                textiii.setText(comoentaire)
                textii.visibility = View.VISIBLE
                textii.setText(comoentaire)
                buttonsubmit.visibility = View.VISIBLE
                back1.setBackgroundResource(R.drawable.customlikebackround)
                textiii.visibility = View.GONE
                like=1
                deslike=0
                back2.setBackgroundResource(R.drawable.customdislikebackround)
            } else{
                comoentaire=""
                textii.setText(comoentaire)
                textiii.setText(comoentaire)
                textiii.visibility = View.GONE
                likesnumber.visibility=View.GONE

                viewModel.viewModelScope.launch {
                    val result = viewModel.addlike(idatualite!!, 1)
                    if (result is ActualiteViewModel.AddLikeResult.Success) {
                        // Handle success
                    } else if (result is ActualiteViewModel.AddLikeResult.Error) {
                        // Handle error
                    }
                }
                textii.visibility = View.GONE
                buttonsubmit.visibility = View.GONE
                like=0
                back1.setBackgroundResource(R.drawable.customlikebackround)
                back2.setBackgroundResource(R.drawable.customdislikebackround)
            }


        }


        /////////////

        var userid = SessionManager(applicationContext).getId()


        var titre =intent.getStringExtra("ACTUALITETITLE")
        if (titre!!.length>15){
            title =titre.substring(0, 15)+"..."
        }
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        var namefragment : TextView = findViewById(R.id.nameofcurentFragment)

        namefragment.gravity = Gravity.START
        namefragment.text = if (titre.toString()!!.length>15){
           titre.substring(0, 25)+"...";

        }else titre

//        testing the avis



buttonsubmit.setOnClickListener {
    if(deslike==1) {
        var comment = textiii.text.toString()
        textiii.setText(comment)
        viewModel.viewModelScope.launch {
            val result = viewModel.addcomment(idatualite!!, comment)
            if (result is ActualiteViewModel.AddLikeResult.Success) {
                // Handle success
            } else if (result is ActualiteViewModel.AddLikeResult.Error) {
                // Handle error
            }
        }
    }else if(like==1){
        var comment = textii.text.toString()
        textii.setText(comment)
        viewModel.viewModelScope.launch {
            val result = viewModel.addcomment(idatualite!!, comment)
            if (result is ActualiteViewModel.AddLikeResult.Success) {
                // Handle success
            } else if (result is ActualiteViewModel.AddLikeResult.Error) {
                // Handle error
            }
        }
    }

    Toast.makeText(
        applicationContext,
        "your review has been sent ",
        Toast.LENGTH_SHORT
    ).show()
}



        //declaring the containers in the ui
        var image : ImageView = findViewById(R.id.ActualiteDetailimage)
        var title : TextView = findViewById(R.id.ActualiteDetailTitle)
        var description : TextView = findViewById(R.id.ActualiteDetailDescription)
        var text : TextView = findViewById(R.id.ActualiteDetailtext)


        // intent get extra
        var actualiteImage = intent.getStringExtra("ACTUAITEIMAGE")
        title.text=intent.getStringExtra("ACTUALITETITLE")
        description.text=intent.getStringExtra("ACTUAITEDESCRIPTION")
        text.text=intent.getStringExtra("ACTUALITETEXT")
        Picasso.with(this).load("http://10.0.2.2:9090/images/actualite/"+actualiteImage).fit().centerInside().into(image)

 ////////////////////////////////////////////test//////////////////

        viewModel.getviewResult.observe(this, Observer { avis ->
            if (avis != null) {
                if(avis.like===2) {
                    textiii.visibility = View.VISIBLE
                    buttonsubmit.visibility = View.VISIBLE
                    comoentaire=avis.comment
                    textiii.setText(comoentaire)
                    back2.setBackgroundResource(R.drawable.customdislikebackround)
                    back1.setBackgroundResource(R.drawable.customlikebackround)
                    deslike=1
                }else if (avis.like===1){
                    textii.visibility = View.VISIBLE
                    comoentaire=avis.comment
                    textii.setText(comoentaire)
                    buttonsubmit.visibility = View.VISIBLE
                    back2.setBackgroundResource(R.drawable.customdislikebackround)
                    back1.setBackgroundResource(R.drawable.customlikebackround)

                    like=1
                }
            }
        })

    }
}