package cn.dong.architecture.data

import cn.dong.architecture.data.model.Story
import cn.dong.architecture.data.net.OkHttpManager
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
}