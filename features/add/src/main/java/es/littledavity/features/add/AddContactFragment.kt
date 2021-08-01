/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.add

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.base.events.Route
import es.littledavity.commons.ui.bindings.viewBinding
import es.littledavity.commons.ui.extensions.applyWindowTopInsetAsPadding
import es.littledavity.commons.ui.extensions.observeIn
import es.littledavity.features.add.databinding.FragmentAddContactBinding
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AddContactFragment : BaseFragment<
    FragmentAddContactBinding,
    AddContactViewModel,
    AddContactNavigator>(
    R.layout.fragment_add_contact
) {

    override val viewBinding by viewBinding(FragmentAddContactBinding::bind)
    override val viewModel by viewModels<AddContactViewModel>()

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            viewModel.updatePhoto(uri)
        }

    override fun onInit() {
        super.onInit()
        initToolbar()
        initAddContactView()
    }

    override fun onBindViewModel() {
        super.onBindViewModel()
        observeUiState()
    }

    private fun observeUiState() {
        viewModel.uiState
            .onEach { viewBinding.addContactView.uiState = it }
            .observeIn(this)
    }

    private fun initToolbar() = with(viewBinding.toolbar) {
        enableBack = false
        applyWindowTopInsetAsPadding()
        onRightButtonClickListener = {
            viewModel.onToolbarRightButtonClicked(
                viewBinding.addContactView.name,
                viewBinding.addContactView.phone
            )
        }
        onLeftButtonClickListener = { viewModel.onToolbarBackButtonClicked() }
    }

    private fun initAddContactView() = with(viewBinding.addContactView) {
        onPhotoClicked = { viewModel.onPhotoClicked(resultLauncher) }
    }

    override fun onRoute(route: Route) {
        super.onRoute(route)
        when (route) {
            is AddContactRoute.Back -> navigator.goBack()
            is AddContactRoute.List -> navigator.goList()
            is AddContactRoute.SettingsApp -> navigator.goSettingsApp()
//            is AddContactRoute.Edit ->
        }
    }
}
