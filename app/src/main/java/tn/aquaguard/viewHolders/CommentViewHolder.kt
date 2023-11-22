package tn.aquaguard.viewHolders

import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import tn.aquaguard.R
import tn.aquaguard.databinding.ItemCommentBinding
import tn.aquaguard.models.Comment
import tn.aquaguard.viewmodel.PostViewModel


class CommentViewHolder(private val context: Context, val itemCommentBinding: ItemCommentBinding,private val viewModel: PostViewModel,private val lifecycleOwner: LifecycleOwner) : RecyclerView.ViewHolder(itemCommentBinding.root) {
    fun setData(comment : Comment,isMyPostFragment: Boolean){
        // Edit Comment icon display gone when isMyPostFragment is true



        val current_user_id = "65572f11bcaa0c0abb35f25d" // This should be fetched from a session or auth manager

        // Set visibility based on whether the current user posted the comment
        itemCommentBinding.commentdelete.visibility = if (current_user_id == comment.idUser) View.VISIBLE else View.GONE





        itemCommentBinding.commentdelete.setOnClickListener {
            viewModel.deleteComment(comment.idComment, comment.idPost)
            Snackbar.make(itemCommentBinding.root, "Comment deleted successfully", Snackbar.LENGTH_SHORT).show()
        }
        Picasso.with(context).load("http://10.0.2.2:9090/images/user/"+comment.commentAvatar).fit().centerInside().into(itemCommentBinding.commentAvatar)


        itemCommentBinding.commentUsername.text = comment.commentUsername
        itemCommentBinding.commentText.text = comment.comment


        if (isMyPostFragment){
            itemCommentBinding.commentEdit.visibility = View.GONE
        }else{
            itemCommentBinding.commentEdit.visibility = if (current_user_id == comment.idUser) View.VISIBLE else View.GONE
        }


        itemCommentBinding.commentEdit.setOnClickListener {
            showEditCommentDialog(context,comment.idComment,comment.comment)
        }

    }

    private fun showEditCommentDialog(context: Context,idComment : String,comment: String) {

        val inflater = LayoutInflater.from(itemView.context)
        val customView = inflater.inflate(R.layout.custom_edit_post_dialog, null)
        val textInputEditText = customView.findViewById<TextInputEditText>(R.id.comment)
        val okButton = customView.findViewById<Button>(R.id.okButton)
        val cancelBinding = customView.findViewById<Button>(R.id.cancelButton)

        textInputEditText.text = Editable.Factory.getInstance().newEditable(comment)

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(customView)
            .setCancelable(false)

        val dialog = dialogBuilder.create()



        okButton.setOnClickListener {
            viewModel.updateComment(idComment,textInputEditText.text.toString())
            dialog.dismiss()
        }

        cancelBinding.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()



    }
}