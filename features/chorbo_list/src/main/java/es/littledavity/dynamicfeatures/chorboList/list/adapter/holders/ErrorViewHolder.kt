/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.list.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.dynamicfeatures.chorboList.list.ChorboListViewModel
import es.littledavity.dynamicfeatures.chorbo_list.databinding.ListItemErrorBinding

/**
 * Class describes chorbos error view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class ErrorViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemErrorBinding>(
    binding = ListItemErrorBinding.inflate(inflater)
) {

    /**
     * Bind view data binding variables.
     *
     * @param viewModel chorbo list view model.
     */
    fun bind(viewModel: ChorboListViewModel) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}
