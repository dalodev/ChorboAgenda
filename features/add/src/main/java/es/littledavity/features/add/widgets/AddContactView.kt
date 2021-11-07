/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.add.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.net.toUri
import androidx.core.view.isVisible
import es.littledavity.commons.ui.extensions.fadeIn
import es.littledavity.commons.ui.extensions.getString
import es.littledavity.commons.ui.extensions.layoutInflater
import es.littledavity.commons.ui.extensions.makeGone
import es.littledavity.commons.ui.extensions.makeInvisible
import es.littledavity.commons.ui.extensions.makeVisible
import es.littledavity.commons.ui.extensions.observeChanges
import es.littledavity.commons.ui.extensions.onClick
import es.littledavity.commons.ui.extensions.resetAnimation
import es.littledavity.commons.ui.extensions.showSnackBar
import es.littledavity.commons.ui.widgets.contacts.ContactModel
import es.littledavity.features.add.AddContactUiState
import es.littledavity.features.add.R
import es.littledavity.features.add.databinding.ViewAddContactBinding

class AddContactView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewAddContactBinding.inflate(context.layoutInflater, this)

    var uiState by observeChanges<AddContactUiState>(AddContactUiState.New) { _, newState ->
        handleUiStateChange(newState)
    }

    var onPhotoClicked: (() -> Unit)? = null
    var onDoneClicked: (() -> Unit)? = null

    val name: String
        get() = binding.nameLayout.editText?.text.toString()

    val phone: String
        get() = binding.phoneLayout.editText?.text.toString()

    init {
        initDefaults()
        binding.photoView.onClick { onPhotoClicked?.invoke() }
        binding.addDoneBtn.onClick { onDoneClicked?.invoke() }
    }

    private fun initDefaults() {
        uiState = uiState
    }

    private fun handleUiStateChange(newState: AddContactUiState) {
        when (newState) {
            is AddContactUiState.New -> onNewStateSelected()
            is AddContactUiState.Loading -> onLoadingStateSelected()
            is AddContactUiState.Error -> onErrorStateSelected(
                newState.nameError,
                newState.phoneError
            )
            is AddContactUiState.ErrorPermission -> onErrorPermissionSelected(newState.navigation)
            is AddContactUiState.Result -> onResultStateSelected(newState)
        }
    }

    private fun onNewStateSelected() {
        showMainView()
        hideProgressBar()
    }

    private fun onLoadingStateSelected() {
        showProgressBar()
        hideMainView()
    }

    private fun onErrorStateSelected(nameError: Boolean, phoneError: Boolean) {
        if (nameError) {
            binding.nameLayout.editText?.error = getString(R.string.add_contact_name_error)
        }
        if (phoneError) {
            binding.phoneLayout.editText?.error = getString(R.string.add_contact_phone_error)
        }
        hideProgressBar()
    }

    private fun onErrorPermissionSelected(navigation: () -> Unit) {
        binding.root.showSnackBar(
            getString(R.string.add_contact_photo_permission_error),
            getString(R.string.add_contact_settings_button),
            navigation
        )
    }

    private fun onResultStateSelected(uiState: AddContactUiState.Result) {
        bindModel(uiState.model)

        showMainView()
        hideProgressBar()
        showAddPhotoView()
    }

    private fun showProgressBar() = with(binding.progressBar) {
        makeVisible()
    }

    private fun hideProgressBar() = with(binding.progressBar) {
        makeGone()
    }

    private fun hideMainView() = with(binding.mainView) {
        makeInvisible()
        resetAnimation()
    }

    private fun showMainView() = with(binding.mainView) {
        if (isVisible) return

        makeVisible()
        fadeIn()
    }

    private fun bindModel(model: ContactModel) {
        binding.photoView.setImageURI(model.coverImageUrl?.toUri())
        binding.nameText.setText(model.name)
        binding.phoneText.setText(model.phone)
    }

    private fun showAddPhotoView() = with(binding.addPhotoView) {
        isVisible = binding.photoView.drawable == null
    }
}
