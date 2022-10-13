package com.yoti.android.cryptocurrencychallenge.config.ext

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("visibleIf")
fun View.isVisible(visible: Boolean) {
    visibility = if (visible) VISIBLE else GONE
}

@BindingAdapter("loading")
fun ShimmerFrameLayout.loading(isLoading: Boolean) {
    if (isLoading) {
        showShimmer(true)
    } else {
        hideShimmer()
    }
}

@BindingAdapter("divider")
fun RecyclerView.setDivider(drawable: Drawable) {
    addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply { setDrawable(drawable) })
}

@BindingAdapter("date_text", "date_format", requireAll = true)
fun TextView.setDateTextWithFormat(date: Long?, dateFormat: String) {
    text = try {
        if (date == null) throw NullPointerException("date is null")
        SimpleDateFormat(dateFormat, Locale.getDefault()).format(Date(date))
    } catch (error: RuntimeException) {
        null
    }
}

@BindingAdapter("visible_on_error")
fun View.visibleOnError(state: LoadState) {
    visibility = if (state is LoadState.Error) VISIBLE else GONE
}

@BindingAdapter("visible_on_loading")
fun View.visibleOnLoading(state: LoadState) {
    visibility = if (state is LoadState.Loading) VISIBLE else GONE
}

@BindingAdapter("shim_on_load_state")
fun ShimmerFrameLayout.shimOnLoadState(state: LoadState) {
    if (state is LoadState.Loading) {
        showShimmer(true)
    } else {
        hideShimmer()
    }
}