package cn.dong.architecture.ui

import android.support.v7.widget.RecyclerView.Adapter
import android.view.ViewGroup
import cn.dong.architecture.data.model.Story
import cn.dong.architecture.ui.story.StoryActivity
import cn.dong.architecture.util.GlideApp

/**
 * @author dong on 2017/12/03.
 */
class NewsAdapter : Adapter<NewsViewHolder>() {
    val stories = mutableListOf<Story>()

    override fun getItemCount(): Int {
        return stories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val story = stories[position]
        holder.title.text = story.title
        GlideApp.with(holder.image)
                .load(story.images.first())
                .centerCrop()
                .into(holder.image)
        holder.itemView.setOnClickListener { StoryActivity.start(holder.itemView.context, story.id) }
    }
}
