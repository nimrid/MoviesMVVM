package ng.com.gocheck.moviesmvvm.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_movie_detail.*
import ng.com.gocheck.moviesmvvm.R
import ng.com.gocheck.moviesmvvm.network.MoviesApi
import ng.com.gocheck.moviesmvvm.network.POSTER_URL
import ng.com.gocheck.moviesmvvm.repository.MovieRepository
import ng.com.gocheck.moviesmvvm.viewModel.MovieDetailVM
import ng.com.gocheck.moviesmvvm.viewModel.MovieDetailVMFactory
import ng.com.gocheck.moviesmvvm.viewModel.MoviesFactory
import ng.com.gocheck.moviesmvvm.viewModel.MoviesViewModel
import java.text.NumberFormat
import java.util.*


class MovieDetailActivity : AppCompatActivity() {

    private val movieApi = MoviesApi()
    private val repository = MovieRepository(movieApi)
    private val factory = MovieDetailVMFactory(repository)
    private lateinit var viewModel: MovieDetailVM

    private val fact = MoviesFactory(repository)
    private lateinit var recommendVM : MoviesViewModel

    private lateinit var videoId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getIntExtra("id", 1)
        viewModel = ViewModelProviders.of(this, factory).get(MovieDetailVM::class.java)

        recommendVM = ViewModelProviders.of(this, fact).get(MoviesViewModel::class.java)

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

//        9O5KYevUkPw   key

        recommendVM.getMovieTrailer(movieId)
        recommendVM.movieTrailer.observe(this, Observer {
            videoId = it.results[0].key
        })

        lifecycle.addObserver(activity_main_youtubePlayerView)
        activity_main_youtubePlayerView.addYouTubePlayerListener(
            object : AbstractYouTubePlayerListener(){
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            }
        )

        recommendation.setOnClickListener {
            val intent = Intent(this, SimilarMovieActivity::class.java)
            intent.putExtra("id", movieId)
            startActivity(intent)
        }

        recommendVM.getRecommendedMovie(movieId)
        recommendVM.recommendMovie.observe(this, Observer { recommended ->
//            recommend_recycler.also {
//                it.layoutManager =
//                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//                it.setHasFixedSize(true)
//                it.adapter = RecommendMovieAdapter(recommended.results!!)
//            }

        })

        }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearCoroutineJobs()
    }

}


