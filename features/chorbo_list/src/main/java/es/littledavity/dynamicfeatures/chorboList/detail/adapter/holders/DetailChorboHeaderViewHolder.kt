package es.littledavity.dynamicfeatures.chorboList.detail.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.dynamicfeatures.chorboList.detail.ChorboDetailViewModel
import es.littledavity.dynamicfeatures.chorboList.detail.model.ChorboDetailContent
import es.littledavity.dynamicfeatures.chorbo_list.R
import es.littledavity.dynamicfeatures.chorbo_list.databinding.DetailChorboHeaderItemBinding

/**
 * Class describes chorbo view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class DetailChorboHeaderViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<DetailChorboHeaderItemBinding>(
    binding = DetailChorboHeaderItemBinding.inflate(inflater)
) {

    /**
     * Bind view data binding variables.
     *
     * @param item chorbo item
     */
    fun bind(item: ChorboDetailContent) {
        when (item.section) {
            ChorboDetailContent.ContentType.INFO -> binding.title =
                itemView.context.resources.getString(
                    R.string.info_section
                )
            ChorboDetailContent.ContentType.SOCIAL -> binding.title =
                itemView.context.resources.getString(
                    R.string.social_section
                )
        }
        binding.executePendingBindings()
    }

}