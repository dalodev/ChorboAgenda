/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.add

import es.littledavity.commons.ui.base.navigation.Navigator

interface AddContactNavigator : Navigator {
    fun goBack()
    fun goList()
    fun goEdit(contactId: Int)
    fun goSettingsApp()
}
