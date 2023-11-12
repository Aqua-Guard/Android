package tn.aquaguard.viewHolders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tn.aquaguard.databinding.ItemCommentBinding
import tn.aquaguard.models.Comment



class CommentViewHolder(private val context: Context, val itemCommentBinding: ItemCommentBinding ) : RecyclerView.ViewHolder(itemCommentBinding.root) {
    fun setData(comment : Comment){

        Picasso.with(context).load("http://10.0.2.2:9090/images/user/"+comment.commentAvatar).fit().centerInside().into(itemCommentBinding.commentAvatar)


        itemCommentBinding.commentUsername.text = comment.commentUsername
        itemCommentBinding.commentText.text = comment.comment
    }
}