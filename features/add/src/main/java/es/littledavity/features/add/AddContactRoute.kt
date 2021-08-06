/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.add

import es.littledavity.commons.ui.base.events.Route

internal sealed class AddContactRoute : Route {
    object Back : AddContactRoute()
    object List : AddContactRoute()
    data class GoToInfo(val contactId: Int) : AddContactRoute()
    object SettingsApp : AddContactRoute()
}
