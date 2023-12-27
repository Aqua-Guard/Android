package tn.aquaguard.viewHolders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tn.aquaguard.databinding.ReclamationEnvoyerItemBinding
import tn.aquaguard.models.Reclamation

class ReclamationViewHolder  (private val context: Context, val ReclamationItem : ReclamationEnvoyerItemBinding): RecyclerView.ViewHolder(ReclamationItem.root){


    fun setData(reclamation: Reclamation) {
        Picasso.with(context).load( "http://10.0.2.2:9090/images/reclamation/"+reclamation.image ).fit().centerInside().into(ReclamationItem.Imagereclamation)
        ReclamationItem.titlereclamation.text=reclamation.title
        ReclamationItem.descriptiontext.text=reclamation.description
        ReclamationItem.reclamationid.text=reclamation.idreclamation

ReclamationItem.buttonDiscussion.setOnClickListener {

}


    }


    }