/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.info.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.base.rv.Item
import es.littledavity.commons.ui.base.rv.NoDependencies
import es.littledavity.commons.ui.extensions.disableChangeAnimations
import es.littledavity.commons.ui.extensions.fadeIn
import es.littledavity.commons.ui.extensions.getDimensionPixelSize
import es.littledavity.commons.ui.extensions.getString
import es.littledavity.commons.ui.extensions.layoutInflater
import es.littledavity.commons.ui.extensions.makeGone
import es.littledavity.commons.ui.extensions.makeInvisible
import es.littledavity.commons.ui.extensions.makeVisible
import es.littledavity.commons.ui.extensions.observeChanges
import es.littledavity.commons.ui.extensions.resetAnimation
import es.littledavity.commons.ui.extensions.showSnackBar
import es.littledavity.commons.ui.recyclerview.SpacingItemDecorator
import es.littledavity.core.providers.StringProvider
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.features.info.R
import es.littledavity.features.info.databinding.ViewContactInfoBinding
import es.littledavity.features.info.widgets.main.header.ContactHeaderController
import es.littledavity.features.info.widgets.main.model.ContactInfoModel
import javax.inject.Inject

@AndroidEntryPoint
internal class ContactInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewContactInfoBinding.inflate(context.layoutInflater, this)

    private lateinit var headerController: ContactHeaderController
    private lateinit var infoAdapter: ContactInfoAdapter

    private var adapterItems by observeChanges<List<Item<*, NoDependencies>>>(emptyList()) { _, newItems ->
        infoAdapter.submitList(newItems)
    }

    var uiState by observeChanges<ContactInfoUiState>(ContactInfoUiState.Empty) { _, newState ->
        handleUiStateChange(newState)
    }

    var currentContact: Contact? = null

    @Inject
    lateinit var stringProvider: StringProvider

    var onGalleryClicked: ((Int) -> Unit)? = null
    var onBackButtonClicked: (() -> Unit)? = null
    var onImageClicked: (() -> Unit)? = null
    var onChangeImageClicked: (() -> Unit)? = null
    var onLikeButtonClicked: (() -> Unit)? = null
    var onAddGalleryImagesClicked: (() -> Unit)? = null

    init {
        initContactHeaderController(context)
        initRecyclerView(context)
        initDefaults()
    }

    private fun initContactHeaderController(context: Context) {
        ContactHeaderController(context, currentContact, binding, stringProvider).apply {
            onGalleryClicked = {
                this@ContactInfoView.onGalleryClicked?.invoke(it)
            }
            onBackButtonClicked = {
                this@ContactInfoView.onBackButtonClicked?.invoke()
            }
            onCoverClicked = {
                this@ContactInfoView.onImageClicked?.invoke()
            }
            onLikeButtonClicked = {
                this@ContactInfoView.onLikeButtonClicked?.invoke()
            }
            onChangeImageClicked = {
                this@ContactInfoView.onChangeImageClicked?.invoke()
            }
            onAddGalleryClicked = {
                this@ContactInfoView.onAddGalleryImagesClicked?.invoke()
            }
        }.also { headerController = it }
    }

    private fun initRecyclerView(context: Context) = with(binding.recyclerView) {
        disableChangeAnimations()
        layoutManager = initLayoutManager(context)
        adapter = initAdapter(context)
        addItemDecoration(initItemDecorator())
    }

    private fun initAdapter(context: Context) = ContactInfoAdapter(context)
        .apply { listenerBinder = ::bindListener }
        .also { infoAdapter = it }

    private fun initDefaults() {
        uiState = uiState
    }

    private fun initItemDecorator() = SpacingItemDecorator(
        spacing = getDimensionPixelSize(R.dimen.contact_info_decorator_spacing),
        sideFlags = SpacingItemDecorator.SIDE_TOP
    )

    private fun initLayoutManager(context: Context) = object : LinearLayoutManager(context) {
        override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
            return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    private fun handleUiStateChange(newState: ContactInfoUiState) {
        when (newState) {
            is ContactInfoUiState.Empty -> onEmptyStateSelected()
            is ContactInfoUiState.Loading -> onLoadingStateSelected()
            is ContactInfoUiState.ErrorPermission -> onErrorPermissionSelected(newState.navigation)
            is ContactInfoUiState.Result -> onResultStateSelected(newState)
        }
    }

    private fun onEmptyStateSelected() {
        showInfoView()
        hideProgressBar()
        hideMainView()
    }

    private fun onLoadingStateSelected() {
        showProgressBar()
        hideInfoView()
        hideMainView()
    }

    private fun onResultStateSelected(uiState: ContactInfoUiState.Result) {
        bindModel(uiState.model)

        showMainView()
        hideInfoView()
        hideProgressBar()
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

    private fun showProgressBar() = with(binding.progressBar) {
        makeVisible()
    }

    private fun hideProgressBar() = with(binding.progressBar) {
        makeGone()
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
        headerController.bindModel(model.headerModel)
        adapterItems = model.toAdapterItems()
    }

    private fun ContactInfoModel.toAdapterItems(): List<Item<*, NoDependencies>> {
        return buildList {
            // TODO add items to info list
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        headerController.onAttachedToWindow()
    }

    private fun bindListener(item: Item<*, NoDependencies>, viewHolder: RecyclerView.ViewHolder) {
        when (viewHolder) {
            // TODO create viewholder for earch item with listener invoke
        }
    }

    private fun onErrorPermissionSelected(navigation: () -> Unit) {
        binding.root.showSnackBar(
            getString(R.string.info_contact_photo_permission_error),
            getString(R.string.info_contact_settings_button),
            navigation
        )
    }
}
