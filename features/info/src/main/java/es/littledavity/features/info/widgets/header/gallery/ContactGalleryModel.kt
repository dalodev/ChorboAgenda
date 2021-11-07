/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.info.widgets.header.gallery

internal sealed class ContactGalleryModel {
    object DefaultImage : ContactGalleryModel()
    data class UrlImage(val url: String) : ContactGalleryModel()
}
