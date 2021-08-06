package es.littledavity.features.edit

import es.littledavity.commons.ui.base.events.Route

internal sealed class EditContactRoute : Route {
    object Back : EditContactRoute()
    object List : EditContactRoute()
}