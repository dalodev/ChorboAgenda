/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.contacts

import es.littledavity.commons.ui.base.navigation.Navigator

interface ContactsNavigator : Navigator {
    fun goToInfo(contactId: Int)
}
