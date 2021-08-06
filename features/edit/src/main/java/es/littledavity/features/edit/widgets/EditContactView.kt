package es.littledavity.features.edit.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.extensions.*
import es.littledavity.core.providers.StringProvider
import es.littledavity.features.edit.EditContactUiState
import es.littledavity.features.edit.databinding.ViewEditContactBinding
import es.littledavity.features.edit.widgets.model.ContactInfoModel
import javax.inject.Inject

@AndroidEntryPoint
internal class EditContactView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewEditContactBinding.inflate(context.layoutInflater, this)

    var uiState by observeChanges<EditContactUiState>(EditContactUiState.Loading) { _, newState ->
        handleUiStateChange(newState)
    }

    @Inject
    lateinit var stringProvider: StringProvider

    private fun handleUiStateChange(newState: EditContactUiState) {
        when (newState) {
            is EditContactUiState.Error -> onErrorStateSelected()
            is EditContactUiState.Loading -> onLoadingStateSelected()
            is EditContactUiState.Result -> onResultStateSelected(newState)
        }
    }

    private fun onErrorStateSelected() {

    }

    private fun onLoadingStateSelected() {
        showProgressBar()
        hideInfoView()
        hideMainView()
    }

    private fun onResultStateSelected(uiState: EditContactUiState.Result) {
        bindModel(uiState.model)

        showMainView()
        hideInfoView()
        hideProgressBar()
    }

    private fun showProgressBar() = with(binding.progressBar) {
        makeVisible()
    }

    private fun hideProgressBar() = with(binding.progressBar) {
        makeGone()
    }

    private fun showInfoView() = with(binding.infoView) {
        if (isVisible) return

        makeVisible()
        fadeIn()
    }

    private fun hideInfoView() = with(binding.infoView) {
        makeGone()
        resetAnimation()
    }

    private fun showMainView() = with(binding.mainView) {
        if (isVisible) return

        makeVisible()
        fadeIn()
    }

    private fun hideMainView() = with(binding.mainView) {
        makeInvisible()
        resetAnimation()
    }

    private fun bindModel(model: ContactInfoModel) {

    }
}