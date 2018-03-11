package cn.dong.architecture.data.net

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import cn.dong.architecture.data.NetworkState
import cn.dong.architecture.data.ZhihuDailyRepository
import cn.dong.architecture.data.model.Story
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

/**
 * @author dong on 2018/03/11.
 */
class StoryDataSource : PageKeyedDataSource<String, Story>() {
    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    val initialLoad = MutableLiveData<NetworkState>()
    val networkState = MutableLiveData<NetworkState>()

    private var retry: (() -> Any)? = null

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            // todo background thread
            it.invoke()
        }
    }

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Story>) {
        initialLoad.postValue(NetworkState.LOADING)
        networkState.postValue(NetworkState.LOADING)

        ZhihuDailyRepository.getLatest()
                .subscribe({
                    val items = it.stories
                    val nextPageKey = getNextPageKey(it.date)
                    retry = null
                    initialLoad.postValue(NetworkState.LOADED)
                    networkState.postValue(NetworkState.LOADED)
                    callback.onResult(items, null, nextPageKey)
                }, {
                    retry = {
                        loadInitial(params, callback)
                    }
                    val error = NetworkState.error(it?.message ?: "unknown error")
                    initialLoad.postValue(error)
                    networkState.postValue(error)
                })
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Story>) {
        networkState.postValue(NetworkState.LOADING)
        ZhihuDailyRepository.getStories(params.key)
                .subscribe({
                    retry = null
                    networkState.postValue(NetworkState.LOADED)
                    callback.onResult(it, getNextPageKey(params.key))
                }, {
                    retry = {
                        loadAfter(params, callback)
                    }
                    networkState.postValue(NetworkState.error(it.message ?: "unknown error"))
                })
    }

    private fun getNextPageKey(date: String): String {
        return LocalDate.from(formatter.parse(date)).minusDays(1).format(formatter)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Story>) {
        // ignored, only load after
    }
}