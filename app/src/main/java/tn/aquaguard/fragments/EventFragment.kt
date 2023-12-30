package tn.aquaguard.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import tn.aquaguard.R
import tn.aquaguard.adapters.EventAdapter
import tn.aquaguard.adapters.MyPostAdapter
import tn.aquaguard.databinding.FragmentEventsBinding
import tn.aquaguard.viewmodel.EventViewModel


class EventFragment : Fragment() {

    private lateinit var binding: FragmentEventsBinding
    private val viewModel: EventViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEventsBinding.inflate(layoutInflater)


        binding.rvEvents.adapter = EventAdapter(emptyList(),viewModel, this)
        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.events.observe(viewLifecycleOwner) { events ->

            println("Event API :"+events.toString())
            if (events.isEmpty()) {
                // Show image and text for empty events list
                binding.emptyEventsImageView.visibility = View.VISIBLE
                binding.noevents.visibility = View.VISIBLE
                binding.rvEvents.visibility = View.GONE
            } else {
                // Hide image and text if events list is not empty
                binding.emptyEventsImageView.visibility = View.GONE
                binding.noevents.visibility = View.GONE
                binding.rvEvents.visibility = View.VISIBLE

                // Set up the RecyclerView adapter with non-empty events list
                binding.rvEvents.adapter = EventAdapter(events, viewModel, this)
            }
        }



        viewModel.addEventResult.observe(viewLifecycleOwner, Observer { result ->
            if (result.isSuccessful) { // Check if the fragment is currently added to its activity
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, EventFragment())
                    .commitAllowingStateLoss()
            }
        })

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