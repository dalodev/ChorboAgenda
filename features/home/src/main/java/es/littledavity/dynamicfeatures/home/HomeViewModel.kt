package es.littledavity.dynamicfeatures.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

/**
 * View model responsible for preparing and managing the data for [HomeFragment].
 *
 * @see ViewModel
 */
class HomeViewModel : ViewModel() {

    private val _state = MutableLiveData<HomeViewState>()
    val state: LiveData<HomeViewState>
        get() = _state

    /**
     * Navigation controller add destination changed listener.
     *
     * @param navController Navigation manager.
     */
    fun navigationControllerChanged(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            _state.postValue(HomeViewState.FullScreen)
        }
    }
}