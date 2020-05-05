package es.littledavity.dynamicfeatures.create.name

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.livedata.SingleLiveData
import javax.inject.Inject

/**
 * View model responsible for preparing and managing the data for [NameFragment].
 *
 * @see ViewModel
 */
class NameViewModel @Inject constructor() : BaseViewModel() {

    val event = SingleLiveData<NameViewEvent>()
    private val _state = MutableLiveData<NameViewState>()
    val state: LiveData<NameViewState>
        get() = _state

    val onChangeText: (String) -> Unit = {
        if (it.trim().isNotEmpty()) _state.postValue(NameViewState.Continue)
        else _state.postValue(NameViewState.EmptyName)
    }

    fun onContinue(){
        event.postValue(NameViewEvent.Next)
    }
}