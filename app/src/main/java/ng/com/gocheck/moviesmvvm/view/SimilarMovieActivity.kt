package ng.com.gocheck.moviesmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recommend.*
import ng.com.gocheck.moviesmvvm.R
import ng.com.gocheck.moviesmvvm.network.MoviesApi
import ng.com.gocheck.moviesmvvm.repository.MovieRepository
import ng.com.gocheck.moviesmvvm.viewModel.MoviesFactory
import ng.com.gocheck.moviesmvvm.viewModel.MoviesViewModel

class SimilarMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)

        val movieId = intent.getIntExtra("id", 1)

        val movieApi = MoviesApi()
        val repository = MovieRepository(movieApi)
        val fact = MoviesFactory(repository)
        val recommendVM = ViewModelProviders.of(this, fact)
            .get(MoviesViewModel::class.java)

        recommendVM.getRecommendedMovie(movieId)
        recommendVM.recommendMovie.observe(this, Observer { recommended ->
            similar_movies.also {
                it.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                it.setHasFixedSize(true)
                it.adapter = RecommendMovieAdapter(recommended.results!!)
            }

        })

    }

}