/*
 * Copyright 2021 dalodev
 */
package es.littledavity.commons.ui.widgets.contacts

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.extensions.addOnScrollListener
import es.littledavity.commons.ui.extensions.disableAfterAnimationEnds
import es.littledavity.commons.ui.extensions.disableChangeAnimations
import es.littledavity.commons.ui.extensions.fadeIn
import es.littledavity.commons.ui.extensions.getColor
import es.littledavity.commons.ui.extensions.getDimensionPixelSize
import es.littledavity.commons.ui.extensions.getDrawable
import es.littledavity.commons.ui.extensions.getString
import es.littledavity.commons.ui.extensions.layoutInflater
import es.littledavity.commons.ui.extensions.makeGone
import es.littledavity.commons.ui.extensions.makeInvisible
import es.littledavity.commons.ui.extensions.makeVisible
import es.littledavity.commons.ui.extensions.observeChanges
import es.littledavity.commons.ui.extensions.resetAnimation
import es.littledavity.commons.ui.extensions.showSnackBar
import es.littledavity.commons.ui.recyclerview.LastItemExclusionPolicy
import es.littledavity.commons.ui.recyclerview.SpacingItemDecorator
import es.littledavity.commons.ui.widgets.R
import es.littledavity.commons.ui.widgets.SwipeToDeleteCallback
import es.littledavity.commons.ui.widgets.databinding.ViewContactsBinding

class ContactsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewContactsBinding.inflate(context.layoutInflater, this)

    private lateinit var adapter: ContactsAdapter

    private val swipeToDelete by lazy { SwipeToDeleteCallback(context, ::onSwipe) }

    private var itemToDelete: ContactItem? = null

    private var currentAdapterItems = mutableListOf<ContactItem>()

    private var adapterItems by observeChanges<List<ContactItem>>(emptyList()) { _, newItems ->
        adapter.submitList(newItems) {
            currentAdapterItems = newItems.toMutableList()
            binding.recyclerView.invalidateItemDecorations()
        }
    }

    var uiState by observeChanges(createDefaultUiState()) { _, newState ->
        handleUiStateChange(newState)
    }

    var onContactClicked: ((ContactModel) -> Unit)? = null
    var onBottomReached: (() -> Unit)? = null
    var onRemoveContact: ((Int?) -> Unit)? = null

    init {
        initSwipeRefreshLayout()
        initRecyclerView(context)
        initDefaults()
    }

    private fun initSwipeRefreshLayout() = with(binding.swipeRefreshLayout) {
        setColorSchemeColors(getColor(R.color.contacts_swipe_refresh_color))
        hideSwipeRefresh()
    }

    private fun initRecyclerView(context: Context) = with(binding.recyclerView) {
        disableChangeAnimations()
        layoutManager = initLayoutManager(context)
        adapter = initAdapter(context)
        addItemDecoration(initItemDecorator())
        addOnScrollListener(onBottomReached = { _, _ -> onBottomReached?.invoke() })
    }

    private fun initLayoutManager(context: Context) = object : LinearLayoutManager(context) {
        override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
            return RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }
    }

    private fun initAdapter(context: Context) = ContactsAdapter(context).apply {
        listenerBinder = ::bindListener
        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }.also { adapter = it }

    private fun onSwipe(position: Int) {
        swipeToDelete.canSwipe = false
        itemToDelete = adapterItems[position]
        adapterItems = currentAdapterItems.apply { removeAt(position) }
        showSnackBar(
            getString(R.string.contacts_delete_snackbar_message),
            getString(R.string.contacts_delete_snackbar_action_message),
            {
                itemToDelete?.let { adapterItems = currentAdapterItems.apply { add(position, it) } }
                swipeToDelete.canSwipe = true
            },
            {
                onRemoveContact?.invoke(itemToDelete?.model?.id)
                swipeToDelete.canSwipe = true
            }
        )
    }

    private fun bindListener(item: ContactItem, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is ContactItem.ViewHolder) {
            viewHolder.setOnContactClickListener { onContactClicked?.invoke(item.model) }
        }
    }

    private fun initItemDecorator() = SpacingItemDecorator(
        spacing = getDimensionPixelSize(R.dimen.contacts_decorator_spacing),
        sideFlags = SpacingItemDecorator.SIDE_BOTTOM,
        itemExclusionPolicy = LastItemExclusionPolicy()
    )

    private fun initDefaults() {
        uiState = uiState
    }

    private fun createDefaultUiState(): ContactsUiState = ContactsUiState.Empty(
        iconId = R.drawable.contact_outline,
        title = getString(R.string.contacts_info_view_title)
    )

    fun clearItems() {
        adapterItems = emptyList()
    }

    private fun List<ContactModel>.toAdapterItems() = map(::ContactItem)

    private fun handleUiStateChange(newState: ContactsUiState) {
        when (newState) {
            is ContactsUiState.Empty -> onEmptyUiStateSelected(newState)
            is ContactsUiState.Loading -> onLoadingStateSelected()
            is ContactsUiState.Result -> onResultUiStateSelected(newState)
        }
    }

    private fun onEmptyUiStateSelected(uiState: ContactsUiState.Empty) {
        showInfoView(uiState)
        hideLoadingIndicators()
        hideRecyclerView()
    }

    private fun showInfoView(uiState: ContactsUiState.Empty) = with(binding.infoView) {
        icon = getDrawable(uiState.iconId)
        titleText = uiState.title

        if (isVisible) return
        makeVisible()
        fadeIn()
    }

    private fun hideInfoView() = with(binding.infoView) {
        makeGone()
        resetAnimation()
    }

    private fun onLoadingStateSelected() {
        if (adapterItems.isNotEmpty()) {
            showSwipeRefresh()
        } else {
            showProgressBar()
            hideInfoView()
            hideRecyclerView()
        }
    }

    private fun onResultUiStateSelected(uiState: ContactsUiState.Result) {
        adapterItems = uiState.items.toAdapterItems()

        showRecyclerView()
        hideInfoView()
        hideLoadingIndicators()
    }

    private fun showProgressBar() = with(binding.progressBar) {
        makeVisible()
        fadeIn()
    }

    private fun hideProgressBar() = with(binding.progressBar) {
        makeGone()
        resetAnimation()
    }

    private fun showSwipeRefresh() = with(binding.swipeRefreshLayout) {
        isEnabled = true
        isRefreshing = true
    }

    private fun hideSwipeRefresh() = with(binding.swipeRefreshLayout) {
        isRefreshing = false
        disableAfterAnimationEnds()
    }

    private fun hideLoadingIndicators() {
        hideProgressBar()
        hideSwipeRefresh()
    }

    private fun showRecyclerView() = with(binding.recyclerView) {
        if (isVisible) return

        makeVisible()
        fadeIn()
    }

    private fun hideRecyclerView() = with(binding.recyclerView) {
        makeInvisible()
        resetAnimation()
    }
}
