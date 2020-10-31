package es.littledavity.dynamicfeatures.chorboList.detail.adapter.holders

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.dynamicfeatures.chorboList.detail.ChorboDetailViewModel
import es.littledavity.dynamicfeatures.chorboList.detail.model.ChorboDetailContent
import es.littledavity.dynamicfeatures.chorbo_list.R
import es.littledavity.dynamicfeatures.chorbo_list.databinding.DetailItemChorboBinding

/**
 * Class describes chorbo view and metadata about its place within the [RecyclerView].
 *
 * @see BaseViewHolder
 */
class DetailChorboViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<DetailItemChorboBinding>(
    binding = DetailItemChorboBinding.inflate(inflater)
) {

    /**
     * Bind view data binding variables.
     *
     * @param item detail chorbo item
     */
    fun bind(item: ChorboDetailContent.Content?) {
        binding.iconItem = when (item?.sectionContent) {
            ChorboDetailContent.SectionContent.LOCATION -> R.drawable.ic_location
            ChorboDetailContent.SectionContent.WHATSAPP -> R.drawable.ic_whatsapp
            ChorboDetailContent.SectionContent.INSTAGRAM -> R.drawable.ic_instagram
            else -> View.NO_ID //TODO change by error view
        }
        binding.content = item?.content
        binding.executePendingBindings()

    }
}