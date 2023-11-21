package tn.aquaguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.ItemMyPostBinding
import tn.aquaguard.models.Post
import tn.aquaguard.viewHolders.MyPostViewHolder
import tn.aquaguard.viewmodel.PostViewModel

class MyPostAdapter (val myPostList : List<Post>,private val viewModel: PostViewModel, private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<MyPostViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemMyPostBinding = ItemMyPostBinding.inflate(inflater, parent, false)
        return MyPostViewHolder(parent.context,itemMyPostBinding,viewModel,lifecycleOwner)
    }

    override fun getItemCount(): Int {
        return myPostList.size
    }

    override fun onBindViewHolder(holder: MyPostViewHolder, position: Int) {
        val myPost = myPostList[position]
        holder.setData(myPost)
    }
}