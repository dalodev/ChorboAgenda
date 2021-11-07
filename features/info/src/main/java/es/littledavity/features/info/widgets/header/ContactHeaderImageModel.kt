/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.info.widgets.header

internal sealed class ContactHeaderImageModel {
    object DefaultImage : ContactHeaderImageModel()
    data class UrlImage(val url: String) : ContactHeaderImageModel()
}
