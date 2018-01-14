package cn.dong.architecture.ui.story

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.dong.architecture.R
import kotlinx.android.synthetic.main.activity_story.*

/**
 * @author dong on 2017/12/04.
 */
class StoryActivity : AppCompatActivity() {
    private val id by lazy { intent.getIntExtra(KEY_ID, -1) }
    private val viewModel by lazy { ViewModelProviders.of(this).get(StoryViewModel::class.java) }

    companion object {
        private const val KEY_ID = "id"

        fun start(context: Context, id: Int) {
            val intent = Intent(context, StoryActivity::class.java)
            intent.putExtra(KEY_ID, id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        viewModel.init(id)?.observe(this, Observer {
            webView.loadUrl(it?.share_url)
        })
    }
}