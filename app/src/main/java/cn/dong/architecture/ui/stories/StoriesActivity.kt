package cn.dong.architecture.ui.stories

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.dong.architecture.R
import kotlinx.android.synthetic.main.activity_stories.*

/**
 * @author dong on 2018/03/11.
 */
class StoriesActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(StoriesViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stories)

        val adapter = StoriesAdapter()
        viewModel.stories.observe(this, Observer { adapter.submitList(it) })
        list.adapter = adapter
    }
}