package tn.aquaguard.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import tn.aquaguard.R
import tn.aquaguard.adapters.ActualitesAdapter
import tn.aquaguard.adapters.PostAdapter
import tn.aquaguard.databinding.FragmentHomeBinding
import tn.aquaguard.models.Actualites
import tn.aquaguard.viewmodel.ActualiteViewModel
import tn.aquaguard.viewmodel.PostViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: ActualiteViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        // Set up RecyclerView with an empty adapter initially
        binding.rvactualite.layoutManager = LinearLayoutManager(requireContext())
        binding.rvactualite.adapter= ActualitesAdapter(emptyList())

        // Observe the LiveData from ViewModel
        viewModel.events.observe(viewLifecycleOwner) {ActualiteList ->
            println("Post API :"+ActualiteList.toString())
            binding.rvactualite.adapter = ActualitesAdapter(ActualiteList)

        }

        return binding.root
    }


}