package ng.com.gocheck.moviesmvvm.model.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ng.com.gocheck.moviesmvvm.model.popularMovies.Result
import ng.com.gocheck.moviesmvvm.model.network.MoviesApi

class PopMovieDataSourceFactory(
//    private val moviesApi: MoviesApi
) : DataSource.Factory<Int, Result>() {

    val mutableDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Result> {
        val movieDataSource = MovieDataSource(MoviesApi())
        mutableDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}