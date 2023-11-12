package tn.aquaguard.viewHolders

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tn.aquaguard.adapters.CommentAdapter
import tn.aquaguard.databinding.ItemPostBinding
import tn.aquaguard.models.Post

import tn.aquaguard.ui.DetailPostActivity

class PostViewHolder(private val context: Context, val itemPostBinding: ItemPostBinding) : RecyclerView.ViewHolder(itemPostBinding.root) {
    fun setData(post : Post){


        Picasso.with(context).load("http://10.0.2.2:9090/images/user/"+post.userImage).fit().centerInside().into(itemPostBinding.userimage)

        itemPostBinding.username.text=post.userName
        itemPostBinding.userRole.text=post.userRole
        itemPostBinding.description.text=post.description

        Picasso.with(context).load("http://10.0.2.2:9090/images/post/"+post.postImage).fit().centerInside().into(itemPostBinding.postimage)

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