/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.dashboard.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.base.events.Route
import es.littledavity.commons.ui.bindings.viewBinding
import es.littledavity.commons.ui.extensions.applyWindowBottomInsetAsMargin
import es.littledavity.commons.ui.extensions.applyWindowTopInsetAsPadding
import es.littledavity.commons.ui.extensions.getColor
import es.littledavity.commons.ui.extensions.getSerializable
import es.littledavity.commons.ui.extensions.setItemColors
import es.littledavity.features.dashboard.R
import es.littledavity.features.dashboard.databinding.FragmentDashboardBinding
import es.littledavity.features.dashboard.fragment.DashboardPage.Companion.toDashboardPageFromMenuItemId
import es.littledavity.features.dashboard.fragment.adapter.DashboardViewPagerAdapter
import es.littledavity.features.dashboard.fragment.adapter.DashboardViewPagerAdapterFactory
import javax.inject.Inject

@AndroidEntryPoint
internal class DashboardFragment : BaseFragment<
        FragmentDashboardBinding,
        DashboardViewModel,
        DashBoardNavigator
        >(R.layout.fragment_dashboard) {

    private companion object {
        private const val KEY_ADAPTER_STATE = "adapter_state"
        private const val KEY_SELECTED_PAGE = "selected_page"
        private val DEFAULT_SELECTED_PAGE = DashboardPage.CONTACTS

    }

    override val viewBinding by viewBinding(FragmentDashboardBinding::bind)
    override val viewModel by viewModels<DashboardViewModel>()

    private lateinit var viewPagerAdapter: DashboardViewPagerAdapter

    @Inject
    lateinit var viewPagerAdapterFactory: DashboardViewPagerAdapterFactory

    override fun onInit() {
        super.onInit()
        initToolbar()
        initBottomNavigation()
        initViewPager()
    }

    override fun onPostInit() {
        super.onPostInit()
        selectPage(DEFAULT_SELECTED_PAGE)
    }

    private fun selectPage(page: DashboardPage) = with(viewBinding) {
        bottomNav.selectedItemId = page.menuItemId
        viewPagerContainer.viewPager.setCurrentItem(page.position, false)
    }

    private fun initToolbar() = with(viewBinding.toolbar) {
        enableBack = false
        applyWindowTopInsetAsPadding()
        onRightButtonClickListener = { viewModel.onToolbarRightButtonClicked() }
    }

    private fun initBottomNavigation() = with(viewBinding.bottomNav) {
        applyWindowBottomInsetAsMargin()
        setItemColors(
            unselectedStateColor = getColor(R.color.bottom_navigation_item_color_state_unselected),
            selectedStateColor = getColor(R.color.bottom_navigation_item_color_state_selected)
        )
        setOnNavigationItemSelectedListener(::onNavigationItemSelected)
    }

    private fun initViewPager() = with(viewBinding.viewPagerContainer.viewPager) {
        adapter = initViewPagerAdapter()
        offscreenPageLimit = viewPagerAdapter.itemCount
        isUserInputEnabled = false
    }

    private fun initViewPagerAdapter() = viewPagerAdapterFactory.createAdapter(this)
        .also { viewPagerAdapter = it }

    private fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        return if (menuItem.toPagePosition() == DashboardPage.ADD_CONTACT.position) {
            viewModel.onAddContactButtonClicked()
            false
        } else {
            viewBinding.viewPagerContainer.viewPager.setCurrentItem(
                menuItem.toPagePosition(),
                false
            )
            true
        }
    }

    private fun MenuItem.toPagePosition() = itemId.toDashboardPageFromMenuItemId().position

    override fun onRoute(route: Route) {
        super.onRoute(route)

        when (route) {
            is DashboardRoute.Search -> navigator.goToSearch()
            is DashboardRoute.Add -> navigator.goToAddContact()
        }
    }

    override fun onRestoreState(state: Bundle) {
        super.onRestoreState(state)

        // Restoring the adapter's state since for some reason it does not do this by itself
        state.getParcelable<Parcelable>(KEY_ADAPTER_STATE)?.let(viewPagerAdapter::restoreState)
        selectPage(state.getSerializable(KEY_SELECTED_PAGE, DEFAULT_SELECTED_PAGE))
    }

    override fun onSaveState(state: Bundle) {
        super.onSaveState(state)

        // Saving the adapter's state since for some reason it does not do this by itself
        state.putParcelable(KEY_ADAPTER_STATE, viewPagerAdapter.saveState())
        state.putSerializable(
            KEY_SELECTED_PAGE,
            viewBinding.bottomNav.selectedItemId.toDashboardPageFromMenuItemId()
        )
    }
}
