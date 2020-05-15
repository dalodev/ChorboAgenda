/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorbo_list.list

import android.os.Bundle
import android.view.View
import androidx.paging.PagedList
import com.airbnb.lottie.LottieDrawable
import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.extensions.linearLayoutManager
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.dynamicfeatures.chorbo_list.R
import es.littledavity.dynamicfeatures.chorbo_list.databinding.FragmentChorboListBinding
import es.littledavity.dynamicfeatures.chorbo_list.list.adapter.ChorboListAdapterState
import es.littledavity.dynamicfeatures.chorbo_list.list.adapter.ChorbosListAdapter
import es.littledavity.dynamicfeatures.chorbo_list.list.di.ChorboListModule
import es.littledavity.dynamicfeatures.chorbo_list.list.di.DaggerChorboListComponent
import es.littledavity.dynamicfeatures.chorbo_list.list.model.ChorboItem
import javax.inject.Inject

/**
 * Chorbo list principal view containing bottom navigation bar with different chorbos tabs.
 *
 * @see BaseFragment
 */
class ChorboListFragment : BaseFragment<FragmentChorboListBinding, ChorboListViewModel>(
    layoutId = R.layout.fragment_chorbo_list
) {

    @Inject
    lateinit var viewAdapter: ChorbosListAdapter

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
        observe(viewModel.state, ::onViewStateChange)
        observe(viewModel.data, ::onViewDataChange)
        observe(viewModel.event, ::onViewEvent)
    }

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerChorboListComponent
            .builder()
            .coreComponent(ChorboagendaApp.coreComponent(requireContext()))
            .chorboListModule(ChorboListModule(this))
            .build()
            .inject(this)
    }

    /**
     * Initialize view data binding variables.
     */
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.chorboList.apply {
            adapter = viewAdapter
            layoutManager = linearLayoutManager
        }
    }

    override fun onClear() {
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
                viewBinding.openOptionsFab.repeatCount = LottieDrawable.INFINITE
        }
    }

    /**
     * Observer view event change on [ChorboListViewModel].
     *
     * @param viewEvent Event on chorbos list.
     */
    private fun onViewEvent(viewEvent: ChorboListViewEvent) {
        when (viewEvent) {
            is ChorboListViewEvent.OpenChorboOptions -> playAnimationAndNavigate()
        }
    }

    private fun playAnimationAndNavigate() {
        viewBinding.openOptionsFab.cancelAnimation()
        viewBinding.openOptionsFab.repeatCount = 0
        viewBinding.openOptionsFab.addAnimatorUpdateListener {
            val progress = (it.animatedValue as Float * 100).toInt()
            // navigate
            if (progress >= 25) {
                val direction = ChorboListFragmentDirections.toName()
                viewModel.navigate(direction)
            }
        }
        viewBinding.openOptionsFab.playAnimation()
    }
}
