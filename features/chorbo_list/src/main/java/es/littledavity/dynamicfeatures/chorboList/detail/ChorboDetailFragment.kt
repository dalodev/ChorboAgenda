/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.detail

import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.dynamicfeatures.chorboList.detail.di.ChorboDetailModule
import es.littledavity.dynamicfeatures.chorboList.detail.di.DaggerChorboDetailComponent
import es.littledavity.dynamicfeatures.chorboList.list.di.ChorboListModule
import es.littledavity.dynamicfeatures.chorboList.list.di.DaggerChorboListComponent
import es.littledavity.dynamicfeatures.chorbo_list.R
import es.littledavity.dynamicfeatures.chorbo_list.databinding.FragmentChorboDetailBinding

/**
 * View detail for selected chorbo, displaying extra info.
 *
 * @see BaseFragment
 */
class ChorboDetailFragment : BaseFragment<FragmentChorboDetailBinding, ChorboDetailViewModel>(
    layoutId = R.layout.fragment_chorbo_detail
) {
    override fun onInitDependencyInjection() {
        DaggerChorboDetailComponent
            .builder()
            .coreComponent(ChorboagendaApp.coreComponent(requireContext()))
            .chorboDetailModule(ChorboDetailModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel

    }

    override fun onClear() = Unit
}
