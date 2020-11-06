package ng.com.gocheck.moviesmvvm.repository

import retrofit2.Response
import java.io.IOException

abstract class SafeApiCall {

    suspend fun <T : Any> apiResponse(call : suspend() -> Response<T>) : T {
        val response = call.invoke()
        if (response.isSuccessful)
            return response.body()!!
        else
            throw ApiException(response.errorBody().toString())
    }

}

class ApiException(message : String) : IOException(message)