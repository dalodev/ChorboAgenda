package es.littledavity.features.info

import es.littledavity.commons.ui.base.navigation.Navigator

interface ContactInfoNavigator : Navigator {
    fun goToImageViewer(title: String?, initialPosition: Int, imageUrls: List<String>)
    fun goToInfo(contactId: Int)
    fun goBack()
}