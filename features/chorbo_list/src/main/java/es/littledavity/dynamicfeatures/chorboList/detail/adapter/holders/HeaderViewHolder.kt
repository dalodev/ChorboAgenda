package es.littledavity.dynamicfeatures.chorboList.detail.adapter.holders

import android.view.LayoutInflater
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.dynamicfeatures.chorboList.detail.ChorboDetailViewModel
import es.littledavity.dynamicfeatures.chorboList.detail.model.InfoItem
import es.littledavity.dynamicfeatures.chorbo_list.databinding.DetailItemHeaderBinding

class HeaderViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<DetailItemHeaderBinding>(
    binding = DetailItemHeaderBinding.inflate(inflater)
) {

    fun bind(viewModel: ChorboDetailViewModel, header: InfoItem) {
        binding.viewModel = viewModel
        binding.header = header as InfoItem.Header
        binding.executePendingBindings()
    }
}