package tn.aquaguard.viewHolders

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tn.aquaguard.databinding.SingleItemEventsBinding
import tn.aquaguard.models.Event
import tn.aquaguard.ui.DetailEventActivity
import tn.aquaguard.viewmodel.EventViewModel

import java.text.SimpleDateFormat
import java.util.Locale

class EventViewHolder (private val context: Context, val itemEventBinding: SingleItemEventsBinding  ,private val viewModel: EventViewModel, private val lifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(itemEventBinding.root) {
    fun setData( event : Event){

        Picasso.with(context).load("https://aquaguard-tux1.onrender.com/images/event/"+event.eventImage).fit().centerInside().into(itemEventBinding.eventImage)
        itemEventBinding.eventTitle.text = event.eventName
        itemEventBinding.eventDescription.text = event.description
        Log.d("khkhk: ",event.description)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateDebutFormatted = dateFormat.format(event.DateDebut)
        val dateFinFormatted = dateFormat.format(event.DateFin)

        itemEventBinding.dateEvent.text = "$dateDebutFormatted to $dateFinFormatted"
        itemEventBinding.eventlocation.text = event.lieu
        itemEventBinding.infobtnmenu.visibility = View.GONE
        itemEventBinding.infobtn.setOnClickListener{
            val intent = Intent(context, DetailEventActivity::class.java)
            intent.putExtra("EventName",event.eventName)
            intent.putExtra("description",event.description)
            intent.putExtra("DateDebut",dateDebutFormatted)
            intent.putExtra("DateFin",dateFinFormatted)
            intent.putExtra("eventImage",event.eventImage)
            intent.putExtra("lieu",event.lieu)
            intent.putExtra("eventId",event.idEvent)
            context.startActivity(intent)


        }

    }

}