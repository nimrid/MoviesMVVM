package ng.com.gocheck.moviesmvvm.model.recommend

data class ResultsItem(
    val overview: String = "",
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val video: Boolean = false,
    val title: String = "",
    val genreIds: List<Integer>?,
    val posterPath: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val popularity: Double = 0.0,
    val id: Int = 0,
    val adult: Boolean = false,
)