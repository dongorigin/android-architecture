package cn.dong.architecture.data.model

/**
 * @author dong on 2017/12/04.
 */
data class LatestNews(
        val date: String,
        val stories: List<Story>,
        val top_stories: List<TopStory>)
