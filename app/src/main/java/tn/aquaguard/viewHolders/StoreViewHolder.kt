package tn.aquaguard.viewHolders

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tn.aquaguard.databinding.ProductitemBinding
import tn.aquaguard.models.Product
import tn.aquaguard.ui.DetailActualite
import tn.aquaguard.ui.DetailProductActivite
import tn.aquaguard.viewmodel.StoreViewModel


class StoreViewHolder (private val context: Context, val ProductItem : ProductitemBinding, private val viewModel: StoreViewModel): RecyclerView.ViewHolder(ProductItem.root) {
    fun setData(products: Product) {
        Picasso.with(context).load( "http://10.0.2.2:9090/images/produit/"+products.image ).fit().centerInside().into(ProductItem.productImage)
      ProductItem.productname.text=products.name
        ProductItem.price.text=products.price.toString()+"PT"
        ProductItem.content.setOnClickListener {


                val intent = Intent(context, DetailProductActivite::class.java)
                intent.putExtra("PRODUCTNAME",products.name)
                intent.putExtra("PRODUCTIMAGE", products.image)
                intent.putExtra("PRODUCTPRICE", products.price.toString())
                intent.putExtra("PRODUCTCATEGORY", products.category)
                intent.putExtra("PRODUCTDESCRIPTION", products.description)
                intent.putExtra("PRODUCTQUANTITY", products.quantity.toString())
                context.startActivity(intent)


        }

    }
}