/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.contact

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.livedata.SingleLiveData
import es.littledavity.core.database.chorbo.Chorbo
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.core.service.ImageGalleryService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model responsible for preparing and managing the data for [ContactFragment].
 *
 * @see BaseViewModel
 */
class ContactViewModel @Inject constructor(
    private val chorboRepository: ChorboRepository,
    private val imageGalleryService: ImageGalleryService
) : BaseViewModel() {

    val event = SingleLiveData<ContactViewEvent>()
    private val _state = MutableLiveData<ContactViewState>()
    val state: LiveData<ContactViewState>
        get() = _state

    lateinit var chorbo: Chorbo

    val onInstagramTextChange: (String) -> Unit = { chorbo.instagram = it }

    val onWhatsappTextChange: (String) -> Unit = { chorbo.whatsapp = it }

    fun onContinue() {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, exception ->
                _state.postValue(ContactViewState.Error(message = exception.message))
            }
        ) {
            val imageBitmap = imageGalleryService.getBitmap(Uri.parse(chorbo.image))
            chorbo.image = imageGalleryService.saveMediaImage(imageBitmap, chorbo)
            val flagBitmap = imageGalleryService.getBitmap(chorbo.flag)
            chorbo.flag = imageGalleryService.saveMediaImage(flagBitmap, chorbo, chorbo.countryName)
            chorboRepository.insertChorbo(chorbo)
            event.postValue(ContactViewEvent.Next)
        }
    }

    fun setData(args: ContactFragmentArgs) {

        chorbo = Chorbo(
            name = args.name,
            image = args.image,
            countryCode = args.countryCode,
            countryName = args.countryName,
            flag = args.flag,
            whatsapp = "",
            instagram = ""
        )
    }
}
