package ng.com.gocheck.moviesmvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ng.com.gocheck.moviesmvvm.model.network.MoviesApi
import ng.com.gocheck.moviesmvvm.model.network.NetworkConnectionInterceptor

//@Suppress("UNCHECKED_CAST")
//class ViewModelFactory(
//    private val moviesApi: MoviesApi
//) : ViewModelProvider.NewInstanceFactory() {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return PopularMovieVM(moviesApi) as T
//    }
//}