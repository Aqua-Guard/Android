package tn.aquaguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.ItemCommentBinding
import tn.aquaguard.models.Comment
import tn.aquaguard.models.Post
import tn.aquaguard.viewHolders.CommentViewHolder
import tn.aquaguard.viewmodel.PostViewModel


class CommentAdapter (var commentList: List<Comment>,private val viewModel: PostViewModel,private val lifecycleOwner: LifecycleOwner, private val maxItemCount: Int = -1, private val isMyPostFragment: Boolean = false) : RecyclerView.Adapter<CommentViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemCommentBinding = ItemCommentBinding.inflate(inflater, parent, false)
        return CommentViewHolder(parent.context,itemCommentBinding,viewModel,lifecycleOwner)
    }

    override fun getItemCount(): Int {
        return if (maxItemCount > 0) minOf(commentList.size, maxItemCount) else commentList.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]
        holder.setData(comment,isMyPostFragment)
    }

    fun updateData(newData: List<Comment>) {
        this.commentList = newData
        notifyDataSetChanged()
    }
}