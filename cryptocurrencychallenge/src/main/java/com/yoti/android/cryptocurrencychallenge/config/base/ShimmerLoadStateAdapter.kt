package com.yoti.android.cryptocurrencychallenge.config.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yoti.android.cryptocurrencychallenge.BR
import com.yoti.android.cryptocurrencychallenge.config.ext.bind

class ShimmerLoadStateAdapter<B : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
    private val viewHolder: (B) -> LoadStateViewHolder<B>,
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder<B>>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder<B>, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder<B> {
        return parent.bind(layoutResId, viewHolder)
    }
}

open class LoadStateViewHolder<B : ViewDataBinding>(private val itemBinding: B) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(loadState: LoadState, retry: () -> Unit) {
        itemBinding.setVariable(BR.state, loadState)
        itemBinding.setVariable(BR.retry, retry)
        itemBinding.executePendingBindings()
    }
}


