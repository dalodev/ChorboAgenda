package es.littledavity.features.info.widgets.main.header.gallery

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.extensions.getDrawable
import es.littledavity.commons.ui.extensions.observeChanges
import es.littledavity.features.info.R
import es.littledavity.imageLoading.Config
import es.littledavity.imageLoading.ImageLoader
import javax.inject.Inject

@AndroidEntryPoint
internal class ContactGalleryImageView constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttrs) {

    private var defaultBackgroundDrawable = checkNotNull(getDrawable(R.drawable.gallery_image_default))

    var model by observeChanges<ContactGalleryModel?>(null) { _, newModel ->
        newModel?.let(::onModelChanged)
    }

    @Inject
    lateinit var imageLoader: ImageLoader

    init {
        scaleType = ScaleType.CENTER_CROP
    }

    private fun onModelChanged(newModel: ContactGalleryModel) {
        when (newModel) {
            is ContactGalleryModel.DefaultImage -> loadDefaultImage()
            is ContactGalleryModel.UrlImage -> loadUrlImage(newModel.url)
        }
    }

    private fun loadDefaultImage() {
        setImageDrawable(defaultBackgroundDrawable)
    }

    private fun loadUrlImage(url: String) {
        imageLoader.loadImage(
            Config.Builder()
                .fit()
                .centerCrop()
                .imageUrl(url)
                .target(this)
                .progressDrawable(defaultBackgroundDrawable)
                .errorDrawable(defaultBackgroundDrawable)
                .build()
        )
    }
}