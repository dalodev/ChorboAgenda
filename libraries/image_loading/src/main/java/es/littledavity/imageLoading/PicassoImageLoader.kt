package es.littledavity.imageLoading

import android.widget.ImageView
import androidx.core.net.toUri
import com.paulrybitskyi.hiltbinder.BindType
import com.squareup.picasso.Picasso
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
internal class PicassoImageLoader @Inject constructor(
    private val picasso: Picasso
) : ImageLoader {

    override fun loadImage(config: Config) {
        config.onStart?.invoke()

        val requestCreator = picasso.load(config.imageUrl.toUri())

        if (config.shouldCenterCrop) requestCreator.centerCrop()
        if (config.shouldCenterInside) requestCreator.centerInside()
        if (config.shouldFit) requestCreator.fit()
        if (config.hasTargetSize) requestCreator.resize(config.targetWidth, config.targetHeight)

        config.progressDrawable?.let(requestCreator::placeholder)
        config.errorDrawable?.let(requestCreator::error)

        if (config.hasTransformations) {
            config.transformations
                .map(::PicassoTransformation)
                .let { requestCreator.transform(it) }
        }

        if (config.hasAtLeastOneResultListener) {
            requestCreator.into(
                target = config.target,
                onSuccess = config.onSuccess,
                onFailure = config.onFailure
            )
        } else {
            requestCreator.into(config.target)
        }
    }

    override fun cancelRequests(target: ImageView) {
        picasso.cancelRequest(target)
    }
}