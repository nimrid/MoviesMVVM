package ng.com.gocheck.moviesmvvm.repository

import ng.com.gocheck.moviesmvvm.network.MoviesApi

class MovieRepository(private val movieApi: MoviesApi) : SafeApiCall() {
    
    suspend fun getRecommendedMovies(id : Int) =
        apiResponse { movieApi.recommendMovie(id) }

    suspend fun getMovieDetails(movieId : Int) = apiResponse { movieApi.movieDetails(movieId)}

    suspend fun getLatestMovie() = apiResponse { movieApi.latestMovie() }

    suspend fun getTopRatedMovie() = apiResponse { movieApi.topRatedMovie() }

    suspend fun getUpComingMovie() = apiResponse { movieApi.upComingMovie() }

    suspend fun getTrailer(id: Int) = apiResponse { movieApi.movieTrailer(id) }

}