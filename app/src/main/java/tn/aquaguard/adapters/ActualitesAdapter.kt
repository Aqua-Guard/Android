package tn.aquaguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.ActualiteItemBinding
import tn.aquaguard.models.Actualites
import tn.aquaguard.viewHolders.ActualiteViewHolder

class ActualitesAdapter (val ActualiteList: List<Actualites>) : RecyclerView.Adapter<ActualiteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActualiteViewHolder {
        val binding = ActualiteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActualiteViewHolder(parent.context,binding)
    }

    override fun getItemCount() = ActualiteList.size

    override fun onBindViewHolder(holder: ActualiteViewHolder, position: Int) {
        holder.setData(ActualiteList[position])
    }



}