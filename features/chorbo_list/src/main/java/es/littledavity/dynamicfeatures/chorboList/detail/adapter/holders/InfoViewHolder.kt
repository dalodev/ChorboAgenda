package es.littledavity.dynamicfeatures.chorboList.detail.adapter.holders

import android.view.LayoutInflater
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.dynamicfeatures.chorboList.detail.ChorboDetailViewModel
import es.littledavity.dynamicfeatures.chorboList.detail.model.InfoData
import es.littledavity.dynamicfeatures.chorboList.detail.model.InfoItem
import es.littledavity.dynamicfeatures.chorbo_list.databinding.DetailItemInfoBinding

class InfoViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<DetailItemInfoBinding>(
    binding = DetailItemInfoBinding.inflate(inflater)
) {

    fun bind(viewModel: ChorboDetailViewModel, info: InfoItem) {
        binding.viewModel = viewModel
        binding.info = info as InfoItem.Item
        binding.value = info.info.value as InfoData.Simple
        binding.executePendingBindings()
    }
}