package ng.com.gocheck.moviesmvvm.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_list.view.*
import ng.com.gocheck.moviesmvvm.R
import ng.com.gocheck.moviesmvvm.model.movie.Result
import ng.com.gocheck.moviesmvvm.network.POSTER_URL

class MovieAdapter(private val context: Context, private val movie : List<Result>)
    : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val upComing = movie[position]
        holder.itemView.movie_title.text = upComing.title
        holder.itemView.movie_release_date.text = upComing.releaseDate

        val moviePosterUrl = POSTER_URL + upComing.posterPath
        Glide.with(holder.itemView.context)
            .load(moviePosterUrl)
            .into(holder.itemView.movie_image)

        holder.itemView.setOnClickListener {
            val intent = Intent(context.applicationContext, MovieDetailActivity::class.java)
            intent.putExtra("id", upComing.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = movie.size

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)

}