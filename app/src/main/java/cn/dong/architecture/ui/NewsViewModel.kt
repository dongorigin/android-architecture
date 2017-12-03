package cn.dong.architecture.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import cn.dong.architecture.data.ZhihuDailyRepository
import cn.dong.architecture.data.model.Story
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

/**
 * @author dong on 2017/12/03.
 */
class NewsViewModel : ViewModel() {
    private var loadedDate: LocalDate? = null
    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    private val stories = mutableListOf<Story>()
    private val storiesDate = MutableLiveData<List<Story>>()

    var loading = false
        private set

    fun getStories(): LiveData<List<Story>> = storiesDate

    init {
        ZhihuDailyRepository.getLatest()
                .subscribe {
                    loadedDate = LocalDate.from(formatter.parse(it.date))
                    stories.addAll(it.stories)
                    storiesDate.postValue(stories)
                }
    }

    fun refresh() {
    }

    fun loadMore() {
        if (loadedDate == null) {
            return
        }
        loading = true
        loadedDate = loadedDate!!.minusDays(1)
        ZhihuDailyRepository.getStories(loadedDate!!.format(formatter))
                .subscribe {
                    loading = false
                    stories.addAll(it)
                    storiesDate.postValue(stories)
                }
    }
}