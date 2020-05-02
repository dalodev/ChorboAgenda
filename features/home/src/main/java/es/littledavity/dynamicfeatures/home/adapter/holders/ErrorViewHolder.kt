package es.littledavity.dynamicfeatures.home.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.dynamicfeatures.home.HomeViewModel
import es.littledavity.dynamicfeatures.home.databinding.ListItemErrorBinding

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
    fun bind(viewModel: HomeViewModel) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}