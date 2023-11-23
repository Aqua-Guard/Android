package tn.aquaguard.viewHolders

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tn.aquaguard.databinding.ActualiteItemBinding
import tn.aquaguard.models.Actualites
import tn.aquaguard.network.SessionManager
import tn.aquaguard.ui.DetailActualite


class ActualiteViewHolder (private val context: Context, val ActualiteItem :ActualiteItemBinding): RecyclerView.ViewHolder(ActualiteItem.root){

    fun setData(actualite:Actualites) {

        var username =SessionManager(context).getUsername()
        Picasso.with(context).load( "http://10.0.2.2:9090/image/actualite/"+actualite.image ).fit().centerInside().into(ActualiteItem.newsImage)
        ActualiteItem.newsTitle.text=actualite.title
        ActualiteItem.newsDescription.text=actualite.description

        ActualiteItem.content.setOnClickListener {
            val intent = Intent(context, DetailActualite::class.java)
            intent.putExtra("USERNAME",username)
            intent.putExtra("ACTUALITETITLE", actualite.title)
            intent.putExtra("ACTUALITEID", actualite.idactualite)
            intent.putExtra("ACTUALITETEXT", actualite.text)
            intent.putExtra("ACTUAITEDESCRIPTION", actualite.description)
            intent.putExtra("ACTUAITEIMAGE", actualite.image)


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



}
}