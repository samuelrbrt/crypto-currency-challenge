package com.yoti.android.cryptocurrencychallenge.config.ext

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.databinding.BindingAdapter
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