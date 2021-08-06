package es.littledavity.features.edit

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.base.events.Route
import es.littledavity.commons.ui.bindings.viewBinding
import es.littledavity.commons.ui.extensions.observeIn
import es.littledavity.features.edit.databinding.FragmentEditContactBinding
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
internal class EditContactFragment : BaseFragment<
        FragmentEditContactBinding,
        EditContactViewModel,
        EditContactNavigator
        >(
    R.layout.fragment_edit_contact
) {
    override val viewBinding by viewBinding(FragmentEditContactBinding::bind)
    override val viewModel by viewModels<EditContactViewModel>()

    override fun onInit() {
        super.onInit()

    }

    override fun onBindViewModel() {
        super.onBindViewModel()
        observeUiState()
    }

    private fun observeUiState() {
        viewModel.uiState
            .onEach { viewBinding.editContactView.uiState = it }
            .observeIn(this)
    }

    override fun onRoute(route: Route) {
        super.onRoute(route)
        when (route) {
            is EditContactRoute.Back -> navigator.goBack()
            is EditContactRoute.List -> navigator.goList()
        }
    }

}