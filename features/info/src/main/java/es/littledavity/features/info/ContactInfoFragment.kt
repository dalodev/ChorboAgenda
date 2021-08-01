/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.info

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.base.events.Command
import es.littledavity.commons.ui.base.events.Route
import es.littledavity.commons.ui.bindings.viewBinding
import es.littledavity.commons.ui.extensions.applyWindowBottomInsetAsMargin
import es.littledavity.commons.ui.extensions.defaultWindowAnimationDuration
import es.littledavity.commons.ui.extensions.observeIn
import es.littledavity.commons.ui.extensions.showShortToast
import es.littledavity.core.urlopener.UrlOpener
import es.littledavity.features.info.databinding.FragmentContactInfoBinding
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
internal class ContactInfoFragment : BaseFragment<
    FragmentContactInfoBinding,
    ContactInfoViewModel,
    ContactInfoNavigator>(
    R.layout.fragment_contact_info
) {
    override val viewBinding by viewBinding(FragmentContactInfoBinding::bind)
    override val viewModel by viewModels<ContactInfoViewModel>()

    @Inject
    lateinit var urlOpener: UrlOpener

    override fun onInit() {
        super.onInit()
        initContactInfoView()
    }

    private fun initContactInfoView() = with(viewBinding.contactInfoView) {
        applyWindowBottomInsetAsMargin()
        onGalleryClicked = viewModel::onGalleryClicked
        onBackButtonClicked = viewModel::onBackButtonClicked
        onImageClicked = viewModel::onCoverClicked
        onLikeButtonClicked = viewModel::onLikeButtonClicked
    }

    override fun onBindViewModel() {
        super.onBindViewModel()
        observeUiState()
    }

    private fun observeUiState() {
        viewModel.uiState
            .onEach { viewBinding.contactInfoView.uiState = it }
            .observeIn(this)
    }

    override fun onLoadData() {
        super.onLoadData()
        viewModel.loadData(requireContext().defaultWindowAnimationDuration())
    }

    override fun onHandleCommand(command: Command) {
        super.onHandleCommand(command)
        when (command) {
            is ContactInfoCommand.OpenUrl -> openUrl(command.url)
        }
    }

    private fun openUrl(url: String) {
        if (!urlOpener.openUrl(url, requireActivity())) {
            showShortToast(getString(R.string.url_opener_not_found))
        }
    }

    override fun onRoute(route: Route) {
        super.onRoute(route)
        when (route) {
            is ContactInfoRoute.ImageViewer -> navigateToImageViewer(route)
            is ContactInfoRoute.Info -> navigator.goToInfo(route.contactId)
            is ContactInfoRoute.Back -> navigator.goBack()
        }
    }

    private fun navigateToImageViewer(route: ContactInfoRoute.ImageViewer) {
        navigator.goToImageViewer(
            route.title,
            route.initialPosition,
            route.imageUrls
        )
    }
}
