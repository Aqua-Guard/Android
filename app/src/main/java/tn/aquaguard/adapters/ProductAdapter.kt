package tn.aquaguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.aquaguard.databinding.ActualiteItemBinding
import tn.aquaguard.databinding.ProductItemBinding
import tn.aquaguard.models.Actualites
import tn.aquaguard.models.Product

class ProductAdapter (val Poductlist: MutableList<Product>) : RecyclerView.Adapter<ProductAdapter.ProductHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): .ProductHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductHolder, position: Int) {
        holder.setData(Productlist[position])
    }

    override fun getItemCount() = Productlist.size
    inner class ActualiteHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(act: Product){
            with(act){
                binding.newsTitle.text = productname
                binding.newsDescription.text = nbPoints
                binding.newsImage.setImageResource(image)

            }
        }
    }
}