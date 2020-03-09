package ng.com.gocheck.moviesmvvm.model.repository

import ng.com.gocheck.moviesmvvm.model.network.MoviesApi

class MovieRepository(private val movieApi: MoviesApi) : SafeApiCall() {

    suspend fun getPopularMovies(page : Int) = apiResponse { movieApi.getMovies(page) }

    suspend fun getMovieDetails(movieId : Int) = apiResponse { movieApi.movieDetails(movieId) }

}