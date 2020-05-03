package es.littledavity.dynamicfeatures.create

import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.dynamicfeatures.create.databinding.FragmentCreateBinding
import es.littledavity.dynamicfeatures.create.di.CreateModule
import es.littledavity.dynamicfeatures.create.di.DaggerCreateComponent

/**
 * Chorbo list principal view containing bottom navigation bar with different chorbos tabs.
 *
 * @see BaseFragment
 */
class CreateFragment : BaseFragment<FragmentCreateBinding, CreateViewModel>(
    layoutId = R.layout.fragment_create
) {

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerCreateComponent
            .builder()
            .coreComponent(ChorboagendaApp.coreComponent(requireContext()))
            .createModule(CreateModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    override fun onClear() {
    }

}