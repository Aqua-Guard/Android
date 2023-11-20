package tn.aquaguard.viewHolders

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tn.aquaguard.databinding.ActualiteItemBinding
import tn.aquaguard.models.Actualites
import tn.aquaguard.ui.DetailActualite


class ActualiteViewHolder (private val context: Context, val ActualiteItem :ActualiteItemBinding): RecyclerView.ViewHolder(ActualiteItem.root){

    fun setData(actualite:Actualites) {


        Picasso.with(context).load( ""+actualite.image ).fit().centerInside().into(ActualiteItem.newsImage)
        ActualiteItem.newsTitle.text=actualite.title
        ActualiteItem.newsDescription.text=actualite.description

        ActualiteItem.content.setOnClickListener {
            val intent = Intent(context, DetailActualite::class.java)
            intent.putExtra("ACTUALITETITLE", actualite.title)
            intent.putExtra("ACTUALITETEXT", actualite.text)
            intent.putExtra("ACTUAITEDESCRIPTION", actualite.description)


            context.startActivity(intent)

    }
        ActualiteItem.ActualiteShareButton.setOnClickListener {
    var sendintent =Intent().apply {
        action=Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT,"http://127.0.0.1:5500/index.html")
        type="text/plain"
    }
val shareIntent= Intent.createChooser(sendintent,"a5tar fisa3")
    context.startActivity(shareIntent)
}



}}