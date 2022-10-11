package com.yoti.android.cryptocurrencychallenge.config.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

fun <T : RecyclerView.ViewHolder, R : ViewDataBinding> ViewGroup.bind(
    @LayoutRes resId: Int, construct: (R) -> T,
): T {
    val inflater = LayoutInflater.from(context)
    val binding: R = DataBindingUtil.inflate(inflater, resId, this, false)
    return construct(binding)
}

fun ViewGroup.bind(@LayoutRes resId: Int): View {
    val inflater = LayoutInflater.from(context)
    return inflater.inflate(resId, this, false)
}