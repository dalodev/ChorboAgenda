/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.navArgs
import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.dynamicfeatures.chorboList.detail.di.ChorboDetailModule
import es.littledavity.dynamicfeatures.chorboList.detail.di.DaggerChorboDetailComponent
import es.littledavity.dynamicfeatures.chorboList.list.di.ChorboListModule
import es.littledavity.dynamicfeatures.chorboList.list.di.DaggerChorboListComponent
import es.littledavity.dynamicfeatures.chorbo_list.R
import es.littledavity.dynamicfeatures.chorbo_list.databinding.FragmentChorboDetailBinding
import es.littledavity.dynamicfeatures.create.image.ImageFragmentArgs

/**
 * View detail for selected chorbo, displaying extra info.
 *
 * @see BaseFragment
 */
class ChorboDetailFragment : BaseFragment<FragmentChorboDetailBinding, ChorboDetailViewModel>(
    layoutId = R.layout.fragment_chorbo_detail
) {

    private val args: ChorboDetailFragmentArgs by navArgs()

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
        viewModel.loadData(args.chorboId)
        observe(viewModel.viewState, ::onViewStateChange)
    }

    /**
     * Initialize view data binding variables.
     */
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.chorboImage.transitionName = args.chorboId.toString()
        viewBinding.toolbar.setNavigationOnClickListener { viewModel.back() }
    }

    /**
     * Observer view state change on [ChorboDetailFragment].
     *
     * @param viewState State of chorbo detaul fragment view.
     */
    private fun onViewStateChange(viewState: ChorboDetailViewState) {
        when (viewState) {
            is ChorboDetailViewState.Loaded -> {
                viewBinding.toolbar.transitionName = viewState.chorbo.name
                viewBinding.toolbar.title = viewState.chorbo.name
            }
        }
    }

    override fun onClear() = Unit

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerChorboDetailComponent
            .builder()
            .coreComponent(ChorboagendaApp.coreComponent(requireContext()))
            .chorboDetailModule(ChorboDetailModule(this))
            .build()
            .inject(this)
    }
}
