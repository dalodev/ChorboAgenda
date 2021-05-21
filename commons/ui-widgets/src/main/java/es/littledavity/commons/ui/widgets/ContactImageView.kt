/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.paulrybitskyi.commons.ktx.getColor
import com.paulrybitskyi.commons.ktx.getDimension
import com.paulrybitskyi.commons.ktx.getDimensionPixelSize
import com.paulrybitskyi.commons.ktx.getDrawable
import com.paulrybitskyi.commons.ktx.getString
import com.paulrybitskyi.commons.ktx.setHorizontalPadding
import com.paulrybitskyi.commons.ktx.views.setFontFamily
import com.paulrybitskyi.commons.ktx.views.setTextSizeInPx
import com.paulrybitskyi.commons.utils.observeChanges
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    var isTitleVisible: Boolean = true
        set(value) {
            field = value
            titleTv.isVisible = value
        }

    var title: CharSequence
        set(value) { titleTv.text = value }
        get() = titleTv.text

    var imageUrl by observeChanges<String?>(null) { oldValue, newValue ->
        if((coverIv.drawable == null) || (oldValue != newValue)) loadImage(newValue)
    }

    private var defaultDrawable = checkNotNull(getDrawable(android.R.drawable.screen_background_dark))

    private lateinit var titleTv: AppCompatTextView
    private lateinit var coverIv: AppCompatImageView

//    @Inject lateinit var imageLoader: ImageLoader

    init {
        initCard()
        initUi(context)
    }

    private fun initCard() {
        setCardBackgroundColor(getColor(R.color.contact_card_background_color))
        cardElevation = getDimension(R.dimen.contact_card_elevation)
        radius = getDimension(R.dimen.contact_image_card_corner_radius)
    }

    private fun initUi(context: Context) {
        initCoverImageView(context)
        initTitleTextView(context)
    }

    private fun initTitleTextView(context: Context) {
        titleTv = AppCompatTextView(context)
            .apply {
                layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER)
                setHorizontalPadding(getDimensionPixelSize(R.dimen.contact_image_title_horizontal_padding))
                setTextSizeInPx(getDimension(R.dimen.contact_image_title_text_size))
                setTextColor(getColor(R.color.contact_image_title_text_color))
                setFontFamily(getString(R.string.text_font_family_medium))
                gravity = Gravity.CENTER
            }
            .also(::addView)
    }

    private fun initCoverImageView(context: Context) {
        coverIv = AppCompatImageView(context)
            .apply {
                layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            .also(::addView)
    }

    private fun loadImage(url: String?) {
        if(url == null) {
            showDefaultImage()
            return
        }

        /*imageLoader.loadImage(
            Config.Builder()
                .fit()
                .centerCrop()
                .imageUrl(url)
                .target(coverIv)
                .progressDrawable(defaultDrawable)
                .errorDrawable(defaultDrawable)
                .onStart(::onImageLoadingStarted)
                .onSuccess(::onImageLoadingSucceeded)
                .onFailure(::onImageLoadingFailed)
                .build()
        )*/
    }

    private fun showDefaultImage() {
        showTitle()
        coverIv.setImageDrawable(defaultDrawable)
    }

    private fun onImageLoadingStarted() {
        showTitle()
    }

    private fun onImageLoadingSucceeded() {
        hideTitle()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onImageLoadingFailed(error: Exception) {
        showTitle()
    }

    private fun showTitle() {
        if(!isTitleVisible) return

        titleTv.isVisible = true
    }

    private fun hideTitle() {
        if(!isTitleVisible) return

        titleTv.isVisible = false
    }

    fun disableRoundCorners() {
        radius = 0f
    }
}
