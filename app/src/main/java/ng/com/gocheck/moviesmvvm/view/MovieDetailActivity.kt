package ng.com.gocheck.moviesmvvm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*
import ng.com.gocheck.moviesmvvm.R
import ng.com.gocheck.moviesmvvm.model.network.MoviesApi
import ng.com.gocheck.moviesmvvm.model.network.POSTER_URL
import ng.com.gocheck.moviesmvvm.model.repository.MovieRepository
import ng.com.gocheck.moviesmvvm.viewModel.MovieDetailVM
import ng.com.gocheck.moviesmvvm.viewModel.MovieDetailVMFactory
import java.text.NumberFormat
import java.util.*

class MovieDetailActivity : AppCompatActivity() {

//    private val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
    private val movieApi = MoviesApi()
    private val repository = MovieRepository(movieApi)
    private val factory = MovieDetailVMFactory(repository)

    private lateinit var viewModel: MovieDetailVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getIntExtra("id", 1)
        viewModel = ViewModelProviders.of(this, factory).get(MovieDetailVM::class.java)

        viewModel.getMovieDetail(movieId)
        viewModel.movieDetail.observe(this, Observer {
            movie_detail_title.text = it.title
            movie_detail_release.text = it.releaseDate
            movie_overview.text = it.overview

            val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
            movie_budget.text = currencyFormat.format(it.budget)
            movie_revenue.text = currencyFormat.format(it.revenue)

            val movieImageUrl = POSTER_URL + it.posterPath
            Glide.with(this).load(movieImageUrl)
                .into(movie_detail_image)

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearCoroutineJobs()
    }
}
