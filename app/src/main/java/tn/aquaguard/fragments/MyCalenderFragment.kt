package tn.aquaguard.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import tn.aquaguard.R
import tn.aquaguard.databinding.FragmentMyCalenderBinding
import tn.aquaguard.models.Participation
import tn.aquaguard.viewmodel.ParticipationViewModel
import java.text.SimpleDateFormat
import java.util.*

class MyCalenderFragment : Fragment() {
    private  lateinit var binding: FragmentMyCalenderBinding
    private val participationViewModel: ParticipationViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCalenderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarView: CalendarView = view.findViewById(R.id.calendarView)

        // Observe changes in the participations LiveData
        participationViewModel.participations.observe(viewLifecycleOwner) { participations ->
            val events = mutableListOf<EventDay>()

            participations.forEach { participation ->
                // Extract the date from your participation object (replace with your actual date extraction logic)
                val calendar = Calendar.getInstance()
                calendar.time = participation.DateEvent

                // Add the EventDay object to the list
                events.add(EventDay(calendar, R.drawable.baseline_circle_24))
            }

            // Set the events on the calendar
            calendarView.setEvents(events)
        }

        // Set OnClickListener for calendarView
        calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                // Extract the date from the clicked day
                val clickedDate = eventDay.calendar.time

                // Find all participations for the clicked date
                val clickedParticipations = participationViewModel.participations.value
                    ?.filter { it.DateEvent == clickedDate }

                if (clickedParticipations.isNullOrEmpty()) {
                    // No participations for the clicked date, do nothing or show a message
                    return
                }
                // Show the dialog with all events for the clicked date
                clickedParticipations?.let {
                    showEventDialog(it)
                }
            }
        })
    }


    private fun showEventDialog(participations: List<Participation>) {
        val customView = layoutInflater.inflate(R.layout.custom_event_dialog, null)
        val iconImageView = customView.findViewById<ImageView>(R.id.iconImageView)
        val titleTextView = customView.findViewById<TextView>(R.id.titleTextView)
        val messageTextView = customView.findViewById<TextView>(R.id.messageTextView)
        val okButton = customView.findViewById<Button>(R.id.okButton)

        // Use a StringBuilder to concatenate event details
        val stringBuilder = StringBuilder()

        participations.forEachIndexed { index, participation ->
            val eventName = participation.Eventname
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormat.format(participation.DateEvent)

            stringBuilder.append("Event ${index + 1}:\n")
            stringBuilder.append("Name: $eventName\n")
            stringBuilder.append("Date: $formattedDate\n\n")
        }

        iconImageView.setImageResource(R.drawable.baseline_event_24)
        titleTextView.text = "Event Details"
        titleTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.darkBlue))
        messageTextView.text = stringBuilder.toString()

        // Debugging logs
        print( "Participations: $participations")
        print("StringBuilder: $stringBuilder")

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(customView)
            .setCancelable(false)

        val dialog = dialogBuilder.create()

        okButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }



}