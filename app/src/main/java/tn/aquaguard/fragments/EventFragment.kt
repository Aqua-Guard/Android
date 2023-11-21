package tn.aquaguard.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import tn.aquaguard.R
import tn.aquaguard.adapters.EventAdapter
import tn.aquaguard.databinding.FragmentEventsBinding
import tn.aquaguard.viewmodel.EventViewModel


class EventFragment : Fragment() {

    private lateinit var binding: FragmentEventsBinding
    private val viewModel: EventViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEventsBinding.inflate(layoutInflater)


        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.events.observe(viewLifecycleOwner) { events ->

            println("Event API :"+events.toString())
            binding.rvEvents.adapter = EventAdapter(events,viewModel,this)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            if (isAdded) { // Check if the fragment is currently added to its activity
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, EventFragment())
                    .commitAllowingStateLoss()
            }
            binding.swipeRefreshLayout.post { binding.swipeRefreshLayout.isRefreshing = false }
        }


        return binding.root
    }




}