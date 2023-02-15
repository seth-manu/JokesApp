package com.android.jokes.presentation.jokes

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.android.jokes.R
import com.android.jokes.databinding.ActivityPostsBinding
import com.android.jokes.utils.isNetworkAvailable
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.viewModel

class JokesActivity : AppCompatActivity() {

    private lateinit var activityPostsBinding: ActivityPostsBinding
    private var mAdapter: JokesAdapter? = JokesAdapter()
    private val postViewModel: JokesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPostsBinding = DataBindingUtil.setContentView(this, R.layout.activity_posts)

        activityPostsBinding.postsRecyclerView.adapter = mAdapter

        with(postViewModel) {

            postsData.observe(this@JokesActivity, Observer {
                activityPostsBinding.postsProgressBar.visibility = GONE
                mAdapter?.mPostList = it
            })

            messageData.observe(this@JokesActivity, Observer {
                Toast.makeText(this@JokesActivity, it, LENGTH_LONG).show()
            })

            showProgressbar.observe(this@JokesActivity, Observer { isVisible ->
                posts_progress_bar.visibility = if (isVisible) VISIBLE else GONE
            })
        }

        startCoroutineTimer(0L, 10000) {
            try {
                if (isNetworkAvailable()) {
                    postViewModel.getJokes()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.no_internet_connection),
                        LENGTH_SHORT
                    ).show()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.d(TAG, e.message.toString())
            }
        }
    }


    override fun onDestroy() {
        mAdapter = null
        super.onDestroy()
    }

    companion object {
        private val TAG = JokesActivity::class.java.name
    }

    private inline fun startCoroutineTimer(
        delayMillis: Long = 0,
        repeatMillis: Long = 0,
        crossinline action: () -> Unit
    ) = GlobalScope.launch {
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (true) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }
}
