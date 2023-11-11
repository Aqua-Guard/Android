package tn.aquaguard.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import tn.aquaguard.R
import tn.aquaguard.adapters.EventAdapter
import tn.aquaguard.databinding.FragmentEventsBinding
import tn.aquaguard.models.Event
import java.util.Date

class EventFragment : Fragment() {
    private lateinit var binding: FragmentEventsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEventsBinding.inflate(layoutInflater)

        binding.rvEvents.adapter = EventAdapter(getListEvents(requireContext()))
        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    private fun getListEvents(context: Context) : MutableList<Event> {
        return mutableListOf(
            Event("1","event" , Date(),Date(),"Une initiative communautaire pour nettoyer les plages et protéger l'environnement.","sidi bou said, Tunisia", R.drawable.sidi_bou_said),
            Event("1","event" , Date(),Date(),"Une initiative communautaire pour nettoyer les plages et protéger l'environnement.","sidi bou said, Tunisia", R.drawable.sidi_bou_said),
            Event("1","event" , Date(),Date(),"Une initiative communautaire pour nettoyer les plages et protéger l'environnement.","sidi bou said, Tunisia", R.drawable.sidi_bou_said),
            Event("1","event" , Date(),Date(),"Une initiative communautaire pour nettoyer les plages et protéger l'environnement.","sidi bou said, Tunisia", R.drawable.sidi_bou_said),
            Event("1","event" , Date(),Date(),"Une initiative communautaire pour nettoyer les plages et protéger l'environnement.","sidi bou said, Tunisia", R.drawable.sidi_bou_said),
            Event("1","event" , Date(),Date(),"Une initiative communautaire pour nettoyer les plages et protéger l'environnement.","sidi bou said, Tunisia", R.drawable.sidi_bou_said),
            Event("1","event" , Date(),Date(),"Une initiative communautaire pour nettoyer les plages et protéger l'environnement.","sidi bou said, Tunisia", R.drawable.sidi_bou_said),
            Event("1","event" , Date(),Date(),"Une initiative communautaire pour nettoyer les plages et protéger l'environnement.","sidi bou said, Tunisia", R.drawable.sidi_bou_said),
            Event("1","event" , Date(),Date(),"Une initiative communautaire pour nettoyer les plages et protéger l'environnement.","sidi bou said, Tunisia", R.drawable.sidi_bou_said),
            Event("1","event" , Date(),Date(),"Une initiative communautaire pour nettoyer les plages et protéger l'environnement.","sidi bou said, Tunisia", R.drawable.sidi_bou_said),
            Event("1","event" , Date(),Date(),"Une initiative communautaire pour nettoyer les plages et protéger l'environnement.","sidi bou said, Tunisia", R.drawable.sidi_bou_said),
            Event("1","event" , Date(),Date(),"Une initiative communautaire pour nettoyer les plages et protéger l'environnement.","sidi bou said, Tunisia", R.drawable.sidi_bou_said),
            Event("1","event" , Date(),Date(),"Une initiative communautaire pour nettoyer les plages et protéger l'environnement.","sidi bou said, Tunisia", R.drawable.sidi_bou_said),

        )
    }


}