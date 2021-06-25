package es.littledavity.features.add

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.base.events.Route
import es.littledavity.commons.ui.bindings.viewBinding
import es.littledavity.commons.ui.extensions.applyWindowTopInsetAsPadding
import es.littledavity.commons.ui.extensions.onClick
import es.littledavity.features.add.R
import es.littledavity.features.add.databinding.FragmentAddContactBinding

@AndroidEntryPoint
class AddContactFragment : BaseFragment<
        FragmentAddContactBinding,
        AddContactViewModel,
        AddContactNavigator>(R.layout.fragment_add_contact) {

    override val viewBinding by viewBinding(FragmentAddContactBinding::bind)
    override val viewModel by viewModels<AddContactViewModel>()

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            //TODO save image on view
        }
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
        onRightButtonClickListener = { viewModel.onToolbarRightButtonClicked() }
        onLeftButtonClickListener = { viewModel.onToolbarBackButtonClicked() }
    }

    private fun initPhotoView() = with(viewBinding.photoView) {
        onClick {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            resultLauncher.launch(intent)
        }

    }

    override fun onRoute(route: Route) {
        super.onRoute(route)
        when (route) {
            is AddContactRoute.Back -> navigator.goBack()
        }
    }
}