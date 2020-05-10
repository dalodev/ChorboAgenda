package es.littledavity.dynamicfeatures.create.location

import android.R.attr.bitmap
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.debut.countrycodepicker.data.Country
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.livedata.SingleLiveData
import java.io.ByteArrayOutputStream
import javax.inject.Inject


/**
 * View model responsible for preparing and managing the data for [LocationFragment].
 *
 * @see BaseViewModel
 */
class LocationViewModel @Inject constructor(
    private val context: Context
) : BaseViewModel() {

    val event = SingleLiveData<LocationViewEvent>()
    private val _state = MutableLiveData<LocationViewState>()
    val state: LiveData<LocationViewState>
        get() = _state

    val countryName = MutableLiveData<String>()
    val countryFlag = MutableLiveData<Drawable>()

    lateinit var country: Country

    fun onSelectCountry() = event.postValue(LocationViewEvent.CountryPicker)

    fun onContinue() = event.postValue(LocationViewEvent.Next)

    fun loadCountry(country: Country) {
        this.country = country
        _state.postValue(LocationViewState.Continue)
        countryName.postValue("+${country.countryCode}  ${country.name}")
        countryFlag.postValue(BitmapDrawable(context.resources, country.flag))
    }

    fun getFlag(): String {
        val baos = ByteArrayOutputStream()
        country.flag.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
}