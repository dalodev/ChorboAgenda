/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.info.widgets.main.header.gallery

internal sealed class ContactGalleryModel {
    object DefaultImage : ContactGalleryModel()
    data class UrlImage(val url: String) : ContactGalleryModel()
}
