package ng.com.gocheck.moviesmvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_popular.*
import ng.com.gocheck.moviesmvvm.R
import ng.com.gocheck.moviesmvvm.network.MoviesApi
import ng.com.gocheck.moviesmvvm.network.NetworkState
import ng.com.gocheck.moviesmvvm.network.POSTER_URL
import ng.com.gocheck.moviesmvvm.repository.MovieRepository
import ng.com.gocheck.moviesmvvm.viewModel.MoviesFactory
import ng.com.gocheck.moviesmvvm.viewModel.MoviesViewModel
import ng.com.gocheck.moviesmvvm.viewModel.PopularMovieVM

class PopularFragment : Fragment() {

    private lateinit var latestViewModel : MoviesViewModel
    private lateinit var popularViewModel : PopularMovieVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        popularViewModel = ViewModelProviders.of(this).get(PopularMovieVM::class.java)
        val repository = MovieRepository( MoviesApi() )
        val factory = MoviesFactory(repository)
        latestViewModel = ViewModelProviders.of(this, factory)
            .get(MoviesViewModel::class.java)

        val popMovieAdapter = PageListAdapter(activity!!)

        val gridLayout = GridLayoutManager(activity, 2)

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

        latestViewModel.getLatestMovie()
        latestViewModel.latestMovie.observe(viewLifecycleOwner, Observer {
            latest_movie_title.text = it.title
            latest_movie_release_date.text = it.releaseDate
            val movieImageUrl = POSTER_URL + it.posterPath
            Glide.with(this).load(movieImageUrl).into(latest_movie_image)
        })

        popularViewModel.getMovies().observe(viewLifecycleOwner, Observer {
            popMovieAdapter.submitList(it)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        latestViewModel.clearCoroutineJobs()
    }

}