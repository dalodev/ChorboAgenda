package es.littledavity.features.add

import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.base.events.Route
import es.littledavity.commons.ui.bindings.viewBinding
import es.littledavity.commons.ui.extensions.applyWindowTopInsetAsPadding
import es.littledavity.commons.ui.extensions.onClick
import es.littledavity.features.add.databinding.FragmentAddContactBinding

private const val IMAGE_TYPE = "image/*"

@AndroidEntryPoint
class AddContactFragment : BaseFragment<
        FragmentAddContactBinding,
        AddContactViewModel,
        AddContactNavigator>(
    R.layout.fragment_add_contact
) {

    override val viewBinding by viewBinding(FragmentAddContactBinding::bind)
    override val viewModel by viewModels<AddContactViewModel>()

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        updatePhoto(uri)
    }

    override fun onInit() {
        super.onInit()
        initToolbar()
        initPhotoView()
    }

    override fun onBindViewModel() {
        super.onBindViewModel()
        //TODO observe states
    }

    private fun initToolbar() = with(viewBinding.toolbar) {
        enableBack = false
        applyWindowTopInsetAsPadding()
        onRightButtonClickListener = {
            viewModel.onToolbarRightButtonClicked(viewBinding.phoneLayout.editText?.text?.isBlank())
        }
        onLeftButtonClickListener = { viewModel.onToolbarBackButtonClicked() }
    }

    private fun initPhotoView() = with(viewBinding.photoView) {
        onClick {
            resultLauncher.launch(IMAGE_TYPE)
        }
    }

    private fun updatePhoto(uri: Uri?) = with(viewBinding) {
        uri?.let(photoView::setImageURI)
        addPhotoView.isVisible = photoView.drawable == null
    }

    override fun onRoute(route: Route) {
        super.onRoute(route)
        when (route) {
            is AddContactRoute.Back -> navigator.goBack()
        }
    }
}