/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.extensions

import android.widget.ListView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.recyclerview.RecyclerViewScrollListener

/**
 * Get implementation manger that lays out items in a grid.
 *
 * @return Recycle view grid layout manager if configured, otherwise null.
 */
val RecyclerView.gridLayoutManager: GridLayoutManager?
    get() = layoutManager as? GridLayoutManager

/**
 * Get implementation manager which provides similar functionality [ListView].
 *
 * @return Recycle view linear layout manager if configured, otherwise null.
 */
val RecyclerView.linearLayoutManager: LinearLayoutManager?
    get() = layoutManager as? LinearLayoutManager

inline fun RecyclerView.addOnScrollListener(
    shouldNotifyOnReachingEndsRepeatedly: Boolean = false,
    crossinline onScrolledUpwards: (recyclerView: RecyclerView, deltaY: Int) -> Unit = { _, _ -> },
    crossinline onScrolledDownwards: (recyclerView: RecyclerView, deltaY: Int) -> Unit = { _, _ -> },
    crossinline onTopReached: (recyclerView: RecyclerView, reachedCompletely: Boolean) -> Unit = { _, _ -> },
    crossinline onMiddleReached: (recyclerView: RecyclerView, direction: RecyclerViewScrollListener.Direction) -> Unit = { _, _ -> },
    crossinline onBottomReached: (recyclerView: RecyclerView, reachedCompletely: Boolean) -> Unit = { _, _ -> }
): RecyclerView.OnScrollListener {
    val stateListener = object : RecyclerViewScrollListener.StateListener {

        override fun onScrolledUpwards(recyclerView: RecyclerView, deltaY: Int) = onScrolledUpwards(recyclerView, deltaY)
        override fun onScrolledDownwards(recyclerView: RecyclerView, deltaY: Int) = onScrolledDownwards(recyclerView, deltaY)
        override fun onTopReached(recyclerView: RecyclerView, reachedCompletely: Boolean) = onTopReached(recyclerView, reachedCompletely)
        override fun onMiddleReached(recyclerView: RecyclerView, direction: RecyclerViewScrollListener.Direction) = onMiddleReached(recyclerView, direction)
        override fun onBottomReached(recyclerView: RecyclerView, reachedCompletely: Boolean) = onBottomReached(recyclerView, reachedCompletely)
    }

    return RecyclerViewScrollListener(
        stateListener = stateListener,
        shouldNotifyOnReachingEndsRepeatedly = shouldNotifyOnReachingEndsRepeatedly
    )
        .also(::addOnScrollListener)
}

fun RecyclerView.recreateItemViews() {
    val savedAdapter = adapter

    adapter = null
    adapter = savedAdapter
}

fun RecyclerView.disableScrollbars() {
    isVerticalScrollBarEnabled = false
    isHorizontalScrollBarEnabled = false
}

fun RecyclerView.disableOverScrollMode() {
    overScrollMode = RecyclerView.OVER_SCROLL_NEVER
}

fun RecyclerView.disableAddAnimations() {
    itemAnimator?.addDuration = 0L
}

fun RecyclerView.disableRemovalAnimations() {
    itemAnimator?.removeDuration = 0L
}

fun RecyclerView.disableMoveAnimations() {
    itemAnimator?.moveDuration = 0L
}

fun RecyclerView.disableUpdateAnimations() {
    itemAnimator?.changeDuration = 0L
}

fun RecyclerView.disableChangeAnimations() {
    (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
}

fun RecyclerView.disableAnimations() {
    if (itemAnimator != null) {
        itemAnimator = null
    }
}
