package es.littledavity.dynamicfeatures.chorboList.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.dynamicfeatures.chorboList.detail.ChorboDetailViewModel
import es.littledavity.dynamicfeatures.chorboList.detail.adapter.holders.PreviewImagesViewHolder
import javax.inject.Inject

class PreviewImagesAdapter @Inject constructor(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel: ChorboDetailViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var state: PreviewImagesAdapterState = PreviewImagesAdapterState.Added

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when (ItemView.valueOf(viewType)) {
            ItemView.DETAIL -> PreviewImagesViewHolder(LayoutInflater.from(parent.context))
            else -> PreviewImagesViewHolder(LayoutInflater.from(parent.context))
        }

    override fun getItemCount() = 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    /**
     * Enum class containing the different type of cell view, with the configuration.
     */
    internal enum class ItemView(val type: Int, val span: Int) {
        DETAIL(type = 0, span = 2),
        LOADING(type = 1, span = 2),
        ERROR(type = 2, span = 2);

        companion object {
            fun valueOf(type: Int): ItemView? = values().first { it.type == type }
        }
    }
}