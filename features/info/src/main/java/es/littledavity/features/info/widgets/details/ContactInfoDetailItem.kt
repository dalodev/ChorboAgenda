/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.info.widgets.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.rv.AbstractItem
import es.littledavity.commons.ui.base.rv.HasListeners
import es.littledavity.commons.ui.base.rv.NoDependencies
import es.littledavity.domain.contacts.entities.Info

internal class ContactInfoDetailItem(model: Info) : AbstractItem<
    Info,
    ContactInfoDetailItem.ViewHolder,
    NoDependencies
    >(model) {

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        dependencies: NoDependencies
    ) = ViewHolder(ContactInfoDetailView(parent.context))

    override fun performBinding(viewHolder: ViewHolder, dependencies: NoDependencies) {
        viewHolder.bind(model)
    }

    internal class ViewHolder(
        private val view: ContactInfoDetailView
    ) : RecyclerView.ViewHolder(view), HasListeners {

        fun bind(model: Info) = with(view) {
            title = model.title.toString()
            description = model.description.toString()
        }

        fun setOnVideoClickListener(onClick: () -> Unit) {
            view.onInfoClicked = onClick
        }

        fun setOnTitleTextChangedListener(onTitleTextChanged: (String) -> Unit) {
            view.onTitleTextChange = onTitleTextChanged
        }

        fun setOnDescTextChangedListener(onDescTextChanged: (String) -> Unit) {
            view.onDescriptionTextChanged = onDescTextChanged
        }

        fun setOnDeleteItemListener(onDeleteItem: () -> Unit) {
            view.onDeleteClicked = onDeleteItem
        }
    }
}
