package tn.aquaguard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import tn.aquaguard.R

class DetailProductActivite : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product_activite)
        //declaring the containers in the ui
        var image : ImageView = findViewById(R.id.ProductDetailimage)
        var title : TextView = findViewById(R.id.productDetailname)
        var description : TextView = findViewById(R.id.ProductDetailDescription)
        var price : TextView = findViewById(R.id.price)
        var category : TextView = findViewById(R.id.category)
        var quantity : TextView = findViewById(R.id.qantite)


        // intent get extra
        var productImage = intent.getStringExtra("PRODUCTIMAGE")
        title.text=intent.getStringExtra("PRODUCTNAME")
        description.text="Description :"+intent.getStringExtra("PRODUCTDESCRIPTION")
        price.text="Price :"+intent.getStringExtra("PRODUCTPRICE")+"PT"
        category.text=intent.getStringExtra("PRODUCTCATEGORY")
        quantity.text=intent.getStringExtra("PRODUCTQUANTITY")+" Available Articles"
        Picasso.with(this).load("http://10.0.2.2:9090/images/produit/"+productImage).fit().centerInside().into(image)

    }
}