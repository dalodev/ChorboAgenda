package es.littledavity.features.edit.widgets

import android.content.Context
import es.littledavity.commons.ui.base.rv.AbstractRecyclerViewAdapter
import es.littledavity.commons.ui.base.rv.Item
import es.littledavity.commons.ui.base.rv.NoDependencies

internal class EditContactInfoAdapter(
    context: Context
) : AbstractRecyclerViewAdapter<Item<*, NoDependencies>, NoDependencies>(context) {
    init {
        setHasStableIds(true)
    }
}