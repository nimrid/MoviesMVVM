package ng.com.gocheck.moviesmvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ng.com.gocheck.moviesmvvm.model.latest.LatestMovie
import ng.com.gocheck.moviesmvvm.model.movie.Movie
import ng.com.gocheck.moviesmvvm.model.recommend.RecommendMovie
import ng.com.gocheck.moviesmvvm.model.trailer.MovieTrailer
import ng.com.gocheck.moviesmvvm.repository.MovieRepository

class MoviesViewModel(private val repo: MovieRepository) : ViewModel() {

    private val completeJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completeJob)

    private val _latestMovie = MutableLiveData<LatestMovie>()
    val latestMovie: LiveData<LatestMovie>
        get() = _latestMovie

    private val _upComingMovie = MutableLiveData<Movie>()
    val upComingMovie: LiveData<Movie>
        get() = _upComingMovie

    private val _topRatedMovie = MutableLiveData<Movie>()
    val topRatedMovie: LiveData<Movie>
        get() = _topRatedMovie

    private val _recommendedMovie = MutableLiveData<RecommendMovie>()
    val recommendMovie: LiveData<RecommendMovie>
        get() = _recommendedMovie

    private val _movieTrailer = MutableLiveData<MovieTrailer>()
    val movieTrailer : LiveData<MovieTrailer>
        get() = _movieTrailer

    fun getLatestMovie() {
        coroutineScope.launch {
            _latestMovie.postValue(repo.getLatestMovie())
        }
    }

    fun getUpComing() {
        coroutineScope.launch {
            _upComingMovie.postValue(repo.getUpComingMovie())
        }
    }

    fun topRated() {
        coroutineScope.launch {
            _topRatedMovie.postValue(repo.getTopRatedMovie())
        }
    }

    fun getRecommendedMovie(id : Int) {
        coroutineScope.launch {
            _recommendedMovie.postValue(repo.getRecommendedMovies(id)
            )
        }
    }

    fun getMovieTrailer(id : Int) {
        coroutineScope.launch {
            _movieTrailer.postValue(repo.getTrailer(id)
            )
        }
    }

    fun clearCoroutineJobs() {
        completeJob.cancel()
    }

}