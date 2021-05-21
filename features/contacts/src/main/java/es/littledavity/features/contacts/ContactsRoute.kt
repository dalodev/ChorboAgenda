package es.littledavity.features.contacts

import es.littledavity.commons.ui.base.events.Route

internal sealed class ContactsRoute: Route {
    data class Info(val contactId: Int): ContactsRoute()
}