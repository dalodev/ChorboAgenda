/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.image

import android.Manifest
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
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

    var imageUri = MutableLiveData<Uri>()

    fun addImage() {
        permissionService.requestPermissions(
            listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        _state.postValue(ImageViewState.OpenGallery)
                    }
                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied) {
                        // permission is denied permenantly, navigate user to app settings
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }
        )
    }

    fun onContinue() = event.postValue(ImageViewEvent.Next)

    fun loadImage(uri: Uri) {
        imageUri.postValue(uri)
        _state.postValue(ImageViewState.Continue())
    }
}
