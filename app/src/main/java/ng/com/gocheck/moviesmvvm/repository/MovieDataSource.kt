package ng.com.gocheck.moviesmvvm.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ng.com.gocheck.moviesmvvm.model.movie.Result
import ng.com.gocheck.moviesmvvm.network.FIRST_PAGE
import ng.com.gocheck.moviesmvvm.network.MoviesApi
import ng.com.gocheck.moviesmvvm.network.NetworkState

class MovieDataSource(
    private val moviesApi: MoviesApi
) : PageKeyedDataSource<Int, Result>() {

    private val completeJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completeJob)

    private var page = FIRST_PAGE

    val networkState : MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {

        coroutineScope.launch(Dispatchers.IO) {
            networkState.postValue(NetworkState.LOADING)
            val response = moviesApi.getMovies(page)
            if (response.isSuccessful){
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(response.body()!!.results, null, page+1)
            }else
                networkState.postValue(NetworkState.ERROR)

        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {

        coroutineScope.launch(Dispatchers.IO) {
            networkState.postValue(NetworkState.LOADING)
            val response = moviesApi.getMovies(params.key)
            if ( response.isSuccessful && response.body()!!.totalPages >= params.key){
                callback.onResult(response.body()!!.results, params.key.inc())
                networkState.postValue(NetworkState.LOADED)
            }else
                networkState.postValue(NetworkState.ERROR)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun clearCoroutineJobs(){
        completeJob.cancel()
    }

}