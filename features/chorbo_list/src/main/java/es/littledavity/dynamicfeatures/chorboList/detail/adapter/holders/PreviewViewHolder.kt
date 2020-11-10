package es.littledavity.dynamicfeatures.chorboList.detail.adapter.holders

import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearSnapHelper
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.dynamicfeatures.chorboList.detail.ChorboDetailViewModel
import es.littledavity.dynamicfeatures.chorboList.detail.adapter.PreviewImagesAdapter
import es.littledavity.dynamicfeatures.chorboList.detail.model.InfoData
import es.littledavity.dynamicfeatures.chorboList.detail.model.InfoItem
import es.littledavity.dynamicfeatures.chorbo_list.databinding.DetailItemPreviewBinding
import javax.inject.Inject

class PreviewViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<DetailItemPreviewBinding>(
    binding = DetailItemPreviewBinding.inflate(inflater)
) {
    private val adapter: PreviewImagesAdapter by lazy { PreviewImagesAdapter() }

    fun bind(viewModel: ChorboDetailViewModel, info: InfoItem) {
        val data = (info as InfoItem.Item).info.value as InfoData.Preview
        binding.viewModel = viewModel
        binding.preview = data
        LinearSnapHelper().attachToRecyclerView(binding.images)
        adapter.viewModel = viewModel
        binding.images.adapter = adapter
        adapter.submitList(data.list.toMutableList())
        binding.executePendingBindings()
    }
}