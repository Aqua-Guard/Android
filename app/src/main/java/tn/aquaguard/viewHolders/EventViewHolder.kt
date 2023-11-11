package tn.aquaguard.viewHolders

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.SingleItemEventsBinding
import tn.aquaguard.models.Event
import tn.aquaguard.ui.DetailEventActivity
import java.text.SimpleDateFormat
import java.util.Locale

class EventViewHolder (private val context: Context, val itemEventBinding: SingleItemEventsBinding) : RecyclerView.ViewHolder(itemEventBinding.root) {
    fun setData( event : Event){
        itemEventBinding.eventImage.setImageResource(event.image)
        itemEventBinding.eventTitle.text = event.name
        itemEventBinding.eventDescription.text = event.description

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateDebutFormatted = dateFormat.format(event.dateDebut)
        val dateFinFormatted = dateFormat.format(event.dateFin)

        itemEventBinding.dateEvent.text = dateDebutFormatted +" to "+ dateFinFormatted
        itemEventBinding.eventlocation.text = event.lieu
        itemEventBinding.infobtn.setOnClickListener{
            val intent = Intent(context, DetailEventActivity::class.java)
            context.startActivity(intent)
        }

    }

}