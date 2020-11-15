/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.detail

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.core.service.PermissionService
import es.littledavity.dynamicfeatures.chorboList.detail.model.ChorboDetailItem
import es.littledavity.dynamicfeatures.chorboList.detail.model.ChorboDetailMapper
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model responsible for preparing and managing the data for [ChorboDetailFragment].
 *
 * @see BaseViewModel
 */
class ChorboDetailViewModel @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val chorboRepository: ChorboRepository,
    @VisibleForTesting(otherwise = PRIVATE)
    val mapper: ChorboDetailMapper,
    private val permissionService: PermissionService
) : BaseViewModel() {

    var chorboDetail = MutableLiveData<ChorboDetailItem>()
    private val _viewState = MutableLiveData<ChorboDetailViewState>()
    val viewState: LiveData<ChorboDetailViewState>
        get() = _viewState

    private var saveChanges = false

    fun loadData(id: Long) {
        viewModelScope.launch {
            val chorbo = chorboRepository.getChorbo(id)
            chorbo?.let {
                val chorboDetailItem = mapper.map(it)
                chorboDetail.postValue(chorboDetailItem)
                _viewState.postValue(ChorboDetailViewState.Loaded(chorboDetailItem))
            }
        }
    }

    @SuppressLint("InlinedApi")
    fun addImage() {
        permissionService.requestPermissions(
            listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_MEDIA_LOCATION
            ).takeIf { Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q } ?: listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        _viewState.postValue(ChorboDetailViewState.OpenGallery)
                        saveChanges = true
                    }
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

    fun loadImage(uri: Uri) = chorboDetail.postValue(
        chorboDetail.value.apply {
            this?.image = uri
        }
    )
}
