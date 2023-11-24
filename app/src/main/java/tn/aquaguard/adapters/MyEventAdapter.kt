package tn.aquaguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.SingleItemEventsBinding
import tn.aquaguard.models.Event
import tn.aquaguard.viewHolders.MyEventViewHolder
import tn.aquaguard.viewmodel.EventViewModel


class MyEventAdapter (val events: List<Event>, private val viewModel: EventViewModel, private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<MyEventViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyEventViewHolder {
        val binding = SingleItemEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyEventViewHolder(parent.context,binding,viewModel,lifecycleOwner)
    }

    override fun getItemCount(): Int {
       return events.size
    }

    override fun onBindViewHolder(holder: MyEventViewHolder, position: Int) {
        val event = events[position]
        holder.setData(event)
    }

}