package es.littledavity.commons.ui.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import es.littledavity.commons.ui.livedata.SingleLiveData
import es.littledavity.commons.ui.navigation.NavigationCommand

abstract class BaseViewModel : ViewModel() {

    val navigationCommands = SingleLiveData<NavigationCommand>()

    fun navigate(directions: NavDirections) {
        navigationCommands.postValue(NavigationCommand.To(directions))
    }
}