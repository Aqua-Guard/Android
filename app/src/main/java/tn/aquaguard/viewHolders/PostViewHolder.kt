package tn.aquaguard.viewHolders

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import tn.aquaguard.R
import tn.aquaguard.adapters.CommentAdapter
import tn.aquaguard.databinding.ItemPostBinding
import tn.aquaguard.models.Post

import tn.aquaguard.ui.DetailPostActivity
import tn.aquaguard.viewmodel.PostViewModel

class PostViewHolder(private val context: Context, val itemPostBinding: ItemPostBinding,private val viewModel: PostViewModel) : RecyclerView.ViewHolder(itemPostBinding.root) {


    fun setData(post: Post) {

        itemPostBinding.sendCommentButton.setOnClickListener {
            val commentText = itemPostBinding.commentInput.text.toString()
            if (commentText.isNotBlank()) {
                viewModel.addCommentToPost(post.idPost, commentText)
                itemPostBinding.commentInput.text.clear()
            } else {
                Snackbar.make(itemPostBinding.root,"Comment cannot be empty",Snackbar.LENGTH_SHORT).show()
            }
        }
        itemPostBinding.likeicon.setOnClickListener {
            if (post.isLiked) {
                unlikePost(post.idPost)
                post.isLiked = false // Update the post's liked status
                itemPostBinding.likeicon.setImageResource(R.drawable.baseline_favorite_border_24)
                Snackbar.make(itemPostBinding.root, "You have unliked the post", Snackbar.LENGTH_SHORT).show()
            } else {
                likePost(post.idPost)
                post.isLiked = true // Update the post's liked status
                itemPostBinding.likeicon.setImageResource(R.drawable.baseline_favorite_24)
                Snackbar.make(itemPostBinding.root, "You liked the post", Snackbar.LENGTH_SHORT).show()
            }

        }
            Picasso.with(context).load("http://10.0.2.2:9090/images/user/" + post.userImage).fit()
                .centerInside().into(itemPostBinding.userimage)

            itemPostBinding.username.text = post.userName
            itemPostBinding.userRole.text = post.userRole
            itemPostBinding.description.text = post.description

            Picasso.with(context).load("http://10.0.2.2:9090/images/post/" + post.postImage).fit()
                .centerInside().into(itemPostBinding.postimage)

            itemPostBinding.nblikes.text = post.nbLike.toString()
            itemPostBinding.nbcomments.text = post.nbComments.toString()
            itemPostBinding.nbshare.text = post.nbShare.toString()



            val commentAdapter = CommentAdapter(post.comments,viewModel)
            itemPostBinding.commentsRecyclerView.layoutManager =
                LinearLayoutManager(itemPostBinding.root.context)
            itemPostBinding.commentsRecyclerView.adapter = commentAdapter
            itemPostBinding.infoIcon.setOnClickListener {
                val intent = Intent(context, DetailPostActivity::class.java)
                intent.putExtra(
                    "POST_ID",
                    post.idPost
                ) // Assuming 'idPost' is the attribute for the post ID
                context.startActivity(intent)
            }

        }

    private fun unlikePost(idPost: String) {
        viewModel.dislikePost(idPost)
    }

    private fun likePost(idPost: String) {
        viewModel.addLike(idPost)
    }

    }




