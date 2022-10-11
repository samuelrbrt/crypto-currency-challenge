package com.yoti.android.cryptocurrencychallenge.config.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yoti.android.cryptocurrencychallenge.BR
import com.yoti.android.cryptocurrencychallenge.config.ext.bind

open class RecyclerAdapter<T: Any, B : ViewDataBinding>(
    @LayoutRes private val itemLayoutResId: Int,
    private val viewHolder: (B) -> BaseDataBindingHolder<T, B>,
    var items: List<T> = mutableListOf(),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseDataBindingHolder<*, *>) {
            holder.bindTo(items[position], position)
        }
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return parent.bind(itemLayoutResId, viewHolder).apply { adapter = this@RecyclerAdapter }
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun updateItems(
        newItems: List<T>?,
        itemSame: (T, T) -> Boolean = { i1, i2 -> i1 == i2 },
        contentSame: (T, T) -> Boolean = { i1, i2 -> i1 == i2 }
    ) {
        if (newItems == null) {
            items = mutableListOf()
            notifyDataSetChanged()
            return
        }

        if (items.isEmpty()) {
            items = newItems
            notifyDataSetChanged()
            return
        }

        val diffResult = DiffUtil.calculateDiff(DiffCalc(items, newItems, itemSame, contentSame))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    fun getItemPosition(item: T) = items.indexOf(item)

    class DiffCalc<T>(
        private val oldItem: List<T>,
        private val newItems: List<T>,
        private val itemSame: (T, T) -> Boolean,
        private val contentSame: (T, T) -> Boolean
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            itemSame(oldItem[oldItemPosition], newItems[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            contentSame(oldItem[oldItemPosition], newItems[newItemPosition])

        override fun getOldListSize() = oldItem.size

        override fun getNewListSize() = newItems.size
    }
}

open class BaseDataBindingHolder<T: Any, B : ViewDataBinding>(protected val itemBinding: B) : RecyclerView.ViewHolder(itemBinding.root) {
    open lateinit var adapter: RecyclerAdapter<T, B>

    init {
        itemBinding.root.setOnClickListener {
            onItemClick(adapter.items[bindingAdapterPosition], bindingAdapterPosition)
        }
    }

    open fun bindTo(item: Any, position: Int) {
        itemBinding.setVariable(BR.item, item)
        itemBinding.executePendingBindings()
    }

    open fun onItemClick(item: T, position: Int) {
        itemBinding.executePendingBindings()
    }
}