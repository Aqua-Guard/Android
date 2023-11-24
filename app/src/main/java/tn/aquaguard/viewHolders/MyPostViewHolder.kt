package tn.aquaguard.viewHolders

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tn.aquaguard.R
import tn.aquaguard.adapters.CommentAdapter
import tn.aquaguard.adapters.LikeAdapter
import tn.aquaguard.databinding.ItemCommentBinding
import tn.aquaguard.databinding.ItemMyPostBinding
import tn.aquaguard.fragments.MyPostFrament
import tn.aquaguard.models.Comment
import tn.aquaguard.models.Like
import tn.aquaguard.models.Post
import tn.aquaguard.ui.MainActivity
import tn.aquaguard.viewmodel.PostViewModel
import java.io.File
import java.io.FileOutputStream

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
            Snackbar.make(
                itemMyPostBinding.root,
                "Post deleted successfully",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        //----------------------------------------------------------------
        //---------------On Click Edit Post ------------------------------
        itemMyPostBinding.postEdit.setOnClickListener {
            val inflater = LayoutInflater.from(context)
            val dialogView = inflater.inflate(R.layout.custom_edit_post_dialog, null)
            val closeButton = dialogView.findViewById<ImageView>(R.id.closeButton)


            val descriptionEditText = dialogView.findViewById<TextInputEditText>(R.id.description)
            descriptionEditText.text = Editable.Factory.getInstance().newEditable(post.description)

            val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
            val addEventImage = dialogView.findViewById<Button>(R.id.addEventImage)
            val okButton = dialogView.findViewById<Button>(R.id.submit)

            val dialogBuilder = AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)

            val dialog = dialogBuilder.create()



            if (closeButton != null) {
                closeButton.setOnClickListener {
                    dialog.dismiss()
                }
            }
            if (cancelButton != null) {
                cancelButton.setOnClickListener {
                    descriptionEditText.text = Editable.Factory.getInstance().newEditable("")
                }
            }

            if (okButton != null) {
                okButton.setOnClickListener {
                    println("Button clicked")
                    val descriptionText = descriptionEditText.text.toString()
                    val description = RequestBody.create(MediaType.parse("text/plain"), descriptionText)
                    viewModel.updatePost(post.idPost,description)
                    viewModel.updatePostStatus.observe( lifecycleOwner , { resource ->
                        if (resource.isSuccessful) {
                            Snackbar.make(
                                it,
                                "Post Updated successfully.",
                                Snackbar.LENGTH_LONG
                            ).setBackgroundTint(Color.parseColor("#90EE90")).show()
                            descriptionEditText.text = Editable.Factory.getInstance().newEditable("")
                        }
                        else {
                            Snackbar.make(
                                it,
                                "The description contains inappropriate language..",
                                Snackbar.LENGTH_LONG
                            ).setBackgroundTint(Color.parseColor("#FF0000")).show()
                        }

                    })

                }
            }

            dialog.show()
        }
        //----------------------------------------------------------------
        //----------------call function when i get one post --------------
        viewModel.fetchPostById(post.idPost)
        viewModel.singlePost.observe(lifecycleOwner) { mypost ->
            println("--------------------------------" + mypost)
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
            val commentAdapter = CommentAdapter(commentList, viewModel, lifecycleOwner, -1, true)
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