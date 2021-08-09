/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.info.widgets

import android.content.Context
import es.littledavity.commons.ui.base.rv.AbstractRecyclerViewAdapter
import es.littledavity.commons.ui.base.rv.Item
import es.littledavity.commons.ui.base.rv.NoDependencies
import es.littledavity.domain.contacts.entities.Info
import es.littledavity.features.info.widgets.details.ContactInfoDetailsItem

internal class ContactInfoAdapter(
    context: Context
) : AbstractRecyclerViewAdapter<Item<*, NoDependencies>, NoDependencies>(context) {

    init {
        setHasStableIds(true)
    }

    fun getData(): MutableList<Info>? = (currentList.find {
        it is ContactInfoDetailsItem
    } as? ContactInfoDetailsItem)?.model?.filter {
        it.title?.isNotBlank() ?: false || it.description?.isNotBlank() ?: false
    }?.toMutableList()
}
