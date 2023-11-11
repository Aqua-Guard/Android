package tn.aquaguard.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
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

class DetailPostActivity : AppCompatActivity() {

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
        likeicons.setOnClickListener {
            showLikesDialog()
        }
        commenticon.setOnClickListener {
            showCommentsDialog()
        }

    }
    private fun showCommentsDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setContentView(R.layout.comments_post)


        val recyclerView = dialog.findViewById<RecyclerView>(R.id.commentsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val commentList = getCommentsList() // Ensure this method is accessible here
        val commentAdapter = CommentAdapter(commentList)
        recyclerView.adapter = commentAdapter

        val cancelButton = dialog.findViewById<ImageView>(R.id.cancelButton)
        cancelButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }

    private fun showLikesDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setContentView(R.layout.likes_post)

        // Initialize RecyclerView inside the dialog
        val recyclerView = dialog.findViewById<RecyclerView>(R.id.rv_post_likes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val likesList = getLikesList() // Ensure this method is accessible here
        val likeAdapter = LikeAdapter(likesList)
        recyclerView.adapter = likeAdapter

        val cancelButton = dialog.findViewById<ImageView>(R.id.cancelButton)
        cancelButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }

    private fun getLikesList(): List<Like> {
        // Sample list of Like objects
        return listOf(
            Like("Malek Labidi", "Partner", R.drawable.user),
            Like("Youssef Farhat", "Member", R.drawable.yousseff),
            Like("youyou", "Admin", R.drawable.youssef)
            // Add more Like objects as needed
        )
    }
    private fun getCommentsList(): List<Comment> {
        // Sample list of Like objects
        return listOf(
            Comment(
                commentAvatar = R.drawable.youssef, // Replace with actual drawable resource ID
                commentUsername = "Youssef Farhat",
                comment = "Really insightful post. Thanks for sharing!"),
            Comment(
                commentAvatar = R.drawable.user, // Replace with actual drawable resource ID
                commentUsername = "Malek Labidi",
                comment = "Really insightful post. Thanks for sharing!"),
            Comment(
                commentAvatar = R.drawable.yousseff, // Replace with actual drawable resource ID
                commentUsername = "YouYou",
                comment = "Really insightful post. Thanks for sharing!")

        )
    }

}