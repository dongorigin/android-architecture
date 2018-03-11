package cn.dong.architecture.ui.stories

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import cn.dong.architecture.data.model.Story
import cn.dong.architecture.ui.NewsViewHolder
import cn.dong.architecture.ui.story.StoryActivity
import cn.dong.architecture.util.GlideApp

/**
 * @author dong on 2018/03/11.
 */
class StoriesAdapter : PagedListAdapter<Story, NewsViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.title.text = story.title
            GlideApp.with(holder.image)
                    .load(story.images.first())
                    .centerCrop()
                    .into(holder.image)
            holder.itemView.setOnClickListener { StoryActivity.start(holder.itemView.context, story.id) }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }
}