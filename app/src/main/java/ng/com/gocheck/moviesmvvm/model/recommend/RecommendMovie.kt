package ng.com.gocheck.moviesmvvm.model.recommend

data class RecommendMovie(
    val page: Int = 0,
    val totalPages: Int = 0,
    val results: List<ResultsItem>?,
    val totalResults: Int = 0
)