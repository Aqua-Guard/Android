package tn.aquaguard.viewHolders

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import tn.aquaguard.databinding.ItemCommentBinding
import tn.aquaguard.models.Comment
import tn.aquaguard.viewmodel.PostViewModel


class CommentViewHolder(private val context: Context, val itemCommentBinding: ItemCommentBinding,private val viewModel: PostViewModel,private val lifecycleOwner: LifecycleOwner) : RecyclerView.ViewHolder(itemCommentBinding.root) {
    fun setData(comment : Comment){
        val current_user_id = "65572f11bcaa0c0abb35f25d" // This should be fetched from a session or auth manager

        // Set visibility based on whether the current user posted the comment
        itemCommentBinding.commentdelete.visibility = if (current_user_id == comment.idUser) View.VISIBLE else View.GONE
        itemCommentBinding.commentEdit.visibility = itemCommentBinding.commentdelete.visibility

        viewModel.commentsLiveData.observe(lifecycleOwner, Observer { comments ->
            itemCommentBinding.commentdelete.visibility = if (current_user_id == comment.idUser) View.VISIBLE else View.GONE
            itemCommentBinding.commentEdit.visibility = itemCommentBinding.commentdelete.visibility

        })

        itemCommentBinding.commentEdit.setOnClickListener {
            // Create an AlertDialog Builder
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Edit Comment")

            // Set up the input field
            val input = EditText(context)
            input.inputType = InputType.TYPE_CLASS_TEXT
            input.setText(comment.comment) // Set the current comment content
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("Apply") { dialog, _ ->
                // Get the updated comment text from the input field
                val updatedComment = input.text.toString()

                // Update the comment via your viewModel
              //  viewModel.updateComment(comment.idComment, updatedComment)

                // Inform the user of the update
                Snackbar.make(itemCommentBinding.root, "Comment updated successfully", Snackbar.LENGTH_SHORT).show()

                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

            // Show the dialog
            builder.show()
        }

        itemCommentBinding.commentdelete.setOnClickListener {
            viewModel.deleteComment(comment.idComment, comment.idPost)
            Snackbar.make(itemCommentBinding.root, "Comment deleted successfully", Snackbar.LENGTH_SHORT).show()
        }
        Picasso.with(context).load("http://10.0.2.2:9090/images/user/"+comment.commentAvatar).fit().centerInside().into(itemCommentBinding.commentAvatar)


        itemCommentBinding.commentUsername.text = comment.commentUsername
        itemCommentBinding.commentText.text = comment.comment
    }
}