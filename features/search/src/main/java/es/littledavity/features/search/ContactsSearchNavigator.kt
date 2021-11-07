/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.search

import es.littledavity.commons.ui.base.navigation.Navigator

interface ContactsSearchNavigator : Navigator {

    fun goToInfo(contactId: Int)

    fun goBack()
}
