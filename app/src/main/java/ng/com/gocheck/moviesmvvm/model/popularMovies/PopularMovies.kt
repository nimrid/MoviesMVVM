package ng.com.gocheck.moviesmvvm.model.popularMovies


import com.google.gson.annotations.SerializedName

data class PopularMovies(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)