package es.littledavity.dynamicfeatures.chorboList.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.dynamicfeatures.chorboList.detail.ChorboDetailViewModel
import es.littledavity.dynamicfeatures.chorboList.detail.adapter.holders.DetailChorboHeaderViewHolder
import es.littledavity.dynamicfeatures.chorboList.detail.adapter.holders.DetailChorboViewHolder
import es.littledavity.dynamicfeatures.chorboList.detail.model.ChorboDetailContent
import javax.inject.Inject

class DetailChorboAdapter @Inject constructor(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel: ChorboDetailViewModel,
    private val items: List<ChorboDetailContent> = viewModel.createListWithHeader()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var state: DetailChorboAdapterState = DetailChorboAdapterState.Added

    override fun getItemViewType(position: Int) =
        when (getItemView(position)) {
            ItemView.ITEM -> 0
            ItemView.HEADER -> 1
            ItemView.LOADING -> 2
            ItemView.ERROR -> 3
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when (ItemView.valueOf(viewType)) {
            ItemView.ITEM -> DetailChorboViewHolder(LayoutInflater.from(parent.context))
            ItemView.HEADER -> DetailChorboHeaderViewHolder(LayoutInflater.from(parent.context))
            else -> DetailChorboViewHolder(LayoutInflater.from(parent.context))
        }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (getItemView(position)) {
            ItemView.ITEM -> (holder as? DetailChorboViewHolder)?.bind(item as? ChorboDetailContent.Content)
            ItemView.HEADER -> (holder as? DetailChorboHeaderViewHolder)?.bind(item)
            else -> Unit
        }
    }

    private fun getItemView(position: Int) =
        if (state.hasExtraRow && position == itemCount - 1) {
            if (state.isAddError()) {
                ItemView.ERROR
            } else {
                ItemView.LOADING
            }
        } else {
            when (items[position]) {
                is ChorboDetailContent.Section -> ItemView.HEADER
                is ChorboDetailContent.Content -> ItemView.ITEM
            }
        }

    /**
     * Enum class containing the different type of cell view, with the configuration.
     */
    internal enum class ItemView(val type: Int) {
        ITEM(type = 0),
        HEADER(type = 1),
        LOADING(type = 2),
        ERROR(type = 3);

        companion object {
            fun valueOf(type: Int): ItemView? = values().first { it.type == type }
        }
    }
}