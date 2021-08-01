/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.widgets.contacts

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import es.littledavity.commons.ui.extensions.getColor
import es.littledavity.commons.ui.extensions.getDimensionPixelSize
import es.littledavity.commons.ui.extensions.getFont
import es.littledavity.commons.ui.extensions.getString
import es.littledavity.commons.ui.extensions.layoutInflater
import es.littledavity.commons.ui.extensions.setColor
import es.littledavity.commons.ui.extensions.setLayoutParamsSize
import es.littledavity.commons.ui.extensions.setSingleLineTextEnabled
import es.littledavity.commons.ui.extensions.setTextSizeInPx
import es.littledavity.commons.ui.extensions.topMargin
import es.littledavity.commons.ui.widgets.R
import es.littledavity.commons.ui.widgets.databinding.ViewInfoBinding

class InfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ViewInfoBinding.inflate(context.layoutInflater, this)

    var isTitleTextOneLiner: Boolean = false
        set(value) {
            field = value
            binding.titleTv.setSingleLineTextEnabled(value)
            binding.titleTv.ellipsize = TextUtils.TruncateAt.END
        }

    var isDescriptionTextOneLiner: Boolean = false
        set(value) {
            field = value
            binding.descriptionTv.setSingleLineTextEnabled(value)
            binding.descriptionTv.ellipsize = TextUtils.TruncateAt.END
        }

    var isDescriptionTextVisible: Boolean
        set(value) {
            binding.descriptionTv.isVisible = value
        }
        get() = binding.descriptionTv.isVisible

    var iconSize: Int = getDimensionPixelSize(R.dimen.info_view_icon_size)
        set(value) {
            field = value
            binding.iconIv.setLayoutParamsSize(value)
        }

    var titleTextTopMargin: Int
        set(value) {
            binding.titleTv.topMargin = value
        }
        get() = binding.titleTv.topMargin

    var descriptionTextTopMargin: Int
        set(value) {
            binding.descriptionTv.topMargin = value
        }
        get() = binding.descriptionTv.topMargin

    @get:ColorInt
    var iconColor: Int = getColor(R.color.info_view_icon_color)
        set(@ColorInt value) {
            field = value
            icon = icon
        }

    @get:ColorInt
    var titleTextColor: Int
        set(@ColorInt value) {
            binding.titleTv.setTextColor(value)
        }
        get() = binding.titleTv.currentTextColor

    @get:ColorInt
    var descriptionTextColor: Int
        set(@ColorInt value) {
            binding.descriptionTv.setTextColor(value)
        }
        get() = binding.descriptionTv.currentTextColor

    var titleTextSize: Float
        set(value) {
            binding.titleTv.setTextSizeInPx(value)
        }
        get() = binding.titleTv.textSize

    var descriptionTextSize: Float
        set(value) {
            binding.descriptionTv.setTextSizeInPx(value)
        }
        get() = binding.descriptionTv.textSize

    var titleTextTypeface: Typeface
        set(value) {
            binding.titleTv.typeface = value
        }
        get() = binding.titleTv.typeface

    var descriptionTextTypeface: Typeface
        set(value) {
            binding.descriptionTv.typeface = value
        }
        get() = binding.descriptionTv.typeface

    var titleText: CharSequence
        set(value) {
            binding.titleTv.text = value
        }
        get() = binding.titleTv.text

    var descriptionText: CharSequence
        set(value) {
            isDescriptionTextVisible = value.isNotBlank()
            binding.descriptionTv.text = value
        }
        get() = binding.descriptionTv.text

    var icon: Drawable?
        set(value) {
            binding.iconIv.setImageDrawable(value?.setColor(iconColor))
        }
        get() = binding.iconIv.drawable

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        attrs?.let { extractAttributes(it, defStyleAttr) }
    }

    private fun extractAttributes(attrs: AttributeSet, defStyleAttr: Int) {
        context.withStyledAttributes(
            set = attrs,
            attrs = R.styleable.InfoView,
            defStyleAttr = defStyleAttr
        ) {
            iconSize = getDimensionPixelSize(R.styleable.InfoView_infoView_iconSize, iconSize)
            titleTextSize = getDimension(R.styleable.InfoView_infoView_titleTextSize, titleTextSize)
            descriptionTextSize = getDimension(R.styleable.InfoView_infoView_descriptionTextSize, descriptionTextSize)
            titleTextTopMargin = getDimensionPixelSize(R.styleable.InfoView_infoView_titleTextTopMargin, titleTextTopMargin)
            descriptionTextTopMargin =
                getDimensionPixelSize(R.styleable.InfoView_infoView_descriptionTextTopMargin, descriptionTextTopMargin)
            iconColor = getColor(R.styleable.InfoView_infoView_iconColor, iconColor)
            titleTextColor = getColor(R.styleable.InfoView_infoView_titleTextColor, titleTextColor)
            descriptionTextColor = getColor(R.styleable.InfoView_infoView_descriptionTextColor, descriptionTextColor)
            titleTextTypeface = getFont(context, R.styleable.InfoView_infoView_titleTextFont, titleTextTypeface)
            descriptionTextTypeface = getFont(context, R.styleable.InfoView_infoView_descriptionTextFont, descriptionTextTypeface)
            icon = getDrawable(R.styleable.InfoView_infoView_icon)
            titleText = getString(R.styleable.InfoView_infoView_titleText, titleText)
            descriptionText = getString(R.styleable.InfoView_infoView_descriptionText, descriptionText)
        }
    }
}
