package tn.aquaguard.viewHolders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tn.aquaguard.databinding.ActualiteItemBinding
import tn.aquaguard.models.Actualites

class ActualiteViewHolder (private val context: Context, val ActualiteItem :ActualiteItemBinding): RecyclerView.ViewHolder(ActualiteItem.root){

    fun setData(actualite:Actualites) {

        Picasso.with(context).load( ""+actualite.image ).fit().centerInside().into(ActualiteItem.newsImage)
        ActualiteItem.newsTitle.text=actualite.title
        ActualiteItem.newsDescription.text=actualite.description

    }



}