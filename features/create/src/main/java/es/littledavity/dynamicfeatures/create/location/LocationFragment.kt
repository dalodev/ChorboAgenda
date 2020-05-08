package es.littledavity.dynamicfeatures.create.location

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.dynamicfeatures.create.R
import es.littledavity.dynamicfeatures.create.databinding.FragmentLocationBinding
import es.littledavity.dynamicfeatures.create.location.di.DaggerLocationComponent
import es.littledavity.dynamicfeatures.create.location.di.LocationModule
import es.littledavity.dynamicfeatures.create.name.NameViewModel

/**
 * Chorbo name view containing bottom navigation bar with different chorbos tabs.
 *
 * @see BaseFragment
 */
class LocationFragment : BaseFragment<FragmentLocationBinding, LocationViewModel>(
    layoutId = R.layout.fragment_location
) {

    private val args: LocationFragmentArgs by navArgs()

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param view The view returned by onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @see BaseFragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.event, ::onViewEvent)
    }

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerLocationComponent
            .builder()
            .coreComponent(ChorboagendaApp.coreComponent(requireContext()))
            .locationModule(LocationModule(this))
            .build()
            .inject(this)
    }

    /**
     * Initialize view data binding variables.
     */
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    override fun onClear() {}

    /**
     * Observer view event change on [NameViewModel].
     *
     * @param viewEvent Event on chorbos list.
     */
    private fun onViewEvent(viewEvent: LocationViewEvent) {
        when (viewEvent) {
            is LocationViewEvent.Next -> {
                val extras = FragmentNavigatorExtras(

                )
            }
        }
    }
}