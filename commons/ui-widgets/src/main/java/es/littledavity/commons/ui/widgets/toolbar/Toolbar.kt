/*
 * Copyright 2021 dalodev
 */
package es.littledavity.commons.ui.widgets.toolbar

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import es.littledavity.commons.ui.extensions.clearEndMargin
import es.littledavity.commons.ui.extensions.endMargin
import es.littledavity.commons.ui.extensions.getColor
import es.littledavity.commons.ui.extensions.getDimension
import es.littledavity.commons.ui.extensions.getDimensionPixelSize
import es.littledavity.commons.ui.extensions.getFont
import es.littledavity.commons.ui.extensions.getString
import es.littledavity.commons.ui.extensions.layoutInflater
import es.littledavity.commons.ui.extensions.observeChanges
import es.littledavity.commons.ui.extensions.onClick
import es.littledavity.commons.ui.extensions.setColor
import es.littledavity.commons.ui.extensions.setHorizontalPadding
import es.littledavity.commons.ui.extensions.setLayoutParamsSize
import es.littledavity.commons.ui.extensions.setTextSizeInPx
import es.littledavity.commons.ui.extensions.updatePadding
import es.littledavity.commons.ui.extensions.verticalPadding
import es.littledavity.commons.ui.widgets.R
import es.littledavity.commons.ui.widgets.databinding.ViewToolbarBinding
import es.littledavity.commons.ui.widgets.toolbar.TitleGravity.Companion.asTitleGravity
import kotlin.math.max

class Toolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val defaultBackgroundColor = getColor(R.color.toolbar_background_color)
    private val defaultToolbarHeight = getDimensionPixelSize(R.dimen.toolbar_height)
    private val defaultButtonConfig = ButtonConfig(
        buttonContainerSize = getDimensionPixelSize(R.dimen.toolbar_button_container_size),
        buttonIconSize = getDimensionPixelSize(R.dimen.toolbar_button_icon_size),
        buttonIconPadding = getDimensionPixelSize(R.dimen.toolbar_button_icon_padding)
    )
    private val defaultTitleConfig = TitleConfig(
        horizontalPaddingWithoutIcon = getDimensionPixelSize(R.dimen.toolbar_title_horizontal_padding_without_icon),
        horizontalPaddingWithIcon = getDimensionPixelSize(R.dimen.toolbar_title_horizontal_padding_with_icon)
    )

    private val binding = ViewToolbarBinding.inflate(context.layoutInflater, this)

    var isLeftButtonVisible: Boolean
        set(value) {
            binding.leftBtnContainer.isVisible = value
            onLeftButtonVisibilityChanged()
        }
        get() = binding.leftBtnContainer.isVisible

    var isRightButtonVisible: Boolean
        set(value) {
            binding.rightBtnContainer.isVisible = value
            onRightButtonVisibilityChanged()
        }
        get() = binding.rightBtnContainer.isVisible

    var isExtraRightButtonVisible: Boolean
        set(value) {
            binding.extraRightBtnContainer.isVisible = value
            onRightButtonVisibilityChanged()
        }
        get() = binding.extraRightBtnContainer.isVisible

    private val areBothRightButtonsVisible: Boolean
        get() = isRightButtonVisible && isExtraRightButtonVisible

    @get:ColorInt
    var buttonIconColor: Int = getColor(R.color.toolbar_button_icon_color)
        set(@ColorInt value) {
            field = value
            leftButtonIcon = leftButtonIcon
            extraRightButtonIcon = extraRightButtonIcon
            rightButtonIcon = rightButtonIcon
        }

    @get:ColorInt
    var buttonRippleColor: Int = getColor(R.color.toolbar_button_ripple_color)
        set(@ColorInt value) {
            field = value
            binding.leftBtnIv.background = binding.leftBtnIv.background?.setColor(value)
            binding.rightBtnIv.background = binding.rightBtnIv.background?.setColor(value)
            binding.extraRightBtnIv.background = binding.extraRightBtnIv.background?.setColor(value)
        }

    @get:ColorInt
    var titleTextColor: Int = getColor(R.color.toolbar_title_text_color)
        set(@ColorInt value) {
            field = value
            binding.titleTv.setTextColor(value)
        }

    var titleTextSize: Float
        set(value) {
            binding.titleTv.setTextSizeInPx(value)
        }
        get() = binding.titleTv.textSize

    /**
     * A field to access the text gravity of the title.
     *
     * Note: When both right buttons are visible, setting any other value except
     * TitleGravity.LEFT will throw an exception.
     *
     * @see TitleGravity
     */
    var titleTextGravity by observeChanges(TitleGravity.CENTER) { _, newValue ->
        checkNewTitleGravity(newValue)
        binding.titleTv.gravity = newValue.gravity or Gravity.CENTER_VERTICAL
        updateTitleHorizontalPadding()
    }

    var titleTextTypeface: Typeface
        set(value) {
            binding.titleTv.typeface = value
        }
        get() = binding.titleTv.typeface

    var titleText: CharSequence
        set(value) {
            binding.titleTv.text = value
        }
        get() = binding.titleTv.text

    var leftButtonIcon: Drawable?
        set(value) {
            binding.leftBtnIv.setImageDrawable(value?.setColor(buttonIconColor))
            isLeftButtonVisible = value != null
        }
        get() = binding.leftBtnIv.drawable

    var rightButtonIcon: Drawable?
        set(value) {
            binding.rightBtnIv.setImageDrawable(value?.setColor(buttonIconColor))
            isRightButtonVisible = value != null
        }
        get() = binding.rightBtnIv.drawable

    var extraRightButtonIcon: Drawable?
        set(value) {
            binding.extraRightBtnIv.setImageDrawable(value?.setColor(buttonIconColor))
            isExtraRightButtonVisible = value != null
        }
        get() = binding.extraRightBtnIv.drawable

    val leftButtonAnimator: ViewPropertyAnimator
        get() = binding.extraRightBtnIv.animate()

    val rightButtonAnimator: ViewPropertyAnimator
        get() = binding.rightBtnIv.animate()

    val extraRightButtonAnimator: ViewPropertyAnimator
        get() = binding.rightBtnIv.animate()

    val titleAnimator: ViewPropertyAnimator
        get() = binding.titleTv.animate()

    var buttonConfig by observeChanges(defaultButtonConfig) { _, newValue ->
        onButtonConfigChanged(newValue)
    }

    var titleConfig by observeChanges(defaultTitleConfig) { _, _ ->
        onTitleConfigChanged()
    }

    private val buttonInfos = mutableListOf<ButtonInfo>()

    var onLeftButtonClickListener: ((View) -> Unit)? = null
        set(value) {
            field = value
            binding.leftBtnContainer.onClick { field?.invoke(it) }
        }

    var onRightButtonClickListener: ((View) -> Unit)? = null
        set(value) {
            field = value
            binding.rightBtnContainer.onClick { field?.invoke(it) }
        }

    var onExtraRightButtonClickListener: ((View) -> Unit)? = null
        set(value) {
            field = value
            binding.extraRightBtnContainer.onClick { field?.invoke(it) }
        }

    init {
        elevation = getDimension(R.dimen.toolbar_elevation)

        initButtonInfos()
        initDefaults()

        attrs?.let { extractAttributes(it, defStyleAttr) }
    }

    private fun initButtonInfos() {
        buttonInfos.apply {
            add(
                ButtonInfo(
                    containerView = binding.leftBtnContainer,
                    iconView = binding.leftBtnIv
                )
            )
            add(
                ButtonInfo(
                    containerView = binding.rightBtnContainer,
                    iconView = binding.rightBtnIv
                )
            )
            add(
                ButtonInfo(
                    containerView = binding.extraRightBtnContainer,
                    iconView = binding.extraRightBtnIv
                )
            )
        }
    }

    private fun initDefaults() {
        setBackgroundColor(defaultBackgroundColor)
        buttonRippleColor = buttonRippleColor
        leftButtonIcon = leftButtonIcon
        rightButtonIcon = rightButtonIcon
        extraRightButtonIcon = extraRightButtonIcon
        titleTextGravity = titleTextGravity
        buttonConfig = buttonConfig
        titleConfig = titleConfig
    }

    private fun extractAttributes(attrs: AttributeSet, defStyleAttr: Int) {
        context.withStyledAttributes(
            set = attrs,
            attrs = R.styleable.CustomToolbar,
            defStyleAttr = defStyleAttr
        ) {
            setBackgroundColor(
                getColor(
                    R.styleable.CustomToolbar_android_background,
                    defaultBackgroundColor
                )
            )
            buttonIconColor =
                getColor(R.styleable.CustomToolbar_toolbar_buttonIconColor, buttonIconColor)
            buttonRippleColor =
                getColor(R.styleable.CustomToolbar_toolbar_buttonRippleColor, buttonRippleColor)
            leftButtonIcon = getDrawable(R.styleable.CustomToolbar_toolbar_leftButtonIcon)
            rightButtonIcon = getDrawable(R.styleable.CustomToolbar_toolbar_rightButtonIcon)
            extraRightButtonIcon =
                getDrawable(R.styleable.CustomToolbar_toolbar_extraRightButtonIcon)
            titleText = getString(R.styleable.CustomToolbar_toolbar_titleText, titleText)
            titleTextColor =
                getColor(R.styleable.CustomToolbar_toolbar_titleTextColor, titleTextColor)
            titleTextSize =
                getDimension(R.styleable.CustomToolbar_toolbar_titleTextSize, titleTextSize)
            titleTextTypeface =
                getFont(context, R.styleable.CustomToolbar_toolbar_titleTextFont, titleTextTypeface)
            titleTextGravity = getInt(
                R.styleable.CustomToolbar_toolbar_titleTextGravity,
                titleTextGravity.id
            ).asTitleGravity()
        }
    }

    private fun onLeftButtonVisibilityChanged() {
        updateTitleHorizontalPadding()
    }

    private fun onRightButtonVisibilityChanged() {
        updateExtraRightButtonContainerPosition()
        updateTitleHorizontalPadding()
        updateTitleGravity()
    }

    private fun updateExtraRightButtonContainerPosition() {
        if (!isExtraRightButtonVisible) return

        if (isRightButtonVisible) {
            binding.extraRightBtnContainer.endMargin = buttonConfig.buttonContainerSize
        } else {
            binding.extraRightBtnContainer.clearEndMargin()
        }
    }

    private fun updateTitleHorizontalPadding() {
        val leftPadding = calculateTitleLeftPadding()
        val rightPadding = calculateTitleRightPadding()

        if (titleTextGravity == TitleGravity.CENTER) {
            binding.titleTv.setHorizontalPadding(max(leftPadding, rightPadding))
        } else {
            binding.titleTv.updatePadding(
                startPadding = leftPadding,
                endPadding = rightPadding
            )
        }
    }

    private fun calculateTitleLeftPadding(): Int {
        return if (isLeftButtonVisible) {
            buttonConfig.buttonContainerSize + titleConfig.horizontalPaddingWithIcon
        } else {
            titleConfig.horizontalPaddingWithoutIcon
        }
    }

    private fun calculateTitleRightPadding(): Int {
        var totalPadding = 0
        val buttonContainerSize = buttonConfig.buttonContainerSize

        if (isRightButtonVisible) {
            totalPadding += buttonContainerSize
        }

        if (isExtraRightButtonVisible) {
            totalPadding += buttonContainerSize
        }

        totalPadding += if (isRightButtonVisible || isExtraRightButtonVisible) {
            titleConfig.horizontalPaddingWithIcon
        } else {
            titleConfig.horizontalPaddingWithoutIcon
        }

        return totalPadding
    }

    private fun updateTitleGravity() {
        if (!areBothRightButtonsVisible) return

        // When both right buttons are visible, resetting
        // the gravity to the LEFT value to avoid bad UI
        titleTextGravity = TitleGravity.LEFT
    }

    private fun checkNewTitleGravity(newTitleGravity: TitleGravity) {
        val canNotAssignNewTitleGravity =
            areBothRightButtonsVisible && newTitleGravity != TitleGravity.LEFT

        if (canNotAssignNewTitleGravity) {
            error(
                """
                Toolbar does not support setting any other title gravity
                except LEFT when both right buttons are visible.
                """.trimIndent()
            )
        }
    }

    private fun onButtonConfigChanged(newConfig: ButtonConfig) {
        buttonInfos.forEach {
            it.containerView.setLayoutParamsSize(newConfig.buttonContainerSize)
            it.iconView.setLayoutParamsSize(newConfig.buttonIconSize)
            it.iconView.setPadding(newConfig.buttonIconPadding)
        }

        updateExtraRightButtonContainerPosition()
        updateTitleHorizontalPadding()
    }

    private fun onTitleConfigChanged() {
        updateTitleHorizontalPadding()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(
                resolveSize(calculateHeight(), heightMeasureSpec),
                MeasureSpec.EXACTLY
            )
        )
    }

    private fun calculateHeight(): Int {
        return defaultToolbarHeight + verticalPadding
    }
}
