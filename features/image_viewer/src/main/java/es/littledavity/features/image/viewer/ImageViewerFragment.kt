/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.image.viewer

import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.base.events.Command
import es.littledavity.commons.ui.base.events.Route
import es.littledavity.commons.ui.bindings.viewBinding
import es.littledavity.commons.ui.extensions.addOnBackPressCallback
import es.littledavity.commons.ui.extensions.getColor
import es.littledavity.commons.ui.extensions.navigationBarColor
import es.littledavity.commons.ui.extensions.observeIn
import es.littledavity.commons.ui.extensions.statusBarColor
import es.littledavity.commons.ui.extensions.window
import es.littledavity.core.sharers.TextSharer
import es.littledavity.features.image.viewer.databinding.FragmentImageViewerBinding
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
internal class ImageViewerFragment : BaseFragment<
        FragmentImageViewerBinding,
        ImageViewerViewModel,
        ImageViewerNavigator
        >(R.layout.fragment_image_viewer) {

    override val viewBinding by viewBinding(FragmentImageViewerBinding::bind)
    override val viewModel by viewModels<ImageViewerViewModel>()

    private var originalStatusBarColor: Int = 0

    @Inject
    lateinit var textSharer: TextSharer

    override fun onInit() {
        super.onInit()
        initSystemWindows()
        initOnBackPress()
        initImageViewerView()
    }

    private fun initSystemWindows() {
        originalStatusBarColor = window.statusBarColor

        context?.let {
            window.statusBarColor = getColor(R.color.flag_translucent_status)
        }

        statusBarColor = getColor(R.color.image_viewer_bar_background_color)
        navigationBarColor = getColor(R.color.image_viewer_bar_background_color)
    }

    private fun initOnBackPress() {
        addOnBackPressCallback { onBackPressed() }
    }

    private fun initImageViewerView() = with(viewBinding.imageViewerView) {
        onToolbarLeftBtnClicked = ::onBackPressed
        onToolbarRightBtnClicked = viewModel::onToolbarRightButtonClicked
        onPageChanged = viewModel::onPageChanged
    }

    override fun onBindViewModel() {
        super.onBindViewModel()

        observeSelectedPosition()
        observeImageUrls()
        observeToolbarTitle()
    }

    private fun observeSelectedPosition() {
        viewModel.selectedPosition
            .onEach { viewBinding.imageViewerView.selectedPosition = it }
            .observeIn(this)
    }

    private fun observeImageUrls() {
        viewModel.imageUrls
            .onEach { viewBinding.imageViewerView.imageUrls = it }
            .observeIn(this)
    }

    private fun observeToolbarTitle() {
        viewModel.toolbarTitle
            .onEach { viewBinding.imageViewerView.toolbarTitle = it }
            .observeIn(this)
    }

    private fun onBackPressed() {
        if (viewBinding.imageViewerView.isCurrentImageScaled()) {
            viewBinding.imageViewerView.resetCurrentImageScale()
        } else {
            viewModel.onBackPressed()
        }
    }

    override fun onHandleCommand(command: Command) {
        super.onHandleCommand(command)
        when (command) {
            is ImageViewerCommand.ShareText -> shareText(command.text)
            is ImageViewerCommand.ResetSystemWindows -> resetSystemWindows()
        }
    }

    private fun shareText(text: String) {
        textSharer.share(requireActivity(), text)
    }

    private fun resetSystemWindows() {
        @Suppress("DEPRECATION")
        window.addFlags(FLAG_TRANSLUCENT_STATUS)

        statusBarColor = originalStatusBarColor
        navigationBarColor = getColor(R.color.colorNavigationBar)
    }

    override fun onRoute(route: Route) {
        super.onRoute(route)
        when (route) {
            is ImageViewerRoute.Back -> navigator.goBack()
        }
    }
}
