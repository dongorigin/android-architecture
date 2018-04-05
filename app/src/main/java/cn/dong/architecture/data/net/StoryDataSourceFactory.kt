package cn.dong.architecture.data.net

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import cn.dong.architecture.data.model.Story

/**
 * @author dong on 2018/03/11.
 */
class StoryDataSourceFactory : DataSource.Factory<String, Story>() {

    val sourceLiveData = MutableLiveData<StoryDataSource>()

    override fun create(): DataSource<String, Story> {
        val source = StoryDataSource()
        sourceLiveData.postValue(source)
        return source
    }
}