package es.littledavity.dynamicfeatures.chorboList.detail.adapter.holders

import android.net.Uri
import android.view.LayoutInflater
import es.littledavity.commons.ui.base.BaseViewHolder
import es.littledavity.dynamicfeatures.chorboList.detail.ChorboDetailViewModel
import es.littledavity.dynamicfeatures.chorbo_list.databinding.PreviewItemImageBinding

class ImagePreviewViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<PreviewItemImageBinding>(
    binding = PreviewItemImageBinding.inflate(inflater)
) {

    fun bind(viewModel: ChorboDetailViewModel?, uri: Uri) {
        binding.uri = uri
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}