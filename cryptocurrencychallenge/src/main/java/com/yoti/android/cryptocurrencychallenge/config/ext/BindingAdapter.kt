package com.yoti.android.cryptocurrencychallenge.config.ext

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout


@BindingAdapter("roundImageUrl")
fun ImageView.loadRoundImage(imageUrl: String?) {
    if (imageUrl.isNullOrEmpty()) {
        setImageDrawable(null)
        return
    }

    GlideApp.with(this)
        .load(imageUrl)
        .circleCrop()
        .into(this)
}

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

@BindingAdapter("dotStartDrawable", "drawableDotColorStart", requireAll = true)
fun TextView.colorDotDrawableStart(drawable: Drawable, colorHex: String?) {
    if (colorHex == null) {
        setCompoundDrawables(null, null, null, null)
        return
    }

    val color = Color.parseColor(colorHex)
    when (drawable) {
        is ShapeDrawable -> {
            drawable.paint.color = color
        }
        is GradientDrawable -> {
            drawable.setColor(color)
        }
        is ColorDrawable -> {
            drawable.color = color
        }
    }

    setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
}