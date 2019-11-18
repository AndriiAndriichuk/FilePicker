package com.ciuc.andrii.filepicker

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlin.math.round


fun loadWithGlide(context: Context, drawable: Int, imageView: ImageView) {
    Glide.with(context)
        .load(drawable)
        .centerCrop()
        .into(imageView)
}

fun loadWithGlide(context: Context, filePath: String, imageView: ImageView) {
    Glide.with(context)
        .load(filePath)
        .centerCrop()
        .into(imageView)
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}