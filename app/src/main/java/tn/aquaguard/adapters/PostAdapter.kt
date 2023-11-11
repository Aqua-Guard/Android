package tn.aquaguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.ItemPostBinding
import tn.aquaguard.models.Post
import tn.aquaguard.viewHolders.PostViewHolder

class PostAdapter (val postList: List<Post>) : RecyclerView.Adapter<PostViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemPostBinding = ItemPostBinding.inflate(inflater, parent, false)
        return PostViewHolder(parent.context,itemPostBinding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.setData(post)
    }
}