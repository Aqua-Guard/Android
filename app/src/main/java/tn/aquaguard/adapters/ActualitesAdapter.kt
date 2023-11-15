package tn.aquaguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.ActualiteItemBinding
import tn.aquaguard.models.Actualites

class ActualitesAdapter (val ActualiteList: MutableList<Actualites>) : RecyclerView.Adapter<ActualitesAdapter.ActualiteHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActualitesAdapter.ActualiteHolder {
        val binding = ActualiteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActualiteHolder(binding)
    }

    override fun onBindViewHolder(holder: ActualitesAdapter.ActualiteHolder, position: Int) {
        holder.setData(ActualiteList[position])
    }

    override fun getItemCount() = ActualiteList.size
    inner class ActualiteHolder(val binding: ActualiteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(act: Actualites){
            with(act){
                binding.newsTitle.text = title
                binding.newsDescription.text = description
                binding.newsImage.setImageResource(image)

            }
        }
    }
}