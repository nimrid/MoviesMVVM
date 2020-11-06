package ng.com.gocheck.moviesmvvm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recommend_movie.view.*
import ng.com.gocheck.moviesmvvm.R
import ng.com.gocheck.moviesmvvm.model.recommend.ResultsItem
import ng.com.gocheck.moviesmvvm.network.POSTER_URL

class RecommendMovieAdapter(private val result: List<ResultsItem> )
    : RecyclerView.Adapter<RecommendMovieAdapter.RecommendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        return RecommendViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recommend_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        val recommended = result[position]
        holder.itemView.recommend_title.text = recommended.title
        holder.itemView.recommend_popular.text = "Popularity ${recommended.popularity}"

        val moviePosterUrl = POSTER_URL + recommended.posterPath
        Glide.with(holder.itemView.context)
            .load(moviePosterUrl)
            .into(holder.itemView.recommend_image)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class RecommendViewHolder(view: View) : RecyclerView.ViewHolder(view)
}