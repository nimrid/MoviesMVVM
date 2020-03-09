package ng.com.gocheck.moviesmvvm.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ng.com.gocheck.moviesmvvm.R
import ng.com.gocheck.moviesmvvm.model.network.NetworkState
import ng.com.gocheck.moviesmvvm.viewModel.PopularMovieVM

class MainActivity : AppCompatActivity() {

//    private val repository = MovieRepository( MoviesApi() )
//    private val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
//    private val moviesApi = MoviesApi(networkConnectionInterceptor)
//    private val viewModelFactory = ViewModelFactory(moviesApi)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(PopularMovieVM::class.java)

        val popMovieAdapter = PageListAdapter(this)

        val gridLayout = GridLayoutManager(this, 2)

        gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType = popMovieAdapter.getItemViewType(position)
                return if (viewType == popMovieAdapter.MOVIE_VIEW_TYPE) 1
                else 2
            }
        }

        movie_recycler_view.layoutManager = gridLayout
        movie_recycler_view.setHasFixedSize(true)
        movie_recycler_view.adapter = popMovieAdapter

        viewModel.getMovies().observe(this, Observer {
            popMovieAdapter.submitList(it)
        })

//        still haven't fixed no internet connection
        viewModel.getNetworkState().observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            network_error.visibility = if (viewModel.isListEmpty() &&
                it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
    }
}
