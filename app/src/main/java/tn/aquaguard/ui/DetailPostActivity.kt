package tn.aquaguard.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.R
import tn.aquaguard.adapters.CommentAdapter
import tn.aquaguard.adapters.LikeAdapter
import tn.aquaguard.models.Comment
import tn.aquaguard.models.Like

import tn.aquaguard.viewmodel.PostViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import tn.aquaguard.models.Post


class DetailPostActivity : AppCompatActivity() {
    private lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_post)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        var namefragment : TextView = findViewById(R.id.nameofcurentFragment)
        namefragment.text = "Detail Post"

        var likeicons: ImageView = findViewById(R.id.likeicon)
        var commenticon: ImageView = findViewById(R.id.commeticon)

        // Enable the back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val whiteArrow = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
        supportActionBar?.setHomeAsUpIndicator(whiteArrow)


        // Set the back arrow click listener
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        viewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        val postId = intent.getStringExtra("POST_ID")
        if (postId != null) {
            viewModel.fetchPostById(postId)
        }

        viewModel.singlePost.observe(this) { post ->
            post?.let {
                updateUI(it)
                likeicons.setOnClickListener {
                    showLikesDialog(post.likes)
                }
                commenticon.setOnClickListener {
                    showCommentsDialog(post.comments)
                }
            }
        }



    }
    private fun updateUI(post: Post) {
        Picasso.with(this)
            .load("http://10.0.2.2:9090/images/user/" + post.userImage)
            .fit()
            .centerInside()
            .into(findViewById<ImageView>(R.id.userimage))

        findViewById<TextView>(R.id.username).text = post.userName
        findViewById<TextView>(R.id.user_role).text = post.userRole

        // Update post details
        findViewById<TextView>(R.id.description).text = post.description

        Picasso.with(this)
            .load("http://10.0.2.2:9090/images/post/" + post.postImage)
            .fit()
            .centerInside()
            .into(findViewById<ImageView>(R.id.postimage))


        findViewById<TextView>(R.id.nblikes).text = post.nbLike.toString()
        findViewById<TextView>(R.id.nbcomments).text = post.nbComments.toString()
        findViewById<TextView>(R.id.nbshare).text = post.nbShare.toString()
    }
    private fun showCommentsDialog(commentList: List<Comment>) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.comments_post)

        // Initialize views
        val recyclerView = dialog.findViewById<RecyclerView>(R.id.commentsRecyclerView)
        val emptyCommentsImageView = dialog.findViewById<ImageView>(R.id.emptyLikesImageView)
        val noCommentsTextView = dialog.findViewById<TextView>(R.id.nolikes)
        val numberOfCommentsTextView = dialog.findViewById<TextView>(R.id.number_of_likes)
        val cancelButton = dialog.findViewById<ImageView>(R.id.cancelButton)


        numberOfCommentsTextView.text = commentList.size.toString()

        if (commentList.isEmpty()) {

            emptyCommentsImageView.visibility = View.VISIBLE
            noCommentsTextView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            // Show the RecyclerView, hide the placeholder image and text
            recyclerView.visibility = View.VISIBLE
            emptyCommentsImageView.visibility = View.GONE
            noCommentsTextView.visibility = View.GONE

            // Set up the RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(this)
            val commentAdapter = CommentAdapter(commentList,viewModel)
            recyclerView.adapter = commentAdapter
        }

        cancelButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }


    private fun showLikesDialog(likesList: List<Like>) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.likes_post)

        val numberOfLikesTextView = dialog.findViewById<TextView>(R.id.number_of_likes)
        val recyclerView = dialog.findViewById<RecyclerView>(R.id.rv_post_likes)
        val emptyLikesImageView = dialog.findViewById<ImageView>(R.id.emptyLikesImageView)
        val noLiketxt = dialog.findViewById<TextView>(R.id.nolikes)
        val cancelButton = dialog.findViewById<ImageView>(R.id.cancelButton)



        numberOfLikesTextView.text = likesList.size.toString()

        if (likesList.isEmpty()) {
            emptyLikesImageView.visibility = View.VISIBLE
            noLiketxt.visibility = View.VISIBLE
            recyclerView?.visibility = View.GONE
        } else {

            recyclerView?.visibility = View.VISIBLE
            emptyLikesImageView.visibility = View.GONE
            noLiketxt.visibility = View.GONE

            // Set up the RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(this)
            val likeAdapter = LikeAdapter(likesList)
            recyclerView.adapter = likeAdapter
        }

        cancelButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }



}