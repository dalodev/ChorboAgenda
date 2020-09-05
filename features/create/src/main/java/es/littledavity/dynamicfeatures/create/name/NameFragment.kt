/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.name

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.dynamicfeatures.create.R
import es.littledavity.dynamicfeatures.create.databinding.FragmentNameBinding
import es.littledavity.dynamicfeatures.create.name.di.DaggerNameComponent
import es.littledavity.dynamicfeatures.create.name.di.NameModule

class NameFragment : BaseFragment<FragmentNameBinding, NameViewModel>(
    layoutId = R.layout.fragment_name
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.event, ::onViewEvent)
    }

    override fun onInitDependencyInjection() {
        DaggerNameComponent
            .builder()
            .coreComponent(ChorboagendaApp.coreComponent(requireContext()))
            .nameModule(NameModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.toolbar.setNavigationOnClickListener { viewModel.back() }
    }

    override fun onClear() = Unit

    /**
     * Observer view event change on [NameViewModel].
     *
     * @param viewEvent Event on chorbos list.
     */
    private fun onViewEvent(viewEvent: NameViewEvent) {
        when (viewEvent) {
            is NameViewEvent.Next -> {
                val extras = FragmentNavigatorExtras(
                    viewBinding.continueButton to viewBinding.continueButton.transitionName,
                    viewBinding.name to viewBinding.name.transitionName
                )
                viewModel.navigate(
                    NameFragmentDirections.toImage(viewBinding.name.text.toString()),
                    extras
                )
            }
        }
    }
}
