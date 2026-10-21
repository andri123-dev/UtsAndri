package id.ac.polbeng.andrisaputra.utsandri.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.polbeng.andrisaputra.utsandri.R
import id.ac.polbeng.andrisaputra.utsandri.api.ApiClient
import id.ac.polbeng.andrisaputra.utsandri.adapter.ProductAdapter
import id.ac.polbeng.andrisaputra.utsandri.databinding.FragmentProductListBinding
import id.ac.polbeng.andrisaputra.utsandri.model.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        fetchProducts()
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(emptyList()) { product ->
            val bundle = Bundle().apply {
                putInt("productId", product.id)
            }
            findNavController().navigate(R.id.action_navigation_product_to_productDetailFragment, bundle)
        }
        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }
    }

    private fun fetchProducts() {
        binding.progressBar.visibility = View.VISIBLE
        ApiClient.instance.getProducts(25).enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    response.body()?.let {
                        productAdapter.updateData(it.products)
                    }
                } else {
                    Toast.makeText(context, "Failed to load products", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
