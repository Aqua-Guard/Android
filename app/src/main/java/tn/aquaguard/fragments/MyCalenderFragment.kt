package tn.aquaguard.fragments

import android.content.res.Resources
import android.graphics.Color

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import tn.aquaguard.databinding.FragmentMyCalenderBinding

import java.util.Calendar

class MyCalenderFragment : Fragment() {
    private lateinit var binding: FragmentMyCalenderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyCalenderBinding.inflate(inflater, container, false)

        // Add events to specific dates (for example, today and tomorrow)
        val today = Calendar.getInstance()
        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DAY_OF_MONTH, 1)

        // Set the background color of specific dates
        setCustomDateBackgroundColor(today)
        setCustomDateBackgroundColor(tomorrow)

        return binding.root
    }

    private fun setCustomDateBackgroundColor(date: Calendar) {
        val dayOfMonth = date.get(Calendar.DAY_OF_MONTH)
        val month = date.get(Calendar.MONTH)
        val year = date.get(Calendar.YEAR)

        val dateString = "$dayOfMonth/$month/$year"

        try {
            val field = CalendarView::class.java.getDeclaredField("mDateTextSize")
            field.isAccessible = true
            field.set(binding.calendarView, 16f)  // Set the date text size
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/$month/$year"
            if (selectedDate == dateString) {
                val dateView = binding.calendarView.findViewById<View>(Resources.getSystem().getIdentifier("date", "id", "android"))
                dateView.setBackgroundColor(Color.BLUE)  // Set your custom color
            }
        }
    }
}
