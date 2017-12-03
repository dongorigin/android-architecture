package cn.dong.architecture.ui

import android.support.v7.widget.RecyclerView.Adapter
import android.view.ViewGroup
import cn.dong.architecture.data.model.Story
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
        holder.title.text = stories[position].title
        GlideApp.with(holder.image)
                .load(stories[position].images.first())
                .centerCrop()
                .into(holder.image)
    }
}
