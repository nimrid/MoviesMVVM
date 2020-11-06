package ng.com.gocheck.moviesmvvm.model.trailer


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id")
    val id: String,
    val iso6391: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("site")
    val site: String,
    @SerializedName("size")
    val size: Int,
    @SerializedName("type")
    val type: String
)