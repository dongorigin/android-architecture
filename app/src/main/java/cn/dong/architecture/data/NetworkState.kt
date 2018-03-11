package cn.dong.architecture.data

/**
 * @author dong on 2018/03/11.
 */
@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val status: Status,
        val message: String? = null) {

    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        fun error(message: String?) = NetworkState(Status.FAILED, message)
    }
}

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}