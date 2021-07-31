/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.info.widgets

import android.content.Context
import es.littledavity.commons.ui.base.rv.AbstractRecyclerViewAdapter
import es.littledavity.commons.ui.base.rv.Item
import es.littledavity.commons.ui.base.rv.NoDependencies

internal class ContactInfoAdapter(
    context: Context
) : AbstractRecyclerViewAdapter<Item<*, NoDependencies>, NoDependencies>(context) {

    init {
        setHasStableIds(true)
    }
}
