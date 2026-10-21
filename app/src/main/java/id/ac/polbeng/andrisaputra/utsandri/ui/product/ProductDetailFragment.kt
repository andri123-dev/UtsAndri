package id.ac.polbeng.andrisaputra.utsandri.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import androidx.appcompat.app.AlertDialog
import id.ac.polbeng.andrisaputra.utsandri.api.ApiClient
import id.ac.polbeng.andrisaputra.utsandri.databinding.FragmentProductDetailBinding
import id.ac.polbeng.andrisaputra.utsandri.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private var productId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = it.getInt("productId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (productId != -1) {
            fetchProductDetail()
        }
    }

    private fun fetchProductDetail() {
        ApiClient.instance.getProductDetail(productId).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    response.body()?.let { displayProduct(it) }
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayProduct(product: Product) {
        with(binding) {
            tvDetailTitle.text = product.title
            tvDetailPrice.text = "$${product.price}"
            tvDetailDescription.text = product.description
            tvDetailCategory.text = product.category
            tvDetailStatus.text = product.availabilityStatus
            tvDetailDimensions.text = "Dimensions: ${product.dimensions.width} x ${product.dimensions.height} x ${product.dimensions.depth}"

            if (product.images.isNotEmpty()) {
                Glide.with(requireContext())
                    .load(product.images[0])
                    .into(ivProductImage)
            }

            if (product.reviews.isNotEmpty()) {
                val topReview = product.reviews[0]
                tvReviewerName.text = topReview.reviewerName
                tvReviewRating.text = "Rating: ${topReview.rating}/5"
                tvReviewComment.text = topReview.comment
            } else {
                cvTopReview.visibility = View.GONE
                tvReviewHeader.text = "No reviews available"
                btnViewAllReviews.visibility = View.GONE
            }
            
            btnViewAllReviews.setOnClickListener {
                showAllReviewsDialog(product)
            }
        }
    }

    private fun showAllReviewsDialog(product: Product) {
        val reviewsStr = product.reviews.joinToString("\n\n") { 
            "From: ${it.reviewerName}\nRating: ${it.rating}/5\nComment: ${it.comment}" 
        }
        
        AlertDialog.Builder(requireContext())
            .setTitle("All Reviews for ${product.title}")
            .setMessage(reviewsStr)
            .setPositiveButton("Close", null)
            .show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
