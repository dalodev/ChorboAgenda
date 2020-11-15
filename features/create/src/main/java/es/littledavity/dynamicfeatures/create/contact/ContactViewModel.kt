/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.contact

import android.telephony.PhoneNumberUtils
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
import java.util.Locale
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
    val phone = MutableLiveData<String>()
    val instagram = MutableLiveData<String>()

    lateinit var chorbo: Chorbo

    val onInstagramTextChange: (String) -> Unit = { newText ->
        chorbo.instagram = newText
        instagram.postValue(
            "@".plus(newText).takeIf {
                !newText.contains("@")
            } ?: newText)
    }

    val onPhoneTextChange: (String) -> Unit = {
        chorbo.whatsapp = it
        phone.postValue(PhoneNumberUtils.formatNumber(it, getCountryCode(chorbo.countryName)) ?: it)
    }

    fun onContinue() {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, exception ->
                _state.postValue(ContactViewState.Error(message = exception.message))
            }
        ) {
            val flagBitmap = imageGalleryService.getBitmap(chorbo.flag)
            chorbo.flag = imageGalleryService.saveMediaImage(flagBitmap, chorbo, chorbo.countryName)
            chorboRepository.insertChorbo(chorbo)
            event.postValue(ContactViewEvent.Next)
        }
    }

    fun setData(args: ContactFragmentArgs) {

        chorbo = Chorbo(
            name = args.name,
            countryCode = args.countryCode,
            countryName = args.countryName,
            flag = args.flag,
            whatsapp = "",
            instagram = ""
        )
    }

    private fun getCountryCode(countryName: String) =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }
}
