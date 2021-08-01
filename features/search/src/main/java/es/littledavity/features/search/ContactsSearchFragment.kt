/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.search

import android.text.InputType
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.base.events.Command
import es.littledavity.commons.ui.base.events.Route
import es.littledavity.commons.ui.bindings.viewBinding
import es.littledavity.commons.ui.extensions.applyWindowBottomInsetAsMargin
import es.littledavity.commons.ui.extensions.applyWindowTopInsetAsPadding
import es.littledavity.commons.ui.extensions.observeIn
import es.littledavity.features.search.databinding.FragmentContactsSearchBinding
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
internal class ContactsSearchFragment : BaseFragment<
    FragmentContactsSearchBinding,
    ContactsSearchViewModel,
    ContactsSearchNavigator>(
    R.layout.fragment_contacts_search
) {

    override val viewBinding by viewBinding(FragmentContactsSearchBinding::bind)
    override val viewModel by viewModels<ContactsSearchViewModel>()

    override fun onInit() {
        super.onInit()
        initSearchToolbar()
        initContactsView()
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

    private fun initContactsView() = with(viewBinding.contactsView) {
        applyWindowBottomInsetAsMargin()
        onContactClicked = viewModel::onContactClicked
        onBottomReached = viewModel::onBottomReached
    }

    private fun initSearchToolbar() = with(viewBinding.searchToolbar) {
        applyWindowTopInsetAsPadding()

        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
        hintText = getString(R.string.contacts_search_fragment_search_hint)
        onSearchActionRequested = viewModel::onSearchActionRequested
        onBackButtonClicked = { viewModel.onToolbarBackButtonClicked() }
    }

    override fun onResume() {
        super.onResume()
        if (viewBinding.searchToolbar.searchQuery.isEmpty()) {
            viewBinding.searchToolbar.showKeyboard(true)
        }
    }

    override fun onPause() {
        super.onPause()
        viewBinding.searchToolbar.hideKeyboard()
    }

    override fun onHandleCommand(command: Command) {
        super.onHandleCommand(command)
        when (command) {
            is ContactsSearchCommand.ClearItems -> viewBinding.contactsView.clearItems()
        }
    }

    override fun onRoute(route: Route) {
        super.onRoute(route)
        when (route) {
            is ContactsSearchRoute.Info -> navigator.goToInfo(route.contactId)
            is ContactsSearchRoute.Back -> navigator.goBack()
        }
    }
}
