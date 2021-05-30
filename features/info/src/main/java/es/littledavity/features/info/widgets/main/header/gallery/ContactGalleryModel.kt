package es.littledavity.features.info.widgets.main.header.gallery

internal sealed class ContactGalleryModel {
    object DefaultImage : ContactGalleryModel()
    data class UrlImage(val url: String) : ContactGalleryModel()
}