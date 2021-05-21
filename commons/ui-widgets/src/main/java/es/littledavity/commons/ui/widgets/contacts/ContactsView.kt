package es.littledavity.commons.ui.widgets.contacts

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paulrybitskyi.commons.ktx.getColor
import com.paulrybitskyi.commons.ktx.getDimensionPixelSize
import com.paulrybitskyi.commons.ktx.getString
import com.paulrybitskyi.commons.ktx.layoutInflater
import com.paulrybitskyi.commons.ktx.makeGone
import com.paulrybitskyi.commons.ktx.makeInvisible
import com.paulrybitskyi.commons.ktx.makeVisible
import com.paulrybitskyi.commons.recyclerview.decorators.spacing.SpacingItemDecorator
import com.paulrybitskyi.commons.recyclerview.decorators.spacing.policies.LastItemExclusionPolicy
import com.paulrybitskyi.commons.recyclerview.utils.addOnScrollListener
import com.paulrybitskyi.commons.recyclerview.utils.disableChangeAnimations
import com.paulrybitskyi.commons.utils.observeChanges
import es.littledavity.commons.ui.extensions.disableAfterAnimationEnds
import es.littledavity.commons.ui.extensions.fadeIn
import es.littledavity.commons.ui.extensions.resetAnimation
import es.littledavity.commons.ui.widgets.R
import es.littledavity.commons.ui.widgets.databinding.ViewContactsBinding

class ContactsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewContactsBinding.inflate(context.layoutInflater, this)

    private lateinit var adapter: ContactsAdapter

    private var adapterItems by observeChanges<List<ContactItem>>(emptyList()) { _, newItems ->
        adapter.submitList(newItems) {
            binding.recyclerView.invalidateItemDecorations()
        }
    }

    var uiState by observeChanges(createDefaultUiState()) { _, newState ->
        handleUiStateChange(newState)
    }

    var onContactClicked: ((ContactModel) -> Unit)? = null
    var onBottomReached: (() -> Unit)? = null

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

    private fun initLayoutManager(context: Context): LinearLayoutManager {
        return object : LinearLayoutManager(context) {

            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            }

        }
    }

    private fun initAdapter(context: Context): ContactsAdapter {
        return ContactsAdapter(context)
            .apply { listenerBinder = ::bindListener }
            .also { adapter = it }
    }

    private fun bindListener(item: ContactItem, viewHolder: RecyclerView.ViewHolder) {
        if(viewHolder is ContactItem.ViewHolder) {
            viewHolder.setOnGameClickListener { onContactClicked?.invoke(item.model) }
        }
    }

    private fun initItemDecorator(): SpacingItemDecorator {
        return SpacingItemDecorator(
            spacing = getDimensionPixelSize(R.dimen.contacts_decorator_spacing),
            sideFlags = SpacingItemDecorator.SIDE_BOTTOM,
            itemExclusionPolicy = LastItemExclusionPolicy()
        )
    }

    private fun initDefaults() {
        uiState = uiState
    }

    private fun createDefaultUiState(): ContactsUiState {
        return ContactsUiState.Empty(
            iconId = R.drawable.gamepad_variant_outline,
            title = getString(R.string.contacts_info_view_title)
        )
    }

    fun clearItems() {
        adapterItems = emptyList()
    }

    private fun List<ContactModel>.toAdapterItems(): List<ContactItem> {
        return map(::ContactItem)
    }

    private fun handleUiStateChange(newState: ContactsUiState) {
        when(newState) {
            is ContactsUiState.Empty -> onEmptyUiStateSelected(newState)
            is ContactsUiState.Loading -> onLoadingStateSelected()
            is ContactsUiState.Result -> onResultUiStateSelected(newState)
        }
    }

    private fun onEmptyUiStateSelected(uiState: ContactsUiState.Empty) {
        hideLoadingIndicators()
        hideRecyclerView()
    }

    private fun onLoadingStateSelected() {
        if(adapterItems.isNotEmpty()) {
            showSwipeRefresh()
        } else {
            showProgressBar()
            hideRecyclerView()
        }
    }

    private fun onResultUiStateSelected(uiState: ContactsUiState.Result) {
        adapterItems = uiState.items.toAdapterItems()

        showRecyclerView()
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
        if(isVisible) return

        makeVisible()
        fadeIn()
    }

    private fun hideRecyclerView() = with(binding.recyclerView) {
        makeInvisible()
        resetAnimation()
    }
}