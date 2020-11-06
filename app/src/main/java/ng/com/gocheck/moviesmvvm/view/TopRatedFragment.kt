package ng.com.gocheck.moviesmvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_top_rated.*
import kotlinx.android.synthetic.main.fragment_upcoming_movie.*
import ng.com.gocheck.moviesmvvm.R
import ng.com.gocheck.moviesmvvm.network.MoviesApi
import ng.com.gocheck.moviesmvvm.repository.MovieRepository
import ng.com.gocheck.moviesmvvm.viewModel.MoviesFactory
import ng.com.gocheck.moviesmvvm.viewModel.MoviesViewModel

class TopRatedFragment : Fragment() {

    private lateinit var viewModel : MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rated, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repository = MovieRepository( MoviesApi() )
        val factory = MoviesFactory(repository)
        viewModel = ViewModelProviders.of(this, factory)
            .get(MoviesViewModel::class.java)

        viewModel.topRated()
        viewModel.topRatedMovie.observe(viewLifecycleOwner, Observer { movies ->

            top_rated_recycler.also {
                it.layoutManager = GridLayoutManager(activity, 3)
                it.setHasFixedSize(true)
                it.adapter = MovieAdapter(context!!, movies.results)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearCoroutineJobs()
    }

}