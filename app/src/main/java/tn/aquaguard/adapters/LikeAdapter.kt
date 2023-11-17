package tn.aquaguard.adapters;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.SingelItemPersonLikeBinding
import tn.aquaguard.databinding.SingleItemEventsBinding
import tn.aquaguard.models.Like;
import tn.aquaguard.viewHolders.EventViewHolder
import tn.aquaguard.viewHolders.LikeViewHolder

public class LikeAdapter (val likeList: List<Like>) : RecyclerView.Adapter<LikeViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder {
        val itemPersonLikeBinding = SingelItemPersonLikeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LikeViewHolder(parent.context,itemPersonLikeBinding)
    }

    override fun getItemCount(): Int {
        return likeList.size
    }

    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) {
        val like = likeList[position]
        holder.setData(like)
    }

}