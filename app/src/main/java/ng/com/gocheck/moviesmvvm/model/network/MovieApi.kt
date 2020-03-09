package ng.com.gocheck.moviesmvvm.model.network

import ng.com.gocheck.movies.model.db.movieDetails.Movies
import ng.com.gocheck.moviesmvvm.model.popularMovies.PopularMovies
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
//    get movie details url
//    https://api.themoviedb.org/3/movie/38700?api_key=3d47b7fc4d11a9a06d52804b2ade5b2d
//    get popular movies url
//    https://api.themoviedb.org/3/movie/popular?api_key=3d47b7fc4d11a9a06d52804b2ade5b2d&page=1

const val API_KEY = "3d47b7fc4d11a9a06d52804b2ade5b2d"
const val POSTER_URL = "https://image.tmdb.org/t/p/w342/"
const val FIRST_PAGE = 1
const val MOVIE_PER_PAGE = 20

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("page") page : Int
    ) : Response<PopularMovies>

    @GET("movie/{movie_id}")
    suspend fun movieDetails(@Path("movie_id")id: Int) : Response<Movies>

    companion object{

        operator fun invoke(
//            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MoviesApi {

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
//                .addInterceptor(networkConnectionInterceptor)
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