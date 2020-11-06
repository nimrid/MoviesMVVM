package ng.com.gocheck.moviesmvvm.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_list.view.*
import kotlinx.android.synthetic.main.network_state.view.*
import ng.com.gocheck.moviesmvvm.R
import ng.com.gocheck.moviesmvvm.model.movie.Result
import ng.com.gocheck.moviesmvvm.network.NetworkState
import ng.com.gocheck.moviesmvvm.network.POSTER_URL

class PageListAdapter(private val context: Context)
    : PagedListAdapter<Result, RecyclerView.ViewHolder>(MovieDiffUtil()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2
    private var networkState : NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == MOVIE_VIEW_TYPE) {
            PopularMovieViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false)
            )
        }else
            NetworkStateViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.network_state, parent,false)
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE)
            (holder as PopularMovieViewHolder).bind(getItem(position), context)
        else
            (holder as NetworkStateViewHolder).bind(networkState!!)
    }

    inner class PopularMovieViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(result: Result?, context: Context){
            itemView.movie_title.text = result?.title
            itemView.movie_release_date.text = result?.releaseDate

            val moviePosterUrl = POSTER_URL + result?.posterPath
            Glide.with(itemView.context)
                .load(moviePosterUrl)
                .into(itemView.movie_image)

            itemView.setOnClickListener {
                val intent = Intent(context.applicationContext, MovieDetailActivity::class.java)
                intent.putExtra("id", result?.id)
                context.startActivity(intent)
            }
        }
    }

    inner class NetworkStateViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(networkState: NetworkState?){
            if(networkState == NetworkState.LOADING)
                itemView.progress_bar_next_page.visibility = View.VISIBLE
            else
                itemView.progress_bar_next_page.visibility = View.GONE

            if (networkState == NetworkState.ERROR){
                itemView.network_error_endlist.visibility = View.VISIBLE
                itemView.network_error_endlist.text = networkState.message
            }else
                itemView.network_error_endlist.visibility = View.GONE
        }

    }

    private fun hasExtraRow() : Boolean {
        return (networkState != null && networkState != NetworkState.LOADED)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1)
            NETWORK_VIEW_TYPE
        else
            MOVIE_VIEW_TYPE
    }

    fun setNetworkState(networkState: NetworkState){
        val previousNetworkState = this.networkState
        val hasExtraRow = hasExtraRow()

        this.networkState = networkState

        if (hasExtraRow != hasExtraRow){
            if (hasExtraRow)
                notifyItemRemoved(super.getItemCount())
            else
                notifyItemInserted(super.getItemCount())

        }

    }

    class MovieDiffUtil : DiffUtil.ItemCallback<Result>(){

        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
            oldItem == newItem

    }

}
