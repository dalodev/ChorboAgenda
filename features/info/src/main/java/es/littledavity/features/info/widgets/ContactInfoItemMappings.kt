package es.littledavity.features.info.widgets

import es.littledavity.features.info.widgets.main.header.ContactHeaderImageModel
import es.littledavity.features.info.widgets.main.header.gallery.ContactGalleryModel

internal fun List<ContactHeaderImageModel>.mapToContactGalleryModels(): List<ContactGalleryModel> {
    return map { imageModel ->
        when (imageModel) {
            is ContactHeaderImageModel.DefaultImage -> ContactGalleryModel.DefaultImage
            is ContactHeaderImageModel.UrlImage -> ContactGalleryModel.UrlImage(imageModel.url)
        }
    }
}