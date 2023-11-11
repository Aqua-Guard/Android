package tn.aquaguard.viewHolders

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.adapters.CommentAdapter
import tn.aquaguard.databinding.ItemPostBinding
import tn.aquaguard.models.Post
import tn.aquaguard.ui.DetailEventActivity
import tn.aquaguard.ui.DetailPostActivity

class PostViewHolder(private val context: Context, val itemPostBinding: ItemPostBinding) : RecyclerView.ViewHolder(itemPostBinding.root) {
    fun setData(post : Post){
        itemPostBinding.userimage.setImageResource(post.userImage)
        itemPostBinding.username.text=post.userName
        itemPostBinding.userRole.text=post.userRole
        itemPostBinding.description.text=post.description
        itemPostBinding.postimage.setImageResource(post.postImage)
        itemPostBinding.nblikes.text=post.nbLike.toString()
        itemPostBinding.nbcomments.text=post.nbComments.toString()
        itemPostBinding.nbshare.text=post.nbShare.toString()


        val commentAdapter = CommentAdapter(post.comments)
        itemPostBinding.commentsRecyclerView.layoutManager = LinearLayoutManager(itemPostBinding.root.context)
        itemPostBinding.commentsRecyclerView.adapter = commentAdapter
        itemPostBinding.infoIcon.setOnClickListener{
            val intent = Intent(context, DetailPostActivity::class.java)
            context.startActivity(intent)
        }
    }
}