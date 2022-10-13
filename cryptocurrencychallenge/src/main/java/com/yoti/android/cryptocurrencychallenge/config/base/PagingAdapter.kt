package com.yoti.android.cryptocurrencychallenge.config.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yoti.android.cryptocurrencychallenge.BR
import com.yoti.android.cryptocurrencychallenge.config.ext.bind

class PagingAdapter<T : Any, B : ViewDataBinding>(
    @LayoutRes private val itemLayoutResId: Int,
    private val viewHolder: (B) -> PagingViewHolder<T, B>,
    contentSame: (T, T) -> Boolean = { i1, i2 -> i1 == i2 },
    itemSame: (T, T) -> Boolean
) : PagingDataAdapter<T, PagingViewHolder<T, B>>(DiffCallback(itemSame, contentSame)) {

    override fun onBindViewHolder(holder: PagingViewHolder<T, B>, position: Int) {
        val item = getItem(position)
        holder.bindTo(item, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder<T, B> {
        return parent.bind(itemLayoutResId, viewHolder).apply { adapter = this@PagingAdapter }
    }

    fun getItemAtPosition(position: Int) = getItem(position)
}

class DiffCallback<T : Any>(
    private val itemSame: (T, T) -> Boolean,
    private val contentSame: (T, T) -> Boolean
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = itemSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T) = contentSame(oldItem, newItem)
}

open class PagingViewHolder<T : Any, B : ViewDataBinding>(private val itemBinding: B) :
    RecyclerView.ViewHolder(itemBinding.root) {
    open lateinit var adapter: PagingAdapter<T, B>

    init {
        itemBinding.root.setOnClickListener {
            onItemClick(adapter.getItemAtPosition(absoluteAdapterPosition)!!, absoluteAdapterPosition)
        }
    }

    open fun bindTo(item: T?, position: Int) {
        itemBinding.setVariable(BR.item, item)
        itemBinding.executePendingBindings()
    }

    open fun onItemClick(item: T, position: Int) {}
}