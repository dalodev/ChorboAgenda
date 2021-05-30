package es.littledavity.features.info.widgets.main.header

internal sealed class ContactHeaderImageModel {
    object DefaultImage : ContactHeaderImageModel()
    data class UrlImage(val url: String) : ContactHeaderImageModel()
}