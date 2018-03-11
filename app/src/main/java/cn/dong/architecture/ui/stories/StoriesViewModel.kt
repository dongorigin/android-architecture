package cn.dong.architecture.ui.stories

import android.arch.lifecycle.ViewModel
import cn.dong.architecture.data.ZhihuDailyRepository

/**
 * @author dong on 2018/03/11.
 */
class StoriesViewModel : ViewModel() {
    private val repoResult = ZhihuDailyRepository.stories()

    val stories = repoResult.pagedList

}