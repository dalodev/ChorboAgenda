package es.littledavity.features.edit

import es.littledavity.commons.ui.base.navigation.Navigator

interface EditContactNavigator : Navigator {
    fun goBack()
    fun goList()
}