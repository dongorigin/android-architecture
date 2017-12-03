package cn.dong.architecture.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import cn.dong.architecture.data.ZhihuDailyRepository
import cn.dong.architecture.data.model.Story
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author dong on 2017/12/03.
 */
class NewsViewModel : ViewModel() {
    private val today: String = SimpleDateFormat("yyyyMMdd", Locale.US).format(Date())
    private val stories = MutableLiveData<List<Story>>()

    fun getStories(): LiveData<List<Story>> = stories

    fun init() {
        ZhihuDailyRepository.getStories(today)
                .subscribe {
                    stories.postValue(it)
                }
    }
}