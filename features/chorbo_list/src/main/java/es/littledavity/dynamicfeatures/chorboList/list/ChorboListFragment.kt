/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.doOnPreDraw
import androidx.paging.PagedList
import com.airbnb.lottie.LottieDrawable
import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.extensions.gridLayoutManager
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.dynamicfeatures.chorboList.list.adapter.ChorboListAdapterState
import es.littledavity.dynamicfeatures.chorboList.list.adapter.ChorbosListAdapter
import es.littledavity.dynamicfeatures.chorboList.list.di.ChorboListModule
import es.littledavity.dynamicfeatures.chorboList.list.di.DaggerChorboListComponent
import es.littledavity.dynamicfeatures.chorboList.list.model.ChorboItem
import es.littledavity.dynamicfeatures.chorbo_list.R
import es.littledavity.dynamicfeatures.chorbo_list.databinding.FragmentChorboListBinding
import javax.inject.Inject

class ChorboListFragment : BaseFragment<FragmentChorboListBinding, ChorboListViewModel>(
    layoutId = R.layout.fragment_chorbo_list
) {

    @Inject
    lateinit var viewAdapter: ChorbosListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)
        observe(viewModel.viewState, ::onViewStateChange)
        observe(viewModel.data, ::onViewDataChange)
        observe(viewModel.event, ::onViewEvent)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onInitDependencyInjection() {
        DaggerChorboListComponent
            .builder()
            .coreComponent(ChorboagendaApp.coreComponent(requireContext()))
            .chorboListModule(ChorboListModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        enableBack = false
        viewBinding?.viewModel = viewModel
        viewBinding?.includeList?.chorboList?.apply {
            adapter = viewAdapter
            gridLayoutManager?.spanSizeLookup = viewAdapter.getSpanSizeLookup()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshLoadedChorboList()
    }

    override fun onClearView() {
        viewBinding?.includeList?.chorboList?.adapter = null
    }


    // ============================================================================================
    //  Private observers methods
    // ============================================================================================

    /**
     * Observer view data change on [ChorboListViewModel].
     *
     * @param viewData Paged list of chorbos.
     */
    private fun onViewDataChange(viewData: PagedList<ChorboItem>) {
        viewAdapter.submitList(viewData)
    }

    /**
     * Observer view state change on [ChorboListViewModel].
     *
     * @param viewState State of chorbo list.
     */
    private fun onViewStateChange(viewState: ChorboListViewState) {
        when (viewState) {
            is ChorboListViewState.Loaded ->
                viewAdapter.submitState(ChorboListAdapterState.Added)
            is ChorboListViewState.AddLoading ->
                viewAdapter.submitState(ChorboListAdapterState.AddLoading)
            is ChorboListViewState.AddError ->
                viewAdapter.submitState(ChorboListAdapterState.AddError)
            is ChorboListViewState.NoMoreElements ->
                viewAdapter.submitState(ChorboListAdapterState.NoMore)
            is ChorboListViewState.Empty ->
                viewBinding?.includeListEmpty?.openOptionsFab?.repeatCount = LottieDrawable.INFINITE
            else -> Unit
        }
    }

    /**
     * Observer view event change on [ChorboListViewModel].
     *
     * @param viewEvent Event on chorbos list.
     */
    private fun onViewEvent(viewEvent: ChorboListViewEvent) {
        when (viewEvent) {
            is ChorboListViewEvent.OpenChorboOptions ->
                viewModel.navigate(ChorboListFragmentDirections.toName())
        }
    }
}
