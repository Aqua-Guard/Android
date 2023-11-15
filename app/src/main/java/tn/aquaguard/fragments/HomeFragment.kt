package tn.aquaguard.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import tn.aquaguard.R
import tn.aquaguard.adapters.ActualitesAdapter
import tn.aquaguard.databinding.FragmentHomeBinding
import tn.aquaguard.models.Actualites

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.rvactualite.adapter= ActualitesAdapter(getListNews(requireContext()))

        binding.rvactualite.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return binding.root
    }
    private fun getListNews(context: Context) : MutableList<Actualites> {
        return mutableListOf(
            Actualites("bilel01", "The season is coming...","The best time ever for hunting Mannani fish", R.drawable.act, "helo helo",12),
            Actualites("jalel02", "The season is coming...","The best time ever for hunting Mannani fish", R.drawable.act, "helo helo",12),
            Actualites("thebestuser", "The season is coming...","The best time ever for hunting Mannani fish", R.drawable.act, "helo helo",12),
        )
    }

}