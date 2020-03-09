package ng.com.gocheck.moviesmvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ng.com.gocheck.movies.model.db.movieDetails.Movies
import ng.com.gocheck.moviesmvvm.model.repository.MovieRepository

class MovieDetailVM(private val repository: MovieRepository) : ViewModel() {

    private val completeJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completeJob)

    private val _movieDetail = MutableLiveData<Movies>()
    val movieDetail : LiveData<Movies>
        get() = _movieDetail

    fun getMovieDetail(movieId : Int) {
        coroutineScope.launch(Dispatchers.IO) {
            val movieDet = repository.getMovieDetails(movieId)
            _movieDetail.postValue(movieDet)
        }
    }

    fun clearCoroutineJobs(){
        completeJob.cancel()
    }

}