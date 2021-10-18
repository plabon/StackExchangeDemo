package com.jukti.stackexchange.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.jukti.stackexchange.data.model.StackExchangeTag


fun ImageView.loadFromUrl(url: String) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context.applicationContext)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("toptags")
fun showTags(cgTag: ChipGroup, tags: List<StackExchangeTag>?) {
    tags?.let {
        cgTag.removeAllViews()
        for (tag:StackExchangeTag in it) {
            val chip = Chip(cgTag.context)
            chip.text = tag.tagName
            cgTag.addView(chip)
        }
    }
}