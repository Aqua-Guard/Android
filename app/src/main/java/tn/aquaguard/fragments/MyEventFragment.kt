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
import tn.aquaguard.adapters.MyEventAdapter
import tn.aquaguard.databinding.FragmentMyEventsBinding
import tn.aquaguard.viewmodel.EventViewModel


class MyEventFragment : Fragment() {

    private lateinit var binding: FragmentMyEventsBinding
    private val viewModel: EventViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMyEventsBinding.inflate(layoutInflater)


        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.myevents.observe(viewLifecycleOwner) { myevents ->

            println("My Event API :"+myevents.toString())
            binding.rvEvents.adapter = MyEventAdapter(myevents,viewModel,this)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            if (isAdded) { // Check if the fragment is currently added to its activity
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MyEventFragment())
                    .commitAllowingStateLoss()
            }
            binding.swipeRefreshLayout.post { binding.swipeRefreshLayout.isRefreshing = false }
        }


        return binding.root
    }




}