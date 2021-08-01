/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.extensions.containsBits

open class SpacingItemDecorator @JvmOverloads constructor(
    protected val spacing: Int,
    protected val sideFlags: Int,
    protected val itemExclusionPolicies: List<ItemExclusionPolicy> = listOf()
) : RecyclerView.ItemDecoration() {

    companion object {

        const val SIDE_LEFT = 0b0001
        const val SIDE_TOP = 0b0010
        const val SIDE_RIGHT = 0b0100
        const val SIDE_BOTTOM = 0b1000
        const val SIDE_ALL = 0b1111
    }

    constructor(
        spacing: Int,
        sideFlags: Int,
        itemExclusionPolicy: ItemExclusionPolicy
    ) : this(
        spacing = spacing,
        sideFlags = sideFlags,
        itemExclusionPolicies = listOf(itemExclusionPolicy)
    )

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        for (itemExclusionPolicy in itemExclusionPolicies) {
            if (itemExclusionPolicy.shouldExclude(view, parent)) {
                return
            }
        }

        if (sideFlags.containsBits(SIDE_LEFT) && shouldAssignSpacing(view, parent)) {
            outRect.left = spacing
        }

        if (sideFlags.containsBits(SIDE_TOP) && shouldAssignSpacing(view, parent)) {
            outRect.top = spacing
        }

        if (sideFlags.containsBits(SIDE_RIGHT) && shouldAssignSpacing(view, parent)) {
            outRect.right = spacing
        }

        if (sideFlags.containsBits(SIDE_BOTTOM) && shouldAssignSpacing(view, parent)) {
            outRect.bottom = spacing
        }
    }

    open fun shouldAssignSpacing(view: View, parent: RecyclerView): Boolean = true

    interface ItemExclusionPolicy {

        fun shouldExclude(view: View, parent: RecyclerView): Boolean
    }
}
