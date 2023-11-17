package tn.aquaguard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import tn.aquaguard.databinding.FragmentStoreBinding
import tn.aquaguard.adapters.EventAdapter

class StoreFragment : Fragment() {
    private lateinit var binding: FragmentStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoreBinding.inflate(inflater, container, false)

        binding.rvproduct.adapter = EventAdapter(emptyList())
        binding.rvproduct.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.products.observe(viewLifecycleOwner) { products ->
            println("Products API: " + products.toString())
            (binding.rvproduct.adapter as? EventAdapter)?.updateProducts(products)
        }

        return binding.root
    }
}

}
}
