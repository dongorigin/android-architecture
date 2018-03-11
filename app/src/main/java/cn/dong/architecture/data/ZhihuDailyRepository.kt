package cn.dong.architecture.data

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.support.annotation.MainThread
import cn.dong.architecture.data.model.Story
import cn.dong.architecture.data.model.StoryDetail
import cn.dong.architecture.data.net.OkHttpManager
import cn.dong.architecture.data.net.StoryDataSourceFactory
import cn.dong.architecture.data.net.ZhihuDailyService
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author dong on 2017/12/03.
 */
object ZhihuDailyRepository {
    private val retrofit: Retrofit
    private val zhihuDailyService: ZhihuDailyService

    init {
        retrofit = Retrofit.Builder()
                .baseUrl("https://news-at.zhihu.com/api/")
                .client(OkHttpManager.client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
        zhihuDailyService = retrofit.create(ZhihuDailyService::class.java)
    }

    fun getLatest() = zhihuDailyService.getLatestNews()

    fun getStories(date: String): Flowable<List<Story>> {
        return zhihuDailyService.getNews(date)
                .map { it.stories }
    }

    fun getStoryDetail(id: Int): Flowable<StoryDetail> {
        return zhihuDailyService.getStory(id)
    }

    @MainThread
    fun stories(): Listing<Story> {
        val sourceFactory = StoryDataSourceFactory()
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(1) // 知乎的接口没有用到每页大小
                .setPrefetchDistance(2)
                .build()
        val pagedList = LivePagedListBuilder(sourceFactory, config)
                .build()
        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }
        val networkState = Transformations.switchMap(sourceFactory.sourceLiveData, {
            it.networkState
        })
        val refresh: () -> Unit = { sourceFactory.sourceLiveData.value?.invalidate() }
        val retry: () -> Unit = { sourceFactory.sourceLiveData.value?.retryAllFailed() }
        return Listing(
                pagedList,
                networkState,
                refreshState,
                refresh,
                retry
        )
    }
}