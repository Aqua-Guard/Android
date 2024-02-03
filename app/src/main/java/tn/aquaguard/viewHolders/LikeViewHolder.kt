package tn.aquaguard.viewHolders

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tn.aquaguard.adapters.CommentAdapter
import tn.aquaguard.databinding.SingelItemPersonLikeBinding
import tn.aquaguard.models.Like
import tn.aquaguard.models.Post
import tn.aquaguard.ui.DetailPostActivity

class LikeViewHolder (private val context: Context,val itemLikeViewHolder: SingelItemPersonLikeBinding) : RecyclerView.ViewHolder(itemLikeViewHolder.root) {
    fun setData(like: Like ){
        Picasso.with(context).load("https://aquaguard-tux1.onrender.com/images/user/"+like.likeAvatar).fit().centerInside().into(itemLikeViewHolder.likeAvatar)
        itemLikeViewHolder.likeUsername.text = like.likeUsername
    }
}