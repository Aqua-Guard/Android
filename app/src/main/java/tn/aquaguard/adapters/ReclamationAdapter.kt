package tn.aquaguard.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.R
import tn.aquaguard.databinding.ActualiteItemBinding
import tn.aquaguard.databinding.ReclamationEnvoyerItemBinding
import tn.aquaguard.models.Reclamation
import tn.aquaguard.viewHolders.ReclamationViewHolder



class ReclamationAdapter(private val reclamationList: List<Reclamation>) : RecyclerView.Adapter<ReclamationViewHolder>() {
    private var expandedPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReclamationViewHolder {
        val binding = ReclamationEnvoyerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.descriptiontext.visibility=View.GONE
        binding.buttonDiscussion.visibility=View.GONE

        binding.topHalf.setOnClickListener {
            if (expandedPosition==0){
                expandedPosition=1
                binding.descriptiontext.visibility=View.VISIBLE
                binding.buttonDiscussion.visibility=View.VISIBLE
            }
           else {
                expandedPosition=0
                binding.descriptiontext.visibility = View.GONE
                binding.buttonDiscussion.visibility = View.GONE
            }

        }
        return ReclamationViewHolder(parent.context,binding)

    }

    override fun onBindViewHolder(holder: ReclamationViewHolder, position: Int) {
        reclamationList[position]?.let { holder.setData(it) }
    }

    override fun getItemCount(): Int {
        return reclamationList.size
    }


}
