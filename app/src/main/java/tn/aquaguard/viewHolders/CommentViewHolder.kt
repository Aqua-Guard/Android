package tn.aquaguard.viewHolders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import tn.aquaguard.databinding.ItemCommentBinding
import tn.aquaguard.models.Comment
import tn.aquaguard.viewmodel.PostViewModel


class CommentViewHolder(private val context: Context, val itemCommentBinding: ItemCommentBinding,private val viewModel: PostViewModel) : RecyclerView.ViewHolder(itemCommentBinding.root) {
    fun setData(comment : Comment){


        itemCommentBinding.commentdelete.setOnClickListener {
            viewModel.deleteComment(comment.idComment)
            Snackbar.make(itemCommentBinding.root, "Comment deleted successfully", Snackbar.LENGTH_SHORT).show()
        }
        Picasso.with(context).load("http://10.0.2.2:9090/images/user/"+comment.commentAvatar).fit().centerInside().into(itemCommentBinding.commentAvatar)


        itemCommentBinding.commentUsername.text = comment.commentUsername
        itemCommentBinding.commentText.text = comment.comment
    }
}