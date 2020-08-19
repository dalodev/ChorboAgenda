/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.detail

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.AppBarLayout
import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.bindings.visible
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.dynamicfeatures.chorboList.detail.di.ChorboDetailModule
import es.littledavity.dynamicfeatures.chorboList.detail.di.DaggerChorboDetailComponent
import es.littledavity.dynamicfeatures.chorbo_list.R
import es.littledavity.dynamicfeatures.chorbo_list.databinding.FragmentChorboDetailBinding
import kotlin.math.abs

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
        viewBinding.appBar.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val expanded = abs(verticalOffset) < appBarLayout.totalScrollRange
                viewBinding.name.visible = abs(verticalOffset) < appBarLayout.totalScrollRange / 2
                if (expanded) {
                    viewBinding.toolbar.navigationIcon?.setTint(Color.WHITE)
                    viewBinding.collapsingToolbar.title = " "
                } else {
                    viewBinding.collapsingToolbar.title = viewModel.chorboDetail.value?.name
                    viewBinding.toolbar.navigationIcon?.setTint(Color.BLACK)
                }
            }
        )
    }

    /**
     * Observer view state change on [ChorboDetailFragment].
     *
     * @param viewState State of chorbo detail fragment view.
     */
    private fun onViewStateChange(viewState: ChorboDetailViewState) {
        when (viewState) {
            is ChorboDetailViewState.Loaded -> {
                viewBinding.name.transitionName = viewState.chorbo.name
                viewBinding.name.text = viewState.chorbo.name
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
