package cn.dong.architecture.ui.story

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import cn.dong.architecture.data.ZhihuDailyRepository
import cn.dong.architecture.data.model.StoryDetail

/**
 * @author dong on 2017/12/04.
 */
class StoryViewModel : ViewModel() {
    var story: MutableLiveData<StoryDetail>? = null

    fun init(id: Int): LiveData<StoryDetail>? {
        if (story != null) {
            return story
        }
        story = MutableLiveData<StoryDetail>()
        ZhihuDailyRepository.getStoryDetail(id).subscribe {
            story?.postValue(it)
        }
        return story
    }
}