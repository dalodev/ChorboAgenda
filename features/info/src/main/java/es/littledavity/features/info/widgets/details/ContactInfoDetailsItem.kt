package es.littledavity.features.info.widgets.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.base.rv.AbstractItem
import es.littledavity.commons.ui.base.rv.HasListeners
import es.littledavity.commons.ui.base.rv.NoDependencies
import es.littledavity.domain.contacts.entities.Info

internal class ContactInfoDetailsItem(model: MutableList<Info>) : AbstractItem<
        MutableList<Info>,
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
        viewHolder.setOnDetailDeleteListener {
            model.removeAt(it)
        }
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

        fun setOnDetailDeleteListener(onDelete: ((Int) -> Unit)? = null) {
            view.onItemDeleted = { onDelete?.invoke(bindingAdapterPosition) }
        }

        fun setOnEmptyListListener(emptyList: ((Boolean) -> Unit)? = null) {
            view.onEmptyList = { emptyList?.invoke(view.items.isEmpty()) }
        }
    }
}