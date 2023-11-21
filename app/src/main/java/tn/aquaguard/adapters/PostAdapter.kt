package tn.aquaguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.ItemPostBinding
import tn.aquaguard.models.Post
import tn.aquaguard.viewHolders.PostViewHolder
import tn.aquaguard.viewmodel.PostViewModel

class PostAdapter (var postList: List<Post>,private val viewModel: PostViewModel, private val lifecycleOwner: LifecycleOwner ) : RecyclerView.Adapter<PostViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemPostBinding = ItemPostBinding.inflate(inflater, parent, false)
        return PostViewHolder(parent.context,itemPostBinding,viewModel,lifecycleOwner)
    }
    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.setData(post)
    }
}