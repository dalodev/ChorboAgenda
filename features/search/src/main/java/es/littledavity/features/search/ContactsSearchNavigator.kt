/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.search

import es.littledavity.commons.ui.base.navigation.Navigator

interface ContactsSearchNavigator : Navigator {

    fun goToInfo(contactId: Int)

    fun goBack()

}
