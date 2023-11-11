package tn.aquaguard.viewHolders

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.adapters.CommentAdapter
import tn.aquaguard.databinding.SingelItemPersonLikeBinding
import tn.aquaguard.models.Like
import tn.aquaguard.models.Post
import tn.aquaguard.ui.DetailPostActivity

class LikeViewHolder (val itemLikeViewHolder: SingelItemPersonLikeBinding) : RecyclerView.ViewHolder(itemLikeViewHolder.root) {
    fun setData(like: Like ){
        itemLikeViewHolder.commentAvatar.setImageResource(like.userImage)
        itemLikeViewHolder.commentUsername.text = like.userName
        itemLikeViewHolder.userRole.text = like.userRole
    }
}