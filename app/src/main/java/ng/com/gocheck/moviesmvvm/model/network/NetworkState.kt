package ng.com.gocheck.moviesmvvm.model.network

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status, val message : String){

    companion object{
        val LOADED : NetworkState = NetworkState(Status.SUCCESS, "Success")
        val LOADING : NetworkState = NetworkState(Status.RUNNING, "Loading")
        val ERROR : NetworkState = NetworkState(Status.FAILED, "Failed")

    }
}