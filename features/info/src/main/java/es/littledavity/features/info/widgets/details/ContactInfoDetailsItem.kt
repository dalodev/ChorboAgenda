package es.littledavity.features.info.widgets.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.rv.AbstractItem
import es.littledavity.commons.ui.base.rv.HasListeners
import es.littledavity.commons.ui.base.rv.NoDependencies
import es.littledavity.domain.contacts.entities.Info

internal class ContactInfoDetailsItem(model: List<Info>) : AbstractItem<
        List<Info>,
        ContactInfoDetailsItem.ViewHolder,
        NoDependencies
        >(model) {

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        dependencies: NoDependencies
    ) = ViewHolder(ContactInfoDetailsView(parent.context))

    override fun performBinding(viewHolder: ViewHolder, dependencies: NoDependencies) {
        viewHolder.bind(model)
    }

    internal class ViewHolder(
        val view: ContactInfoDetailsView
    ) : RecyclerView.ViewHolder(view), HasListeners {

        fun bind(model: List<Info>) {
            view.items = model
        }

        fun setOnDetailClickListener(onClick: (Info) -> Unit) {
            view.onInfoClicked = { onClick(it) }
        }
    }
}