package tn.aquaguard.viewHolders

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import tn.aquaguard.R
import tn.aquaguard.adapters.CommentAdapter
import tn.aquaguard.adapters.LikeAdapter
import tn.aquaguard.databinding.ItemCommentBinding
import tn.aquaguard.databinding.ItemMyPostBinding
import tn.aquaguard.fragments.MyPostFrament
import tn.aquaguard.models.Comment
import tn.aquaguard.models.Like
import tn.aquaguard.models.Post
import tn.aquaguard.viewmodel.PostViewModel

class MyPostViewHolder(
    private val context: Context,
    val itemMyPostBinding: ItemMyPostBinding,
    private val viewModel: PostViewModel,
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.ViewHolder(itemMyPostBinding.root) {

    fun setData(post: Post) {
        Picasso.with(context).load("http://10.0.2.2:9090/images/user/" + post.userImage).fit()
            .centerInside().into(itemMyPostBinding.userimage)
        itemMyPostBinding.username.text = post.userName
        itemMyPostBinding.userRole.text = post.userRole
        itemMyPostBinding.description.text = post.description
        Picasso.with(context).load("http://10.0.2.2:9090/images/post/" + post.postImage).fit()
            .centerInside().into(itemMyPostBinding.postimage)
        itemMyPostBinding.nbcomments.text = post.nbComments.toString()
        itemMyPostBinding.nblikes.text = post.nbLike.toString()

        //---------------On Click Delete Post ------------------------------
        itemMyPostBinding.postDelete.setOnClickListener {
            viewModel.deletePost(post.idPost)
            Snackbar.make(itemMyPostBinding.root, "Post deleted successfully", Snackbar.LENGTH_SHORT).show()
        }
        //----------------------------------------------------------------

        //----------------call function when i get one post --------------
        viewModel.fetchPostById(post.idPost)
        viewModel.singlePost.observe(lifecycleOwner) { mypost ->
            println("--------------------------------"+mypost)
            mypost?.let {
                itemMyPostBinding.likeicon.setOnClickListener {
                    showLikesDialog(mypost.likes)
                }
                itemMyPostBinding.commeticon.setOnClickListener {
                    showCommentsDialog(mypost.comments)
                }
            }
        }
        //------------------------------------------------------------------



    }
    //-----------------Like Dialog ------------------
    private fun showLikesDialog(likesList: List<Like>) {
        val dialog = BottomSheetDialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.likes_post)

        val numberOfLikesTextView = dialog.findViewById<TextView>(R.id.number_of_likes)
        val recyclerView = dialog.findViewById<RecyclerView>(R.id.rv_post_likes)
        val emptyLikesImageView = dialog.findViewById<ImageView>(R.id.emptyLikesImageView)
        val noLiketxt = dialog.findViewById<TextView>(R.id.nolikes)
        val cancelButton = dialog.findViewById<ImageView>(R.id.cancelButton)



        numberOfLikesTextView?.text = likesList.size.toString()

        if (likesList.isEmpty()) {
            emptyLikesImageView?.visibility = View.VISIBLE
            noLiketxt?.visibility = View.VISIBLE
            recyclerView?.visibility = View.GONE
        } else {

            recyclerView?.visibility = View.VISIBLE
            emptyLikesImageView?.visibility = View.GONE
            noLiketxt?.visibility = View.GONE

            // Set up the RecyclerView
            recyclerView?.layoutManager = LinearLayoutManager(context)
            val likeAdapter = LikeAdapter(likesList)
            recyclerView?.adapter = likeAdapter
        }

        cancelButton?.setOnClickListener { dialog.dismiss() }
        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }
    //-----------------------------------------------
    //-----------------Comment Dialog ------------------
    private fun showCommentsDialog(commentList: List<Comment>) {
        val dialog = BottomSheetDialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setContentView(R.layout.comments_post)

        // Initialize views
        val recyclerView = dialog.findViewById<RecyclerView>(R.id.commentsRecyclerView)
        val emptyCommentsImageView = dialog.findViewById<ImageView>(R.id.emptyLikesImageView)
        val noCommentsTextView = dialog.findViewById<TextView>(R.id.nolikes)
        val numberOfCommentsTextView = dialog.findViewById<TextView>(R.id.number_of_likes)
        val cancelButton = dialog.findViewById<ImageView>(R.id.cancelButton)


        numberOfCommentsTextView?.text = commentList.size.toString()

        if (commentList.isEmpty()) {

            emptyCommentsImageView?.visibility = View.VISIBLE
            noCommentsTextView?.visibility = View.VISIBLE
            recyclerView?.visibility = View.GONE
        } else {
            // Show the RecyclerView, hide the placeholder image and text
            recyclerView?.visibility = View.VISIBLE
            emptyCommentsImageView?.visibility = View.GONE
            noCommentsTextView?.visibility = View.GONE

            // Set up the RecyclerView
            recyclerView?.layoutManager = LinearLayoutManager(context)
            val commentAdapter = CommentAdapter(commentList,viewModel,lifecycleOwner,-1,true)
            recyclerView?.adapter = commentAdapter
        }

        cancelButton?.setOnClickListener { dialog.dismiss() }
        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.BOTTOM)
        //---------------------If delete Close dialog-------------------
        viewModel.deleteCommentResult.observe(lifecycleOwner, Observer { result ->
            dialog.dismiss()
            Toast.makeText(context, "Comment Deleted!", Toast.LENGTH_SHORT).show()
        })
        //-------------------------------------------------------------

    }
    //-----------------------------------------------
}