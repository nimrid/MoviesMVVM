package ng.com.gocheck.moviesmvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ng.com.gocheck.moviesmvvm.model.popularMovies.Result
import ng.com.gocheck.moviesmvvm.model.network.MOVIE_PER_PAGE
import ng.com.gocheck.moviesmvvm.model.network.NetworkState
import ng.com.gocheck.moviesmvvm.model.repository.MovieDataSource
import ng.com.gocheck.moviesmvvm.model.repository.PopMovieDataSourceFactory

class PopularMovieVM : ViewModel() {

    private var popMovieDataSourceFactory = PopMovieDataSourceFactory()
    private var livePagedListBuilder : LiveData<PagedList<Result>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(MOVIE_PER_PAGE)
            .setEnablePlaceholders(false)
            .build()

        livePagedListBuilder = LivePagedListBuilder(popMovieDataSourceFactory, config).build()
    }

    fun getNetworkState() : LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            popMovieDataSourceFactory.mutableDataSource, MovieDataSource::networkState
        )
    }

    fun getMovies() = livePagedListBuilder

    fun isListEmpty() : Boolean {
        return livePagedListBuilder.value?.isEmpty() ?: true
    }

}