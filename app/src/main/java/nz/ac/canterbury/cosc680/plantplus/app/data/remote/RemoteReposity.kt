package nz.ac.canterbury.cosc680.plantplus.app.data.remote

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

}

data class Response< T >(val error: Boolean, val message:String, val code : Int, val t:T)
