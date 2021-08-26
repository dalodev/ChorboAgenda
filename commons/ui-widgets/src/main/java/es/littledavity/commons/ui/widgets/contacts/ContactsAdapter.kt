/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.widgets.contacts

import android.content.Context
import es.littledavity.commons.ui.base.rv.AbstractRecyclerViewAdapter
import es.littledavity.commons.ui.base.rv.NoDependencies

internal class ContactsAdapter(
    context: Context
) : AbstractRecyclerViewAdapter<ContactItem, NoDependencies>(context) {

    private val headers: List<String>  by lazy { currentList.map { it.model.name.first().toString() } }
    fun getHeaderForCurrentPosition(position: Int) = if (position in headers.indices) {
        headers[position]
    } else {
        ""
    }
}
