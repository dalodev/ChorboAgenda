package es.littledavity.dynamicfeatures.home

import android.os.Bundle
import android.view.View
import androidx.paging.PagedList
import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.dynamicfeatures.home.databinding.FragmentHomeBinding
import es.littledavity.dynamicfeatures.home.di.DaggerHomeComponent
import es.littledavity.dynamicfeatures.home.di.HomeModule
import es.littledavity.dynamicfeatures.home.model.ChorboItem
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.LottieDrawable.REVERSE
import es.littledavity.commons.ui.extensions.linearLayoutManager
import es.littledavity.dynamicfeatures.home.adapter.ChorboListAdapterState
import es.littledavity.dynamicfeatures.home.adapter.ChorbosListAdapter
import javax.inject.Inject

/**
 * Home principal view containing bottom navigation bar with different chorbos tabs.
 *
 * @see BaseFragment
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    layoutId = R.layout.fragment_home
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
     * Observer view data change on [HomeViewModel].
     *
     * @param viewData Paged list of chorbos.
     */
    private fun onViewDataChange(viewData: PagedList<ChorboItem>) {
        viewAdapter.submitList(viewData)
    }

    /**
     * Observer view state change on [HomeViewModel].
     *
     * @param viewState State of chorbo list.
     */
    private fun onViewStateChange(viewState: HomeViewState) {
        when (viewState) {
            is HomeViewState.Loaded ->
                viewAdapter.submitState(ChorboListAdapterState.Added)
            is HomeViewState.AddLoading ->
                viewAdapter.submitState(ChorboListAdapterState.AddLoading)
            is HomeViewState.AddError ->
                viewAdapter.submitState(ChorboListAdapterState.AddError)
            is HomeViewState.NoMoreElements ->
                viewAdapter.submitState(ChorboListAdapterState.NoMore)
            is HomeViewState.Empty ->
                viewBinding.openOptionsFab.repeatCount = LottieDrawable.INFINITE
        }
    }

    /**
     * Observer view event change on [HomeViewModel].
     *
     * @param viewEvent Event on chorbos list.
     */
    private fun onViewEvent(viewEvent: ChorboListViewEvent) {
        when (viewEvent) {
            is ChorboListViewEvent.OpenChorboDetail ->
               Unit
            is ChorboListViewEvent.OpenChorboOptions ->{
                viewBinding.openOptionsFab.cancelAnimation()
                viewBinding.openOptionsFab.repeatCount = 1
                viewBinding.openOptionsFab.playAnimation()
            }

        }
    }


}