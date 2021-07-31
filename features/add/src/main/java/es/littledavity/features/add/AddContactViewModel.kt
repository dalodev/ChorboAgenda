package es.littledavity.features.add

import android.Manifest
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.viewModelScope
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.lifecycle.HiltViewModel
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.base.events.GeneralCommand
import es.littledavity.core.mapper.ErrorMapper
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.core.service.PermissionService
import es.littledavity.core.utils.Logger
import es.littledavity.core.utils.onError
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.entities.CreationDate
import es.littledavity.domain.contacts.entities.CreationDateCategory
import es.littledavity.domain.contacts.entities.Image
import es.littledavity.domain.contacts.usecases.SaveContactUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val IMAGE_TYPE = "image/*"

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val saveContactUseCase: SaveContactUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val uiStateFactory: AddContactUiStateFactory,
    private val logger: Logger,
    private val errorMapper: ErrorMapper,
    private val permissionService: PermissionService
) : BaseViewModel() {

    private var contactImage: Image? = null

    private val currentContact = createContact()

    private var useCaseParams = SaveContactUseCase.Params(currentContact)

    private val _uiState = MutableStateFlow<AddContactUiState>(AddContactUiState.New)

    val uiState: StateFlow<AddContactUiState>
        get() = _uiState

    fun onToolbarBackButtonClicked() {
        route(AddContactRoute.Back)
    }

    fun onToolbarRightButtonClicked(name: String, phone: String) {
        val canDone = name.isNotBlank()
        currentContact.name = name
        currentContact.phone = phone
        if (canDone) {
            saveContact()
        } else {
            _uiState.value = createErrorAddContactUiState()
        }
    }

    fun updatePhoto(uri: Uri?) {
        contactImage = Image(id = uri.toString())
        currentContact.image = contactImage
        _uiState.value = createResultAddContactUiState()
    }

    private fun saveContact() {
        viewModelScope.launch {
            currentContact.creationDate = CreationDate(
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().get(Calendar.YEAR),
                CreationDateCategory.YYYY_MMMM_DD
            )
            saveContactUseCase.execute(useCaseParams)
                .map(::mapToUiState)
                .flowOn(dispatcherProvider.computation)
                .onError {
                    logger.error(logTag, "Failed to create contact.", it)
                    dispatchCommand(GeneralCommand.ShowLongToast(errorMapper.mapToMessage(it)))
                    emit(createErrorAddContactUiState())
                }
                .onStart {
                    emit(uiStateFactory.createWithLoadingState())
                }.collect {
                    _uiState.value = it
                    route(AddContactRoute.List)
                }
        }
    }

    private fun mapToUiState(contact: Contact?) = if (contact == null) {
        createErrorAddContactUiState()
    } else {
        uiStateFactory.createWithResultState(contact)
    }

    private fun createErrorAddContactUiState() = uiStateFactory.createWithErrorState()

    private fun createResultAddContactUiState() =
        uiStateFactory.createWithResultState(currentContact)

    fun onRetryButtonClicked() {
        _uiState.value = uiStateFactory.createWithNewState()
    }

    fun onPhotoClicked(resultLauncher: ActivityResultLauncher<String>) {
        permissionService.requestPermission(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            object : PermissionListener {
                override fun onPermissionGranted(report: PermissionGrantedResponse?) {
                    resultLauncher.launch(IMAGE_TYPE)
                }

                override fun onPermissionDenied(report: PermissionDeniedResponse?) {
                    _uiState.value = uiStateFactory.createWithPermissionError {
                        route(AddContactRoute.SettingsApp)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    _uiState.value = uiStateFactory.createWithPermissionError {
                        route(AddContactRoute.SettingsApp)
                    }
                }
            })
    }

    private fun createContact() = Contact(
        id = 0,
        image = contactImage,
        name = "",
        phone = "",
        gallery = emptyList(),
        screenshots = emptyList(),
        creationDate = null,
        age = "",
        rating = "",
        country = "",
        instagram = ""
    )
}