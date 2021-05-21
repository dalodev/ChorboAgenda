package es.littledavity.commons.ui.widgets.contacts

import android.content.Context
import es.littledavity.commons.ui.base.rv.AbstractRecyclerViewAdapter
import es.littledavity.commons.ui.base.rv.NoDependencies

internal class ContactsAdapter(
    context: Context
) : AbstractRecyclerViewAdapter<ContactItem, NoDependencies>(context)