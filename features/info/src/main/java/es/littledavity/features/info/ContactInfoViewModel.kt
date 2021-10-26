/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.info

import android.Manifest
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.lifecycle.HiltViewModel
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.base.events.GeneralCommand
import es.littledavity.core.factories.ImageViewerContactUrlFactory
import es.littledavity.core.mapper.ErrorMapper
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.core.providers.StringProvider
import es.littledavity.core.service.PermissionService
import es.littledavity.core.utils.Logger
import es.littledavity.core.utils.combine
import es.littledavity.core.utils.onError
import es.littledavity.core.utils.resultOrError
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.entities.Image
import es.littledavity.domain.contacts.entities.Info
import es.littledavity.domain.contacts.usecases.GetContactUseCase
import es.littledavity.domain.contacts.usecases.ObserveContactLikeStateUseCase
import es.littledavity.domain.contacts.usecases.SaveContactUseCase
import es.littledavity.domain.contacts.usecases.ToggleContactLikeStateUseCase
import es.littledavity.features.info.mapping.ContactInfoUiStateFactory
import es.littledavity.features.info.widgets.ContactInfoUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PARAM_CONTACT_ID = "contact_id"
private const val IMAGE_TYPE = "image/*"

@HiltViewModel
internal class ContactInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCases: ContactInfoUseCases,
    private val uiStateFactory: ContactInfoUiStateFactory,
    private val contactUrlFactory: ImageViewerContactUrlFactory,
    private val dispatcherProvider: DispatcherProvider,
    private val stringProvider: StringProvider,
    private val errorMapper: ErrorMapper,
    private val logger: Logger,
    private val permissionService: PermissionService,
) : BaseViewModel() {

    private var isObservingContactData = false
    private var contactObservingJob: Job? = null

    private val contactId = checkNotNull(savedStateHandle.get<Int>(PARAM_CONTACT_ID))

    var currentContact: Contact? = null

    private var isContactLiked = false

    private val _uiState = MutableStateFlow<ContactInfoUiState>(ContactInfoUiState.Empty)

    val uiState: StateFlow<ContactInfoUiState>
        get() = _uiState

    fun loadData(resultEmissionDelay: Long) {
        observeContactData(resultEmissionDelay)
    }

    private fun observeContactData(resultEmissionDelay: Long) {
        if (isObservingContactData) return
        contactObservingJob = viewModelScope.launch {
            observeContactDataInternal(resultEmissionDelay)
        }
    }

    private suspend fun observeContactDataInternal(resultEmissionDelay: Long) {
        getContact()
            .flatMapConcat { contact ->
                currentContact = contact
                combine(
                    flowOf(contact),
                    observeContactLikeState(contact)
                )
            }
            .map { (contact, isContactLiked) ->
                this.isContactLiked = isContactLiked
                uiStateFactory.createWithResultState(
                    contact,
                    isContactLiked
                )
            }
            .flowOn(dispatcherProvider.computation)
            .onError {
                logger.error(logTag, "Failed to load contact info data.", it)
                dispatchCommand(GeneralCommand.ShowLongToast(errorMapper.mapToMessage(it)))
                emit(uiStateFactory.createWithEmptyState())
            }
            .onStart {
                isObservingContactData = true
                emit(uiStateFactory.createWithLoadingState())
                delay(resultEmissionDelay)
            }
            .onCompletion { isObservingContactData = false }
            .collect { _uiState.value = it }
    }

    private suspend fun getContact(): Flow<Contact> = useCases.getContactUseCase.execute(
        GetContactUseCase.Params(contactId)
    ).resultOrError()

    private suspend fun observeContactLikeState(contact: Contact) =
        useCases.observeContactLikeStateUseCase
            .execute(ObserveContactLikeStateUseCase.Params(contact.id))

    fun onBackButtonClicked() = onBackPressed()

    fun onGalleryClicked(position: Int) {
        navigateToImageViewer(
            title = stringProvider.getString(R.string.gallery),
            initialPosition = position,
            contactImageUrlsProvider = {
                currentContact?.let(contactUrlFactory::createGalleryImageUrls)
                    ?: contactUrlFactory.createGalleryImageUrls(it)
            },
            false
        )
    }

    private fun navigateToImageViewer(
        title: String,
        initialPosition: Int = 0,
        contactImageUrlsProvider: (Contact) -> List<String>,
        profileView: Boolean
    ) {
        viewModelScope.launch {
            val contact = getContact()
                .onError {
                    logger.error(logTag, "Failed to get the contact.", it)
                    dispatchCommand(GeneralCommand.ShowLongToast(errorMapper.mapToMessage(it)))
                }.firstOrNull() ?: return@launch

            val images = contactImageUrlsProvider(contact)
                .takeIf(List<String>::isNotEmpty) ?: return@launch
            route(
                ContactInfoRoute.ImageViewer(
                    title,
                    initialPosition,
                    images,
                    contactId,
                    profileView
                )
            )
            contactObservingJob?.cancelAndJoin()
        }
    }

    fun onImageClicked() {
        navigateToImageViewer(
            title = stringProvider.getString(R.string.cover),
            contactImageUrlsProvider = {
                currentContact?.let(contactUrlFactory::createCoverImageUrl)
                    ?.let(::listOf) ?: emptyList()
            },
            profileView = true
        )
    }

    fun onLikeButtonClicked() {
        viewModelScope.launch {
            useCases.toggleContactLikeStateUseCase
                .execute(ToggleContactLikeStateUseCase.Params(contactId))
        }
    }

    fun updatePhoto(uri: Uri?) {
        if (uri == null) return
        currentContact?.image = Image(id = uri.toString(), created = false)
        currentContact?.let {
            _uiState.value = uiStateFactory.createWithResultState(
                it,
                isContactLiked
            )
        }
        viewModelScope.launch {
            saveCurrentContact(false)
        }
    }

    fun addGalleryImage(uri: Uri?) {
        if (uri == null) return
        currentContact?.gallery?.add(Image(id = uri.toString(), created = false))
        currentContact?.let {
            _uiState.value = uiStateFactory.createWithResultState(
                it,
                isContactLiked
            )
        }
        viewModelScope.launch {
            saveCurrentContact(true)
        }
    }

    fun requestStoragePermission(resultLauncher: ActivityResultLauncher<String>) {
        permissionService.requestPermission(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            object : PermissionListener {
                override fun onPermissionGranted(report: PermissionGrantedResponse?) {
                    resultLauncher.launch(IMAGE_TYPE)
                }

                override fun onPermissionDenied(report: PermissionDeniedResponse?) {
                    _uiState.value = uiStateFactory.createWithPermissionError {
                        route(ContactInfoRoute.SettingsApp)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    _uiState.value = uiStateFactory.createWithPermissionError {
                        route(ContactInfoRoute.SettingsApp)
                    }
                }
            }
        )
    }

    fun updateContactData(
        name: CharSequence,
        phone: CharSequence?,
        instagram: CharSequence?,
        score: CharSequence?,
        location: CharSequence?,
        age: CharSequence?,
        infoList: MutableList<Info>?,
        new: Boolean = false
    ) {
        currentContact?.name = name.toString()
        currentContact?.phone = phone.toString()
        currentContact?.instagram = instagram.toString()
        currentContact?.rating = score.toString()
        currentContact?.country = location.toString()
        currentContact?.age = age.toString()
        currentContact?.info = infoList?.apply {
            if (new) {
                add(Info("", ""))
            }
        } ?: if (new) mutableListOf(Info("", "")) else mutableListOf()
        currentContact?.let {
            _uiState.value = uiStateFactory.createWithResultState(it, isContactLiked)
        }
    }

    fun onBackPressed() {
        viewModelScope.launch {
            saveCurrentContact { state ->
                if (state is ContactInfoUiState.Result) {
                    route(ContactInfoRoute.Back)
                }
            }
        }
    }

    fun saveData() {
        viewModelScope.launch {
            saveCurrentContact()
        }
    }

    private suspend fun saveCurrentContact(
        loading: Boolean = true,
        onCompletion: ((ContactInfoUiState) -> Unit)? = null
    ) {
        currentContact?.let {
            useCases.saveContactUseCase.execute(SaveContactUseCase.Params(it))
                .map(::mapToUiState)
                .flowOn(dispatcherProvider.computation)
                .onStart {
                    if (loading) emit(uiStateFactory.createWithLoadingState())
                }.collect { state ->
                    _uiState.value = state
                    onCompletion?.invoke(state)
                }
        }
    }

    private fun mapToUiState(contact: Contact?) = if (contact == null) {
        uiStateFactory.createWithEmptyState()
    } else {
        uiStateFactory.createWithResultState(contact, isContactLiked)
    }
}
