package es.littledavity.features.info.widgets.details

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import es.littledavity.commons.ui.extensions.disableAnimations
import es.littledavity.commons.ui.extensions.getColor
import es.littledavity.commons.ui.extensions.getDimension
import es.littledavity.commons.ui.extensions.getDimensionPixelSize
import es.littledavity.commons.ui.extensions.layoutInflater
import es.littledavity.commons.ui.extensions.observeChanges
import es.littledavity.commons.ui.recyclerview.SpacingItemDecorator
import es.littledavity.domain.contacts.entities.Info
import es.littledavity.features.info.R
import es.littledavity.features.info.databinding.ViewContactInfoListItemBinding

internal class ContactInfoDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding = ViewContactInfoListItemBinding.inflate(context.layoutInflater, this)

    lateinit var adapter: ContactInfoDetailsAdapter

    private var adapterItems by observeChanges<List<ContactInfoDetailItem>>(emptyList()) { _, newItems ->
        adapter.submitList(newItems)
    }

    var items by observeChanges<List<Info>>(emptyList()) { _, newItems ->
        adapterItems = newItems.toAdapterItems()
    }

    var onInfoClicked: ((Info) -> Unit)? = null

    var currentDetails: MutableList<Info> = mutableListOf()

    init {
        initCard()
        initRecyclerView(context)
    }

    private fun initCard() {
        setBackgroundColor(getColor(R.color.contact_info_item_card_background_color))
        cardElevation = getDimension(R.dimen.contact_info_item_card_elevation)
    }

    private fun initRecyclerView(context: Context) = with(binding.recyclerView) {
        disableAnimations()

        layoutManager = initLayoutManager(context)
        adapter = initAdapter(context)
        addItemDecoration(initItemDecorator())
    }

    private fun initLayoutManager(context: Context) = object : LinearLayoutManager(context) {
        override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
            return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    private fun initAdapter(context: Context): ContactInfoDetailsAdapter {
        return ContactInfoDetailsAdapter(context)
            .apply { listenerBinder = ::bindListener }
            .also { adapter = it }
    }

    private fun bindListener(item: ContactInfoDetailItem, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is ContactInfoDetailItem.ViewHolder) {
            viewHolder.setOnVideoClickListener { onInfoClicked?.invoke(item.model) }
            viewHolder.setOnTitleTextChangedListener { item.model.title = it }
            viewHolder.setOnDescTextChangedListener { item.model.description = it }
        }
    }

    private fun List<Info>.toAdapterItems(): List<ContactInfoDetailItem> {
        return map(::ContactInfoDetailItem)
    }

    private fun initItemDecorator() = SpacingItemDecorator(
        spacing = getDimensionPixelSize(R.dimen.contact_info_item_container_margin),
        sideFlags = SpacingItemDecorator.SIDE_BOTTOM,
    )
}