/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.list.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.dynamicfeatures.chorboList.list.ChorboListViewModel
import es.littledavity.dynamicfeatures.chorboList.list.model.ChorboItem
import es.littledavity.dynamicfeatures.chorbo_list.databinding.ListItemChorboBinding

/**
 * Class describes chorbo view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class ChorboViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemChorboBinding>(
    binding = ListItemChorboBinding.inflate(inflater)
) {

    /**
     * Bind view data binding variables.
     *
     * @param viewModel chorbo list view model.
     * @param item chorbo list item.
     */
    fun bind(viewModel: ChorboListViewModel, item: ChorboItem) {
        binding.viewModel = viewModel
        binding.chorbo = item
        binding.position = adapterPosition
        binding.executePendingBindings()
    }
}
