/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.info.widgets.header.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.rv.AbstractItem
import es.littledavity.commons.ui.base.rv.HasListeners
import es.littledavity.commons.ui.base.rv.NoDependencies
import es.littledavity.commons.ui.extensions.onClick

internal class ContactGalleryItem(
    model: ContactGalleryModel
) : AbstractItem<ContactGalleryModel, ContactGalleryItem.ViewHolder, NoDependencies>(model) {

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        dependencies: NoDependencies
    ) = ViewHolder(
        ContactGalleryImageView(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }
    )

    override fun performBinding(viewHolder: ViewHolder, dependencies: NoDependencies) {
        viewHolder.bind(model)
    }

    internal class ViewHolder(
        private val view: ContactGalleryImageView
    ) : RecyclerView.ViewHolder(view), HasListeners {

        fun bind(model: ContactGalleryModel) {
            view.model = model
        }

        fun setOnGalleryClickListener(onClick: () -> Unit) {
            view.onClick { onClick() }
        }
    }
}
