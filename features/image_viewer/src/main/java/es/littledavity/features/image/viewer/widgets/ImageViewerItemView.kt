/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.image.viewer.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.extensions.layoutInflater
import es.littledavity.commons.ui.extensions.observeChanges
import es.littledavity.core.providers.StringProvider
import es.littledavity.features.image.viewer.R
import es.littledavity.features.image.viewer.databinding.ViewImageViewerItemBinding
import es.littledavity.imageLoading.Config
import es.littledavity.imageLoading.ImageLoader
import javax.inject.Inject

private const val DEFAULT_MIN_SCALE = 1f
private const val DEFAULT_MID_SCALE = 1.75f
private const val DEFAULT_MAX_SCALE = 3f

@AndroidEntryPoint
internal class ImageViewerItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewImageViewerItemBinding.inflate(context.layoutInflater, this)

    private var isInfoViewVisible: Boolean
        set(value) {
            binding.infoView.isVisible = value
        }
        get() = binding.infoView.isVisible

    private var infoViewTitleText: CharSequence
        set(value) {
            binding.infoView.titleText = value
        }
        get() = binding.infoView.titleText

    val isScaled: Boolean
        get() = binding.photoView.scale != DEFAULT_MIN_SCALE

    var imageUrl by observeChanges<String?>(null) { _, newModel ->
        newModel?.let(::loadUrlImage)
    }

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var stringProvider: StringProvider

    init {
        initPhotoView()
    }

    private fun initPhotoView() = with(binding.photoView) {
        minimumScale = DEFAULT_MIN_SCALE
        mediumScale = DEFAULT_MID_SCALE
        maximumScale = DEFAULT_MAX_SCALE
    }

    private fun loadUrlImage(url: String) {
        imageLoader.loadImage(
            Config.Builder()
                .fit()
                .centerInside()
                .imageUrl(url)
                .target(binding.photoView)
                .onSuccess(::onImageLoadingSucceeded)
                .onFailure(::onImageLoadingFailed)
                .build()
        )
    }

    private fun onImageLoadingSucceeded() {
        isInfoViewVisible = false
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onImageLoadingFailed(error: Exception) {
        infoViewTitleText = stringProvider.getString(R.string.error_unknown_message)
        isInfoViewVisible = true
    }

    fun resetScale(animate: Boolean) {
        binding.photoView.setScale(DEFAULT_MIN_SCALE, animate)
    }
}
