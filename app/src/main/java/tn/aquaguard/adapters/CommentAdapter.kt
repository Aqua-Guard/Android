package tn.aquaguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.ItemCommentBinding
import tn.aquaguard.models.Comment
import tn.aquaguard.viewHolders.CommentViewHolder



class CommentAdapter (val commentList: List<Comment>) : RecyclerView.Adapter<CommentViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemCommentBinding = ItemCommentBinding.inflate(inflater, parent, false)
        return CommentViewHolder(itemCommentBinding)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]
        holder.setData(comment)
    }
}