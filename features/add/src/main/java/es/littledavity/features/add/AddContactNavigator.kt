package es.littledavity.features.add

import es.littledavity.commons.ui.base.navigation.Navigator

interface AddContactNavigator : Navigator {
    fun goBack()
    fun goList()
    fun goEdit()
    fun goSettingsApp()
}