package tn.aquaguard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import tn.aquaguard.adapters.ActualitesAdapter
import tn.aquaguard.databinding.FragmentHomeBinding
import tn.aquaguard.models.Actualites
import tn.aquaguard.models.SearchRequest
import tn.aquaguard.viewmodel.ActualiteViewModel

class HomeFragment : Fragment() {
    lateinit var actualitesearche : List<Actualites>
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: ActualiteViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        var test = binding.editTextUsername.text.toString()
        // Set up RecyclerView with an empty adapter initially
        binding.rvactualite.layoutManager = LinearLayoutManager(requireContext())
        binding.rvactualite.adapter= ActualitesAdapter(emptyList())


        ////////////////////////////////////////
binding.searchButton.setOnClickListener {
    val about= SearchRequest(binding.editTextUsername.text.toString())
    viewModel.searchActualites(about)
}

        viewModel.actualitesearched.observe(viewLifecycleOwner) { searchedActualites ->
            println("Searched Actualites: $searchedActualites")
            binding.rvactualite.adapter = ActualitesAdapter(searchedActualites)
        }

        // Observe the LiveData from ViewModel
        viewModel.actualites.observe(viewLifecycleOwner) {ActualiteList ->
            println("actualite API :"+ActualiteList.toString())
            binding.rvactualite.adapter = ActualitesAdapter(ActualiteList)

        }
        return binding.root
    }



}