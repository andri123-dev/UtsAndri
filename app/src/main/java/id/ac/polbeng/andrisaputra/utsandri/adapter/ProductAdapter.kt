package id.ac.polbeng.andrisaputra.utsandri.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.polbeng.andrisaputra.utsandri.databinding.ItemProductBinding
import id.ac.polbeng.andrisaputra.utsandri.model.Product

class ProductAdapter(
    private var products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        with(holder.binding) {
            tvTitle.text = product.title
            tvPrice.text = "$${product.price}"
            tvStock.text = "Stock: ${product.stock}"
            tvRating.text = "Rating: ${product.rating}"

            Glide.with(root.context)
                .load(product.thumbnail)
                .into(ivThumbnail)

            root.setOnClickListener { onItemClick(product) }
        }
    }

    override fun getItemCount(): Int = products.size

    fun updateData(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}
