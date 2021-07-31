/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.image.viewer.widgets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.rv.AbstractItem
import es.littledavity.commons.ui.base.rv.NoDependencies

internal class ImageViewerItem(model: String) : AbstractItem<
        String,
        ImageViewerItem.ViewHolder,
        NoDependencies>(model) {

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        dependencies: NoDependencies
    ) = ViewHolder(
        ImageViewerItemView(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.MATCH_PARENT
            )
        }
    )

    override fun performBinding(viewHolder: ViewHolder, dependencies: NoDependencies) {
        viewHolder.bind(model)
    }

    internal class ViewHolder(
        private val view: ImageViewerItemView
    ) : RecyclerView.ViewHolder(view) {

        fun bind(model: String) {
            view.imageUrl = model
        }
    }
}
