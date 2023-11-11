package tn.aquaguard.viewHolders

import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.ItemCommentBinding
import tn.aquaguard.models.Comment



class CommentViewHolder(val itemCommentBinding: ItemCommentBinding ) : RecyclerView.ViewHolder(itemCommentBinding.root) {
    fun setData(comment : Comment){
        itemCommentBinding.commentAvatar.setImageResource(comment.commentAvatar)
        itemCommentBinding.commentUsername.text = comment.commentUsername
        itemCommentBinding.commentText.text = comment.comment
    }
}