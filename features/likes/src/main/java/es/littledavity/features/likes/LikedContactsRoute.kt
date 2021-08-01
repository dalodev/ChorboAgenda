/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.likes

import es.littledavity.commons.ui.base.events.Route

internal sealed class LikedContactsRoute : Route {
    data class Info(val contactId: Int) : LikedContactsRoute()
}
