package tn.aquaguard.viewHolders

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.launch
import tn.aquaguard.R
import tn.aquaguard.adapters.DiscutionAdapter
import tn.aquaguard.databinding.DiscutionMessageItemBinding
import tn.aquaguard.databinding.ReclamationEnvoyerItemBinding
import tn.aquaguard.models.Discution
import tn.aquaguard.models.Reclamation
import tn.aquaguard.viewmodel.DiscutionViewModel
import tn.aquaguard.viewmodel.EventViewModel

class DiscutionViewHolder (private val context: Context, val messageReclamation : DiscutionMessageItemBinding, private val viewModel: DiscutionViewModel,private val adapter :DiscutionAdapter): RecyclerView.ViewHolder(messageReclamation.root) {
    fun setData(discution: Discution) {

    messageReclamation.id.visibility = View.GONE
    if (discution.userRole.equals("user")) {
        // Set background color to blue for administrator
        messageReclamation.topHalf.setBackgroundResource(R.drawable.graduated_color_2)
    } else {
        // Set background color to gray for other roles
        messageReclamation.topHalf.setBackgroundResource(R.drawable.graduated_color_1)
    }
    messageReclamation.id.text = discution.Id
    messageReclamation.descriptiontext.text = discution.message
    messageReclamation.date.text = discution.createdAt?.toString() ?: "N/A"
    messageReclamation.topHalf.setOnLongClickListener {
        showLongClickDialog(context, messageReclamation.id.text.toString())
        true
    }


    }
    private fun showLongClickDialog(context: Context, idmessage: String) {
        val builder = AlertDialog.Builder(context)

        if (adapter.itemCount < 2) {
            builder.setMessage("Do you want to delete this reclamation?")
        } else {
            builder.setMessage("Do you want to delete this message?")
        }

        // Add a button to close the first dialog
        builder.setNeutralButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        // Add a button to proceed with deletion
        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()

            // Create a new AlertDialog for additional options
            val innerBuilder = AlertDialog.Builder(context)

            // Add a button for "Only for you"
            innerBuilder.setPositiveButton("Only for you") { dialog2, _ ->
                dialog2.dismiss()

                // Create a new AlertDialog for the second step
                val confirmBuilder = AlertDialog.Builder(context)
                confirmBuilder.setMessage("Are you sure you want to delete this message only for you?")

                // Add a button to confirm
                confirmBuilder.setPositiveButton("Confirm") { dialog3, _ ->
                    dialog3.dismiss()
                    viewModel.viewModelScope.launch {
                        viewModel.deleteforall(idmessage)
                        Toast.makeText(
                            context,
                            "message deleted successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                // Add a button to cancel
                confirmBuilder.setNeutralButton("Cancel") { dialog3, _ ->
                    dialog3.dismiss()
                    // Handle the cancellation for "Only for you"
                }

                val confirmDialog = confirmBuilder.create()
                confirmDialog.window?.setBackgroundDrawableResource(R.drawable.costumdialogbackround)
                confirmDialog.show()
            }

            // Add a button for "For all"
            innerBuilder.setNeutralButton("For all") { dialog2, _ ->
                dialog2.dismiss()

                // Create a new AlertDialog for the second step
                val confirmBuilder = AlertDialog.Builder(context)
                confirmBuilder.setMessage("Are you sure you want to delete this message for all?")

                // Add a button to confirm
                confirmBuilder.setPositiveButton("Confirm") { dialog3, _ ->
                    viewModel.viewModelScope.launch {
                        viewModel.deleteonlyforuser(idmessage)
                        Toast.makeText(
                            context,
                            "message deleted successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    dialog3.dismiss()
                    // Handle the confirmation for "For all"
                }

                // Add a button to cancel
                confirmBuilder.setNeutralButton("Cancel") { dialog3, _ ->
                    dialog3.dismiss()
                    // Handle the cancellation for "For all"
                }

                val confirmDialog = confirmBuilder.create()
                confirmDialog.window?.setBackgroundDrawableResource(R.drawable.costumdialogbackround)
                confirmDialog.show()
            }

            val innerDialog = innerBuilder.create()

            // Set rounded corner background for the inner dialog
            innerDialog.window?.setBackgroundDrawableResource(R.drawable.costumdialogbackround)

            innerDialog.show()
        }

        val dialog = builder.create()

        // Set rounded corner background for the main dialog
        dialog.window?.setBackgroundDrawableResource(R.drawable.costumdialogbackround)

        dialog.show()


    }
}