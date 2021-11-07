/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.likes

import es.littledavity.commons.ui.base.navigation.Navigator

interface LikedContactsNavigator : Navigator {
    fun goToInfo(contactId: Int)
}
