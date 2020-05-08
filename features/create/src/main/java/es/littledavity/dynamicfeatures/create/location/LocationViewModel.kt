package es.littledavity.dynamicfeatures.create.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.livedata.SingleLiveData
import javax.inject.Inject

/**
 * View model responsible for preparing and managing the data for [LocationFragment].
 *
 * @see BaseViewModel
 */
class LocationViewModel @Inject constructor() : BaseViewModel() {

    val event = SingleLiveData<LocationViewEvent>()
    private val _state = MutableLiveData<LocationViewState>()
    val state: LiveData<LocationViewState>
        get() = _state

    fun onContinue() = event.postValue(LocationViewEvent.Next)
}