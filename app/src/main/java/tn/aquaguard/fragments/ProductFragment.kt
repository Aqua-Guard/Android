package tn.aquaguard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import tn.aquaguard.R
import tn.aquaguard.adapters.StoreAdapter
import tn.aquaguard.databinding.FragmentProductBinding
import tn.aquaguard.viewmodel.StoreViewModel


class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private val viewModel: StoreViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val spanCount = 2
        binding = FragmentProductBinding.inflate(layoutInflater)
        val layoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.rvproduct.layoutManager = layoutManager


        viewModel.products.observe(viewLifecycleOwner) {ActualiteList ->
            binding.rvproduct.adapter = StoreAdapter(ActualiteList,viewModel)
        }
        return binding.root
    }


    }
