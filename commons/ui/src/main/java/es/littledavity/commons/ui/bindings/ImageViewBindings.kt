/*
 * Copyright 2020 littledavity
 */
package es.littledavity.commons.ui.bindings

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import es.littledavity.commons.ui.R
import java.io.File
import kotlin.random.Random

/**
 * Set image loaded from url.
 *
 * @param url Image url to download and set as drawable.
 * @param placeholderId Drawable resource identifier to set while downloading image.
 */
@BindingAdapter(
    "imageUrl",
    "imagePlaceholder",
    requireAll = false
)
fun ImageView.imageUrl(
    url: String?,
    @DrawableRes placeholderId: Int?
) {
    load(url) {
        crossfade(true)
        placeholder(
            placeholderId?.let {
                ContextCompat.getDrawable(context, it)
            } ?: run {
                val placeholdersColors = resources.getStringArray(R.array.placeholders)
                val placeholderColor = placeholdersColors[Random.nextInt(placeholdersColors.size)]
                ColorDrawable(Color.parseColor(placeholderColor))
            }
        )
    }
}

/**
 * Set image loaded from url.
 *
 * @param url Image url to download and set as drawable.
 * @param placeholderId Drawable resource identifier to set while downloading image.
 */
@BindingAdapter(
    "imageFile",
    "imagePlaceholder",
    requireAll = false
)
fun ImageView.imageFile(
    file: File?,
    @DrawableRes placeholderId: Int?
) {
    load(file) {
        crossfade(true)
        placeholder(
            placeholderId?.let {
                ContextCompat.getDrawable(context, it)
            } ?: run {
                val placeholdersColors = resources.getStringArray(R.array.placeholders)
                val placeholderColor = placeholdersColors[Random.nextInt(placeholdersColors.size)]
                ColorDrawable(Color.parseColor(placeholderColor))
            }
        )
    }
}

@BindingAdapter("app:srcVector")
fun ImageView.setSrcVector(@DrawableRes drawable: Int) {
    this.setImageResource(drawable)
}


/**
 * Set image loaded from url.
 *
 * @param url Image url to download and set as drawable.
 * @param placeholderId Drawable resource identifier to set while downloading image.
 */
@BindingAdapter(
    "imageUri",
    "imagePlaceholder",
    requireAll = false
)
fun ImageView.imageUri(
    uri: Uri?,
    @DrawableRes placeholderId: Int?
) {
    load(uri) {
        crossfade(true)
        placeholder(
            placeholderId?.let {
                ContextCompat.getDrawable(context, it)
            } ?: run {
                val placeholdersColors = resources.getStringArray(R.array.placeholders)
                val placeholderColor = placeholdersColors[Random.nextInt(placeholdersColors.size)]
                ColorDrawable(Color.parseColor(placeholderColor))
            }
        )
    }
}
