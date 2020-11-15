/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.detail

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.AppBarLayout
import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.bindings.visible
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.dynamicfeatures.chorboList.detail.adapter.ChorboDetailAdapter
import es.littledavity.dynamicfeatures.chorboList.detail.di.ChorboDetailModule
import es.littledavity.dynamicfeatures.chorboList.detail.di.DaggerChorboDetailComponent
import es.littledavity.dynamicfeatures.chorbo_list.R
import es.littledavity.dynamicfeatures.chorbo_list.databinding.FragmentChorboDetailBinding
import javax.inject.Inject
import kotlin.math.abs

class ChorboDetailFragment : BaseFragment<FragmentChorboDetailBinding, ChorboDetailViewModel>(
    layoutId = R.layout.fragment_chorbo_detail
) {

    private val args: ChorboDetailFragmentArgs by navArgs()

    @Inject
    lateinit var adapter: ChorboDetailAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData(args.chorboId)
        observe(viewModel.viewState, ::onViewStateChange)
    }

    override fun onInitDataBinding() {
        enableBack = true
        setHasOptionsMenu(true)
        viewBinding?.detailList?.adapter = adapter
        viewBinding?.viewModel = viewModel
        viewBinding?.chorboImage?.transitionName = args.chorboId.toString()
        setupToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Observer view state change on [ChorboDetailFragment].
     *
     * @param viewState State of chorbo detail fragment view.
     */
    private fun onViewStateChange(viewState: ChorboDetailViewState) {
        when (viewState) {
            is ChorboDetailViewState.Loaded -> {
                viewBinding?.nameToolbar?.transitionName = viewState.chorbo.name
                viewBinding?.nameToolbar?.text = viewState.chorbo.name
                adapter.submitSectionedList(viewState.chorbo.info)
            }
        }
    }

    private fun setupToolbar() {
        viewBinding?.appBar?.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val expanded = abs(verticalOffset) < appBarLayout.totalScrollRange
                viewBinding?.nameToolbar?.visible =
                    abs(verticalOffset) < appBarLayout.totalScrollRange
                if (expanded) {
                    viewBinding?.toolbar?.navigationIcon?.setTint(Color.WHITE)
                    viewBinding?.collapsingToolbar?.title = " "
                    viewBinding?.toolbar?.menu?.findItem(R.id.delete)?.icon?.setTint(Color.WHITE)
                } else {
                    viewBinding?.collapsingToolbar?.title = viewModel.chorboDetail.value?.name
                    viewBinding?.toolbar?.navigationIcon?.setTint(Color.BLACK)
                    viewBinding?.toolbar?.menu?.findItem(R.id.delete)?.icon?.setTint(Color.BLACK)
                }
            }
        )
    }

    override fun onClearView() = Unit
    override fun toolbar() = viewBinding?.toolbar

    override fun onInitDependencyInjection() {
        DaggerChorboDetailComponent
            .builder()
            .coreComponent(ChorboagendaApp.coreComponent(requireContext()))
            .chorboDetailModule(ChorboDetailModule(this))
            .build()
            .inject(this)
    }
}
