/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.info

import es.littledavity.commons.ui.base.events.Command
import es.littledavity.commons.ui.base.events.Route

internal sealed class ContactInfoCommand : Command {
    data class OpenUrl(val url: String) : ContactInfoCommand()
}

internal sealed class ContactInfoRoute : Route {
    data class Info(val contactId: Int) : ContactInfoRoute()

    data class ImageViewer(
        val title: String?,
        val initialPosition: Int,
        val imageUrls: List<String>,
        val contactId: Int,
        val profileView: Boolean
    ) : ContactInfoRoute()

    object Back : ContactInfoRoute()
    object SettingsApp : ContactInfoRoute()
    data class InstagramProfile(val profileName: String) : ContactInfoRoute()
}
