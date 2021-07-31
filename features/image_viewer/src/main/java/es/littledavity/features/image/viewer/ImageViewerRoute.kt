/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.image.viewer

import es.littledavity.commons.ui.base.events.Command
import es.littledavity.commons.ui.base.events.Route

internal sealed class ImageViewerCommand : Command {

    data class ShareText(val text: String): ImageViewerCommand()

    object ResetSystemWindows : ImageViewerCommand()

}

internal sealed class ImageViewerRoute : Route {

    object Back : ImageViewerRoute()

}
