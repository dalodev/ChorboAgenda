package es.littledavity.dynamicfeatures.chorboList.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.BaseListAdapter
import es.littledavity.dynamicfeatures.chorboList.detail.ChorboDetailViewModel
import es.littledavity.dynamicfeatures.chorboList.detail.adapter.holders.HeaderViewHolder
import es.littledavity.dynamicfeatures.chorboList.detail.adapter.holders.InfoViewHolder
import es.littledavity.dynamicfeatures.chorboList.detail.adapter.holders.PreviewViewHolder
import es.littledavity.dynamicfeatures.chorboList.detail.model.Info
import es.littledavity.dynamicfeatures.chorboList.detail.model.InfoData
import es.littledavity.dynamicfeatures.chorboList.detail.model.InfoItem
import es.littledavity.dynamicfeatures.chorboList.list.adapter.holders.ErrorViewHolder
import es.littledavity.dynamicfeatures.chorboList.list.adapter.holders.LoadingViewHolder
import javax.inject.Inject

internal enum class ItemView(val type: Int) {
    HEADER(type = 0),
    INFO(type = 1),
    PREVIEW(type = 2),
    LOADING(type = 3),
    ERROR(type = 4);

    companion object {
        fun valueOf(type: Int): ItemView? = values().first { it.type == type }
    }
}

class ChorboDetailAdapter @Inject constructor(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel: ChorboDetailViewModel
) : BaseListAdapter<InfoItem>(
    itemsSame = { old, new -> old == new },
    contentsSame = { old, new -> old == new }
) {

    private var state: ChorboDetailAdapterState = ChorboDetailAdapterState.Added

    override fun getItemViewType(position: Int) = getItemView(position).type

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when (ItemView.valueOf(viewType)) {
            ItemView.HEADER -> HeaderViewHolder(inflater)
            ItemView.INFO -> InfoViewHolder(inflater)
            ItemView.PREVIEW -> PreviewViewHolder(inflater)
            ItemView.LOADING -> LoadingViewHolder(inflater)
            else -> ErrorViewHolder(inflater)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem((position))
        when (getItemView(position)) {
            ItemView.HEADER -> (holder as? HeaderViewHolder)?.bind(viewModel, item)
            ItemView.INFO -> (holder as? InfoViewHolder)?.bind(viewModel, item)
            ItemView.PREVIEW -> (holder as? PreviewViewHolder)?.bind(viewModel, item)
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
            when (val info = getItem(position)) {
                is InfoItem.Header -> ItemView.HEADER
                is InfoItem.Item -> when (info.info.value) {
                    is InfoData.Simple -> ItemView.INFO
                    is InfoData.Preview -> ItemView.PREVIEW
                }
            }
        }

    fun submitSectionedList(list: List<Info>) {
        val groupedList = list.groupBy { it.section }
        val myList = ArrayList<InfoItem>()
        for (i in groupedList.keys) {
            myList.add(InfoItem.Header(i))
            for (v in groupedList.getValue(i)) {
                myList.add(InfoItem.Item(v))
            }
        }
        submitList(myList)
    }
}