package es.littledavity.dynamicfeatures.home.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.dynamicfeatures.home.HomeViewModel
import es.littledavity.dynamicfeatures.home.databinding.ListItemChorboBinding
import es.littledavity.dynamicfeatures.home.model.ChorboItem

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
    fun bind(viewModel: HomeViewModel, item: ChorboItem) {
        binding.viewModel = viewModel
        binding.chorbo = item
        binding.executePendingBindings()
    }
}