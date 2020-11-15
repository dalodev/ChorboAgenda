/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.location

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import com.debut.countrycodepicker.CountryPicker
import com.debut.countrycodepicker.data.Country
import com.debut.countrycodepicker.listeners.CountryCallBack
import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.databinding.FragmentToolbarBinding
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.dynamicfeatures.create.R
import es.littledavity.dynamicfeatures.create.databinding.FragmentLocationBinding
import es.littledavity.dynamicfeatures.create.location.di.DaggerLocationComponent
import es.littledavity.dynamicfeatures.create.location.di.LocationModule
import es.littledavity.dynamicfeatures.create.name.NameViewModel

class LocationFragment : BaseFragment<FragmentLocationBinding, LocationViewModel>(
    layoutId = R.layout.fragment_location
) {

    private val args: LocationFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.event, ::onViewEvent)
    }

    override fun onInitDependencyInjection() {
        DaggerLocationComponent
            .builder()
            .coreComponent(ChorboagendaApp.coreComponent(requireContext()))
            .locationModule(LocationModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        enableBack = true
        viewBinding?.viewModel = viewModel
    }

    override fun onClearView() = Unit

    /**
     * Observer view event change on [NameViewModel].
     *
     * @param viewEvent Event on chorbos list.
     */
    private fun onViewEvent(viewEvent: LocationViewEvent) {
        when (viewEvent) {
            is LocationViewEvent.CountryPicker -> {
                CountryPicker.show(
                    requireCompatActivity().supportFragmentManager,
                    object : CountryCallBack {
                        override fun onCountrySelected(country: Country) {
                            viewModel.loadCountry(country)
                        }
                    }
                )
            }
            is LocationViewEvent.Next -> viewModel.navigate(
                LocationFragmentDirections.toContact(
                    args.name,
                    viewModel.country.countryCode,
                    viewModel.country.name,
                    viewModel.getFlag()
                )
            )
        }
    }
}
