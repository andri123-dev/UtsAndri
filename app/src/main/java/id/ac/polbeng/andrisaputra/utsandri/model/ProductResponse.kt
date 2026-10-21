package id.ac.polbeng.andrisaputra.utsandri.model

data class ProductResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val stock: Int,
    val rating: Double,
    val tags: List<String>,
    val dimensions: Dimensions,
    val reviews: List<Review>,
    val availabilityStatus: String,
    val images: List<String>,
    val thumbnail: String
)

data class Dimensions(
    val width: Double,
    val height: Double,
    val depth: Double
)

data class Review(
    val rating: Int,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String
)
