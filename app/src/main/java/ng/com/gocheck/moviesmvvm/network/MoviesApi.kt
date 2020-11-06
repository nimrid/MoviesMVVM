package ng.com.gocheck.moviesmvvm.network

import ng.com.gocheck.moviesmvvm.model.latest.LatestMovie
import ng.com.gocheck.moviesmvvm.model.movieDetails.Movies
import ng.com.gocheck.moviesmvvm.model.movie.Movie
import ng.com.gocheck.moviesmvvm.model.recommend.RecommendMovie
import ng.com.gocheck.moviesmvvm.model.trailer.MovieTrailer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "3d47b7fc4d11a9a06d52804b2ade5b2d"
const val POSTER_URL = "https://image.tmdb.org/t/p/w342/"
const val TRAILER_URL = "https://www.youtube.com/watch?v="
const val FIRST_PAGE = 1
const val MOVIE_PER_PAGE = 20

interface MoviesApi {

//    trailer
//    https://api.themoviedb.org/3/movie/67/videos?api_key=3d47b7fc4d11a9a06d52804b2ade5b2d&language=en-US

//    https://api.themoviedb.org/3/movie/{movie_id}/recommendations?api_key=3d47b7fc4d11a9a06d52804b2ade5b2d&language=en-US&page=1

    @GET("movie/latest")
    suspend fun latestMovie() : Response<LatestMovie>

    @GET("movie/{movie_id}/videos")
    suspend fun movieTrailer(
        @Path("movie_id") id: Int,
    ) : Response<MovieTrailer>

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("page") page : Int
    ) : Response<Movie>

    @GET("movie/upcoming")
    suspend fun upComingMovie(
        @Query("page") page: Int = 1
    ) : Response<Movie>

    @GET("movie/top_rated")
    suspend fun topRatedMovie(
        @Query("page") page: Int = 1
    ) : Response<Movie>

    @GET("movie/{movie_id}/recommendations")
    suspend fun recommendMovie(
        @Path("movie_id") id: Int,
        @Query("page") page: Int = 1
    ) : Response<RecommendMovie>

    @GET("movie/{movie_id}")
    suspend fun movieDetails(@Path("movie_id")id: Int) : Response<Movies>

    companion object{

        operator fun invoke() : MoviesApi {

            val interceptor = Interceptor {chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .client(okHttpClient)
                .build()
                .create(MoviesApi::class.java)
        }
    }
}