package com.android.jokes.presentation.jokes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.android.jokes.R
import com.android.jokes.databinding.HolderPostBinding
import com.android.jokes.domain.model.Joke
import kotlin.properties.Delegates

class JokesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mPostList: List<String> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holderPostBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.holder_post, parent, false
        )
        return PostViewHolder(holderPostBinding)
    }

    override fun getItemCount(): Int = if (mPostList.isEmpty()) 0 else mPostList.size

    private fun getItem(position: Int): String = mPostList[position]

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).onBind(getItem(position))
    }

    private inner class PostViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun onBind(post: String) {
            val joke: Joke = Joke(post)
            (viewDataBinding as HolderPostBinding).joke = joke
        }
    }
}