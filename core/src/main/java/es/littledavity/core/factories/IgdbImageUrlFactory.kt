/*
 * Copyright 2021 dalodev
 */
package es.littledavity.core.factories

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.domain.contacts.entities.Image
import javax.inject.Inject

enum class IgdbImageSize(internal val rawSize: String) {

    SMALL_COVER("cover_small"),
    BIG_COVER("cover_big"),

    MEDIUM_SCREENSHOT("screenshot_med"),
    BIG_SCREENSHOT("screenshot_big"),
    HUGE_SCREENSHOT("screenshot_huge"),

    MEDIUM_LOGO("logo_med"),

    THUMBNAIL("thumb"),
    MICRO("micro"),

    HD("720p"),
    FULL_HD("1080p")
}

enum class IgdbImageExtension(internal val rawExtension: String) {

    JPG("jpg"),
    PNG("png")
}

interface IgdbImageUrlFactory {

    data class Config(
        val size: IgdbImageSize,
        val extension: IgdbImageExtension = IgdbImageExtension.JPG,
        val withRetinaSize: Boolean = false
    )

    fun createUrl(image: Image, config: Config): String?
}

fun IgdbImageUrlFactory.createUrls(
    images: List<Image>,
    config: IgdbImageUrlFactory.Config
): List<String> {
    if (images.isEmpty()) return emptyList()

    return images.mapNotNull { image ->
        createUrl(image, config)
    }
}

@BindType
internal class IgdbImageUrlFactoryImpl @Inject constructor() : IgdbImageUrlFactory {

    override fun createUrl(image: Image, config: IgdbImageUrlFactory.Config): String? {
        if (image.id?.isBlank() == true) return null
        return image.id
    }

    private fun constructType(config: IgdbImageUrlFactory.Config) = buildString {
        append(config.size.rawSize)
    }
}
