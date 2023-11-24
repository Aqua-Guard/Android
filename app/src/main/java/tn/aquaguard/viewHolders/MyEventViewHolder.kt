package tn.aquaguard.viewHolders

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import tn.aquaguard.R
import tn.aquaguard.databinding.SingleItemEventsBinding
import tn.aquaguard.models.Event
import tn.aquaguard.ui.DetailEventActivity
import tn.aquaguard.ui.UpdateMyEventActivity
import tn.aquaguard.viewmodel.EventViewModel

import java.text.SimpleDateFormat
import java.util.Locale

class MyEventViewHolder (private val context: Context, val itemEventBinding: SingleItemEventsBinding, private val viewModel: EventViewModel, private val lifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(itemEventBinding.root) {
    fun setData( event : Event){

        Picasso.with(context).load("http://10.0.2.2:9090/images/event/"+event.eventImage).fit().centerInside().into(itemEventBinding.eventImage)
        itemEventBinding.eventTitle.text = event.eventName
        itemEventBinding.eventDescription.text = event.description
        Log.d("khkhk: ",event.description)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateDebutFormatted = dateFormat.format(event.DateDebut)
        val dateFinFormatted = dateFormat.format(event.DateFin)

        itemEventBinding.dateEvent.text = "$dateDebutFormatted to $dateFinFormatted"
        itemEventBinding.eventlocation.text = event.lieu
        itemEventBinding.infobtn.visibility = View.GONE


        itemEventBinding.infobtnmenu.setOnClickListener {
            val popupMenu = PopupMenu(context, it)
            popupMenu.inflate(R.menu.item_menu)

            // Set click listener for menu items
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_item_info -> {
                        val intent = Intent(context, DetailEventActivity::class.java)
                        intent.putExtra("EventName",event.eventName)
                        intent.putExtra("description",event.description)
                        intent.putExtra("DateDebut",dateDebutFormatted)
                        intent.putExtra("DateFin",dateFinFormatted)
                        intent.putExtra("eventImage",event.eventImage)
                        intent.putExtra("lieu",event.lieu)
                        intent.putExtra("eventId",event.idEvent)
                        context.startActivity(intent)
                        true
                    }


                    R.id.menu_item_edit -> {
                        val intent = Intent(context, UpdateMyEventActivity::class.java)
                        intent.putExtra("EventName",event.eventName)
                        intent.putExtra("description",event.description)
                        intent.putExtra("DateDebut",dateDebutFormatted)
                        intent.putExtra("DateFin",dateFinFormatted)
                        intent.putExtra("eventImage",event.eventImage)
                        intent.putExtra("lieu",event.lieu)
                        intent.putExtra("eventId",event.idEvent)
                        context.startActivity(intent)
                        true
                    }
                    R.id.menu_item_delete -> {
                        viewModel.viewModelScope.launch {
                            viewModel.deleteEvent(event.idEvent)
                            Toast.makeText(context, "Event deleted successfully!", Toast.LENGTH_SHORT).show()

                        }
                        true
                    }
                    else -> false
                }
            }

            // Show the popup menu
            popupMenu.show()
        }




    }

}