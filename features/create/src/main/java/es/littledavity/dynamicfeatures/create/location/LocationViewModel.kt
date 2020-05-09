package es.littledavity.dynamicfeatures.create.location

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.debut.countrycodepicker.data.Country
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.livedata.SingleLiveData
import javax.inject.Inject

/**
 * View model responsible for preparing and managing the data for [LocationFragment].
 *
 * @see BaseViewModel
 */
class LocationViewModel @Inject constructor(
    val context: Context
) : BaseViewModel() {

    val event = SingleLiveData<LocationViewEvent>()
    private val _state = MutableLiveData<LocationViewState>()
    val state: LiveData<LocationViewState>
        get() = _state

    val countryName = MutableLiveData<String>()
    val countryFlag = MutableLiveData<Drawable>()

    fun onSelectCountry() = _state.postValue(LocationViewState.CountryPicker)

    fun onContinue() = event.postValue(LocationViewEvent.Next)

    fun loadCountry(country: Country) {
        _state.postValue(LocationViewState.Continue)
        countryName.postValue("+ ${country.countryCode} ${country.name}")
        countryFlag.postValue(BitmapDrawable(context.resources, country.flag))
    }
}