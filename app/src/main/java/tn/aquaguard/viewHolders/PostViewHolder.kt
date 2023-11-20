package tn.aquaguard.viewHolders

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
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

class PostViewHolder(
    private val context: Context,
    val itemPostBinding: ItemPostBinding,
    private val viewModel: PostViewModel,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(itemPostBinding.root) {


    fun setData(post: Post) {

        itemPostBinding.sendCommentButton.setOnClickListener {
            val commentText = itemPostBinding.commentInput.text.toString()
            if (commentText.isNotBlank()) {
                viewModel.addCommentToPost(post.idPost, commentText)
                itemPostBinding.commentInput.text.clear()
                Snackbar.make(itemPostBinding.root, "Comment added", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(
                    itemPostBinding.root,
                    "Comment cannot be empty",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.checkIfPostLiked(post.idPost)
        viewModel.isPostLiked.observe(lifecycleOwner) { isliked ->
            if (isliked) {
                itemPostBinding.likeicon.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                itemPostBinding.likeicon.setImageResource(R.drawable.baseline_favorite_border_24)

            }
        }

        viewModel.checkIfPostLiked(post.idPost)
        viewModel.isPostLiked.observe(lifecycleOwner) { isLiked ->

            itemPostBinding.likeicon.setImageResource(
                if (isLiked) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
            )
        }


        itemPostBinding.nblikes.text = post.nbLike.toString()


        itemPostBinding.likeicon.setOnClickListener {
            val isLiked = viewModel.isPostLiked.value ?: false
            if (isLiked) {

                unlikePost(post.idPost)
                post.nbLike = (post.nbLike - 1).coerceAtLeast(0)
                itemPostBinding.nblikes.text = post.nbLike.toString()
                Snackbar.make(
                    itemPostBinding.root,
                    "You have unliked the post!",
                    Snackbar.LENGTH_SHORT
                ).show()


            } else {

                likePost(post.idPost)
                post.nbLike += 1
                itemPostBinding.nblikes.text = post.nbLike.toString()
                Snackbar.make(itemPostBinding.root, "You liked the post!", Snackbar.LENGTH_SHORT)
                    .show()
            }
            viewModel.checkIfPostLiked(post.idPost) // Update the like status in the view model
        }
        Picasso.with(context).load("http://10.0.2.2:9090/images/user/" + post.userImage).fit()
            .centerInside().into(itemPostBinding.userimage)

        itemPostBinding.username.text = post.userName
        itemPostBinding.userRole.text = post.userRole
        itemPostBinding.description.text = post.description

        Picasso.with(context).load("http://10.0.2.2:9090/images/post/" + post.postImage).fit()
            .centerInside().into(itemPostBinding.postimage)

        itemPostBinding.nbcomments.text = post.nbComments.toString()
        itemPostBinding.nbshare.text = post.nbShare.toString()


        val commentAdapter = CommentAdapter(post.comments, viewModel, lifecycleOwner, 3)
        itemPostBinding.commentsRecyclerView.layoutManager =
            LinearLayoutManager(itemPostBinding.root.context)


        itemPostBinding.commentsRecyclerView.adapter = commentAdapter

        viewModel.commentsLiveData.observe(lifecycleOwner) { updatedComments ->

            if (updatedComments != null) {
                (itemPostBinding.commentsRecyclerView.adapter as? CommentAdapter)?.updateData(
                    updatedComments
                )
            }
        }


        itemPostBinding.infoIcon.setOnClickListener {
            val intent = Intent(context, DetailPostActivity::class.java)
            intent.putExtra(
                "POST_ID",
                post.idPost
            )
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




