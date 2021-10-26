/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.info

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.base.events.Command
import es.littledavity.commons.ui.base.events.Route
import es.littledavity.commons.ui.bindings.viewBinding
import es.littledavity.commons.ui.extensions.addOnBackPressCallback
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

    private var imageResultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            viewModel.updatePhoto(uri)
        }

    private var galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            viewModel.addGalleryImage(uri)
        }

    override fun onInit() {
        super.onInit()
        initContactInfoView()
        initOnBackPress()
    }

    private fun initOnBackPress() {
        addOnBackPressCallback { onBackPressed() }
    }

    private fun initContactInfoView() = with(viewBinding.contactInfoView) {
        applyWindowBottomInsetAsMargin()
        onGalleryClicked = {
            viewModel.onGalleryClicked(it).also {
                updateContactData()
            }
        }
        onBackButtonClicked = { updateContactData().also { viewModel.onBackButtonClicked() } }
        onImageClicked = {
            viewModel.onImageClicked().also {
                updateContactData()
            }
        }
        onChangeImageClicked =
            { viewModel.requestStoragePermission(imageResultLauncher).also { updateContactData() } }
        onLikeButtonClicked = viewModel::onLikeButtonClicked
        onAddGalleryImagesClicked = {
            viewModel.requestStoragePermission(galleryResultLauncher).also { updateContactData() }
        }
        onAddEmptyDetailItem = { updateContactData(true) }
    }

    override fun onBindViewModel() {
        super.onBindViewModel()
        observeUiState()
    }

    private fun observeUiState() {
        viewModel.uiState
            .onEach {
                viewBinding.contactInfoView.currentContact = viewModel.currentContact
                viewBinding.contactInfoView.uiState = it
            }
            .observeIn(this)
    }

    override fun onResume() {
        super.onResume()
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
            is ContactInfoRoute.SettingsApp -> navigator.goSettingsApp()
        }
    }

    private fun navigateToImageViewer(route: ContactInfoRoute.ImageViewer) {
        navigator.goToImageViewer(
            route.title,
            route.initialPosition,
            route.imageUrls,
            route.contactId,
            route.profileView
        )
    }

    private fun updateContactData(new: Boolean = false) = with(viewBinding.contactInfoView) {
        viewModel.updateContactData(
            headerController.name,
            headerController.phone,
            headerController.instagram,
            headerController.rating,
            headerController.country,
            headerController.age,
            infoAdapter.getData(),
            new
        )
    }

    private fun onBackPressed() {
        updateContactData()
        viewModel.onBackPressed()
    }
}
