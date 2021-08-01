/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.search

import es.littledavity.commons.ui.base.events.Command
import es.littledavity.commons.ui.base.events.Route

internal sealed class ContactsSearchCommand : Command {
    object ClearItems : ContactsSearchCommand()
}

internal sealed class ContactsSearchRoute : Route {
    data class Info(val contactId: Int) : ContactsSearchRoute()
    object Back : ContactsSearchRoute()
}
