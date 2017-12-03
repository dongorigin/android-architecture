package cn.dong.architecture.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.dong.architecture.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author dong on 2017/12/03.
 */
class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(NewsViewModel::class.java) }
    private val adapter = NewsAdapter()
    private val layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.adapter = adapter
        list.layoutManager = layoutManager
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (!viewModel.loading && layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 1) {
                    viewModel.loadMore()
                }
            }
        })

        viewModel.getStories().observe(this, Observer {
            if (it != null) {
                adapter.stories.clear()
                adapter.stories.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
    }
}
