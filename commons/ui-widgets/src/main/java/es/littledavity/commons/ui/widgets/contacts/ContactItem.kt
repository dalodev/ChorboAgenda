/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.widgets.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.rv.AbstractItem
import es.littledavity.commons.ui.base.rv.HasListeners
import es.littledavity.commons.ui.base.rv.HasUniqueIdentifier
import es.littledavity.commons.ui.base.rv.NoDependencies

internal class ContactItem(model: ContactModel) : AbstractItem<
        ContactModel,
        ContactItem.ViewHolder,
        NoDependencies>(model), HasUniqueIdentifier<Int> {

    override val uniqueIdentifier: Int
        get() = model.id

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        dependencies: NoDependencies
    ) = ViewHolder(ContactView(parent.context))

    override fun performBinding(viewHolder: ViewHolder, dependencies: NoDependencies) {
        viewHolder.bind(model)
    }

    internal class ViewHolder(
        private val view: ContactView
    ) : RecyclerView.ViewHolder(view), HasListeners {

        fun bind(model: ContactModel) = with(view) {
            image = model.coverImageUrl
            name = model.name
            phone = model.phone
        }

        fun setOnGameClickListener(onClick: () -> Unit) {
            view.onContactClicked = onClick
        }
    }
}
