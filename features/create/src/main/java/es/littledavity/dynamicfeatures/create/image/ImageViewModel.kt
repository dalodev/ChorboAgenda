package es.littledavity.dynamicfeatures.create.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.livedata.SingleLiveData
import javax.inject.Inject

/**
 * View model responsible for preparing and managing the data for [ImageFragment].
 *
 * @see BaseViewModel
 */
class ImageViewModel @Inject constructor() : BaseViewModel() {

    val event = SingleLiveData<ImageViewEvent>()
    private val _state = MutableLiveData<ImageViewState>()
    val state: LiveData<ImageViewState>
        get() = _state

    fun onContinue() = event.postValue(ImageViewEvent.Next)
}