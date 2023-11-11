package tn.aquaguard.viewHolders

import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.SingleItemEventsBinding
import tn.aquaguard.models.Event

class EventViewHolder (val itemEventBinding: SingleItemEventsBinding) : RecyclerView.ViewHolder(itemEventBinding.root) {
    fun setData( event : Event){
        itemEventBinding.eventImage.setImageResource(event.image)
        itemEventBinding.eventTitle.text = event.name
        itemEventBinding.eventDescription.text = event.description
        itemEventBinding.dateEvent.text = event.dateDebut.toString() +" to "+event.dateFin.toString()
        itemEventBinding.eventlocation.text = event.lieu

    }

}