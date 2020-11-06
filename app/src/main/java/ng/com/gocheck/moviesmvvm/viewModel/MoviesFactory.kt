package ng.com.gocheck.moviesmvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ng.com.gocheck.moviesmvvm.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class MoviesFactory(private val repository: MovieRepository) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MoviesViewModel(repository) as T
}