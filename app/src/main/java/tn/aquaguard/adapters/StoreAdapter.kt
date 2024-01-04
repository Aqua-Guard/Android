package tn.aquaguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.ActualiteItemBinding
import tn.aquaguard.databinding.ProductitemBinding
import tn.aquaguard.models.Actualites
import tn.aquaguard.models.Product
import tn.aquaguard.viewHolders.ActualiteViewHolder
import tn.aquaguard.viewHolders.StoreViewHolder
import tn.aquaguard.viewmodel.ActualiteViewModel
import tn.aquaguard.viewmodel.StoreViewModel

class StoreAdapter(val ProductList: List<Product?>, private val viewModel: StoreViewModel): RecyclerView.Adapter<StoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val binding = ProductitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreViewHolder(parent.context,binding,viewModel)
    }

    override fun getItemCount() = ProductList.size

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        ProductList[position]?.let { holder.setData(it) }
    }
}