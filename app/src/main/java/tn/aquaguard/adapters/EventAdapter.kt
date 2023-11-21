package tn.aquaguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.SingleItemEventsBinding
import tn.aquaguard.models.Event
import tn.aquaguard.viewHolders.EventViewHolder
import tn.aquaguard.viewmodel.EventViewModel


class EventAdapter (val events: List<Event>, private val viewModel: EventViewModel, private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<EventViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = SingleItemEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(parent.context,binding,viewModel,lifecycleOwner)
    }

    override fun getItemCount(): Int {
       return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.setData(event)
    }
}