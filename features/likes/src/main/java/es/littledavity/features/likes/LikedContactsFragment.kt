/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.likes

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.base.events.Route
import es.littledavity.commons.ui.bindings.viewBinding
import es.littledavity.commons.ui.extensions.observeIn
import es.littledavity.features.likes.databinding.FragmentLikedContactsBinding
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LikedContactsFragment : BaseFragment<
        FragmentLikedContactsBinding,
        LikedContactsViewModel,
        LikedContactsNavigator>(R.layout.fragment_liked_contacts) {

    override val viewBinding by viewBinding(FragmentLikedContactsBinding::bind)
    override val viewModel by viewModels<LikedContactsViewModel>()

    override fun onInit() {
        super.onInit()
        initContactsView()
    }

    private fun initContactsView() = with(viewBinding.contactsView) {
        onContactClicked = viewModel::onContactClicked
        onBottomReached = viewModel::onBottomReached
    }

    override fun onBindViewModel() {
        super.onBindViewModel()
        observeUiState()
    }

    private fun observeUiState() {
        viewModel.uiState
            .onEach { viewBinding.contactsView.uiState = it }
            .observeIn(this)
    }

    override fun onLoadData() {
        super.onLoadData()
        viewModel.loadData()
    }

    override fun onRoute(route: Route) {
        super.onRoute(route)
        when (route) {
            is LikedContactsRoute.Info -> navigator.goToInfo(route.contactId)
        }
    }
}
