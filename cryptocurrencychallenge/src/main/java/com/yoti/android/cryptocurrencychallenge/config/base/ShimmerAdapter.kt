package com.yoti.android.cryptocurrencychallenge.config.base

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.yoti.android.cryptocurrencychallenge.config.ext.bind

open class ShimmerAdapter<T: Any, B : ViewDataBinding>(
    @LayoutRes itemLayoutResId: Int,
    @LayoutRes private val shimmerLayoutResId: Int,
    viewHolder: (B) -> BaseDataBindingHolder<T, B>,
    items: List<T> = emptyList(),
    private val shimmerItemCount: Int = 8,
    var isShimming: Boolean = true,
) : RecyclerAdapter<T, B>(itemLayoutResId, viewHolder, items) {

    init {
        isShimming = items.isEmpty()
    }

    override fun getItemCount() = if (isShimming) shimmerItemCount else super.getItemCount()

    override fun getItemViewType(position: Int): Int {
        if (isShimming) return SHIMMER_VIEW_TYPE
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == SHIMMER_VIEW_TYPE) {
            return ShimmerViewHolder(parent.bind(shimmerLayoutResId))
        }

        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ShimmerViewHolder) {
            super.onBindViewHolder(holder, position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setShimmer(shimming: Boolean) {
        isShimming = shimming
        notifyDataSetChanged()
    }

    companion object {
        private const val SHIMMER_VIEW_TYPE = -1
    }
}

class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)