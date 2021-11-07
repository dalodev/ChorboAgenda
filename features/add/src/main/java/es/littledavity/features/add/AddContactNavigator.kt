/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.add

import es.littledavity.commons.ui.base.navigation.Navigator

interface AddContactNavigator : Navigator {
    fun goBack()
    fun goToInfo(contactId: Int)
    fun goSettingsApp()
}
