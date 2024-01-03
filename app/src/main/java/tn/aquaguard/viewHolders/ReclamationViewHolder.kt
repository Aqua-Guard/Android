package tn.aquaguard.viewHolders

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tn.aquaguard.R
import tn.aquaguard.databinding.ReclamationEnvoyerItemBinding
import tn.aquaguard.models.Reclamation

class ReclamationViewHolder  (private val context: Context, val ReclamationItem : ReclamationEnvoyerItemBinding): RecyclerView.ViewHolder(ReclamationItem.root){


    fun setData(reclamation: Reclamation) {


            Picasso.with(context)
                .load("https://aquaguard-tux1.onrender.com/images/reclamation/" + reclamation.image).fit()
                .centerInside().into(ReclamationItem.Imagereclamation)
            ReclamationItem.titlereclamation.text = reclamation.title
            ReclamationItem.descriptiontext.text = reclamation.description
            ReclamationItem.reclamationid.text = reclamation.idreclamation
             ReclamationItem.date.text=reclamation.date
        if (reclamation.answered) {
            // Set background color to a color from the drawable resources
            ReclamationItem.topHalf.background =ContextCompat.getDrawable(context, R.drawable.graduated_color_2)        }




ReclamationItem.buttonDiscussion.setOnClickListener {

}


    }


    }