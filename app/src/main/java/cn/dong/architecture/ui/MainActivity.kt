package cn.dong.architecture.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.dong.architecture.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author dong on 2017/12/03.
 */
class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(NewsViewModel::class.java) }
    private val adapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.adapter = adapter

        viewModel.getStories().observe(this, Observer {
            if (it != null) {
                adapter.stories.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.init()
    }
}
