/*
 * Copyright 2021 dalodev
 */
package es.littledavity.commons.ui.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LastItemExclusionPolicy : SpacingItemDecorator.ItemExclusionPolicy {

    override fun shouldExclude(view: View, parent: RecyclerView): Boolean {
        return parent.getChildAdapterPosition(view) == (parent.adapter?.itemCount ?: 0) - 1
    }
}
