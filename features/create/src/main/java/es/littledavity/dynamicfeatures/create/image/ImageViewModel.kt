package es.littledavity.dynamicfeatures.create.image

import android.Manifest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.livedata.SingleLiveData
import es.littledavity.core.service.PermissionService
import javax.inject.Inject


/**
 * View model responsible for preparing and managing the data for [ImageFragment].
 *
 * @see BaseViewModel
 */
class ImageViewModel @Inject constructor(
    private val permissionService: PermissionService
) : BaseViewModel() {
    val event = SingleLiveData<ImageViewEvent>()
    private val _state = MutableLiveData<ImageViewState>()
    val state: LiveData<ImageViewState>
        get() = _state

    fun addImage() {
        permissionService.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, object: PermissionListener{
            override fun onPermissionGranted(reponse: PermissionGrantedResponse?) {
                _state.postValue(ImageViewState.OpenGallery)
            }

            override fun onPermissionRationaleShouldBeShown(
                request: PermissionRequest?,
                token: PermissionToken?
            ) {
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
            }

        })
    }

    fun onContinue() = event.postValue(ImageViewEvent.Next)

    fun onViewStateChange(vieState: ImageViewState) = _state.postValue(vieState)
}