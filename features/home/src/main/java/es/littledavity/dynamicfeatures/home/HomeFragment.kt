package es.littledavity.dynamicfeatures.home

import android.os.Bundle
import android.view.View
import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.dynamicfeatures.home.databinding.FragmentHomeBinding
import es.littledavity.dynamicfeatures.home.di.DaggerHomeComponent
import es.littledavity.dynamicfeatures.home.di.HomeModule

/**
 * Home principal view containing bottom navigation bar with different chorbos.
 *
 * @see BaseFragment
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    layoutId = R.layout.fragment_home
) {

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerHomeComponent
            .builder()
            .coreComponent(ChorboagendaApp.coreComponent(requireContext()))
            .homeModule(HomeModule(this))
            .build()
            .inject(this)
    }

    /**
     * Initialize view data binding variables.
     */
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    override fun onClear() {
    }

    // ============================================================================================
    //  Private setups methods
    // ============================================================================================



}