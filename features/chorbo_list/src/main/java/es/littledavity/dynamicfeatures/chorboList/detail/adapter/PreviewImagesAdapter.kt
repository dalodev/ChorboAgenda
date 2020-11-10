package es.littledavity.dynamicfeatures.chorboList.detail.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.BaseListAdapter
import es.littledavity.dynamicfeatures.chorboList.detail.ChorboDetailViewModel
import es.littledavity.dynamicfeatures.chorboList.detail.adapter.holders.ImagePreviewViewHolder
import java.io.File
import javax.inject.Inject

class PreviewImagesAdapter : BaseListAdapter<Uri>(
    itemsSame = { old, new -> old == new },
    contentsSame = { old, new -> old == new }
) {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var viewModel: ChorboDetailViewModel? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder = ImagePreviewViewHolder(inflater)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ImagePreviewViewHolder)?.bind(viewModel, getItem((position)))
    }

}