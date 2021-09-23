/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.contacts

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.base.events.Route
import es.littledavity.commons.ui.bindings.viewBinding
import es.littledavity.commons.ui.extensions.observeIn
import es.littledavity.features.contacts.databinding.FragmentContactsBinding
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ContactsFragment : BaseFragment<
    FragmentContactsBinding,
    ContactsViewModel,
    ContactsNavigator>(
    R.layout.fragment_contacts
) {

    override val viewBinding by viewBinding(FragmentContactsBinding::bind)
    override val viewModel by viewModels<ContactsViewModel>()

    override fun onInit() {
        super.onInit()
        initContactsView()
    }

    override fun onBindViewModel() {
        super.onBindViewModel()
        observeUiState()
    }

    override fun onLoadData() {
        super.onLoadData()
        viewModel.loadData()
    }

    override fun onRoute(route: Route) {
        super.onRoute(route)
        when (route) {
            is ContactsRoute.Info -> navigator.goToInfo(route.contactId)
        }
    }

    private fun initContactsView() = with(viewBinding.contactsView) {
        onContactClicked = viewModel::onContactClicked
        onBottomReached = viewModel::onBottomReached
        onRemoveContact = viewModel::onRemoveContact
    }

    private fun observeUiState() {
        viewModel.uiState
            .onEach { viewBinding.contactsView.uiState = it }
            .observeIn(this)
    }
}
