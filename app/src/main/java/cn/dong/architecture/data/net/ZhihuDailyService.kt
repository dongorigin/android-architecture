package cn.dong.architecture.data.net

import cn.dong.architecture.data.model.LatestNews
import cn.dong.architecture.data.model.News
import cn.dong.architecture.data.model.StoryDetail
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author dong on 2017/12/03.
 */
interface ZhihuDailyService {
    @GET("4/news/latest")
    fun getLatestNews(): Flowable<LatestNews>

    @GET("4/news/before/{date}")
    fun getNews(@Path("date") date: String): Flowable<News>

    @GET("4/news/{id}")
    fun getStory(@Path("id") id: Int): Flowable<StoryDetail>
}
