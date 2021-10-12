/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.info.widgets.header.gallery

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import es.littledavity.commons.ui.extensions.observeChanges
import es.littledavity.commons.ui.extensions.recyclerView
import es.littledavity.commons.ui.extensions.registerOnPageChangeCallback

internal class ContactGalleryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ContactGalleryAdapter

    var isScrollingEnabled: Boolean
        set(value) {
            viewPager.isUserInputEnabled = value
        }
        get() = viewPager.isUserInputEnabled

    var isGalleryClickEnabled = true

    private var adapterItems by observeChanges<List<ContactGalleryItem>>(emptyList()) { _, newItems ->
        adapter.submitList(newItems) {
            viewPager.recyclerView?.invalidateItemDecorations()
            if (!initOpen) {
                viewPager.currentItem = newItems.size
            }else {
                initOpen = false
            }
        }
    }

    var galleryModels by observeChanges<List<ContactGalleryModel>>(emptyList()) { _, newItems ->
        adapterItems = newItems.map(::ContactGalleryItem)
    }

    var initOpen: Boolean = true

    var onGalleryChanged: ((Int) -> Unit)? = null
    var onGalleryClicked: ((Int) -> Unit)? = null

    init {
        initViewPager(context)
    }

    private fun initViewPager(context: Context) {
        viewPager = ViewPager2(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            recyclerView?.overScrollMode = OVER_SCROLL_NEVER
            registerOnPageChangeCallback(onPageSelected = { onGalleryChanged?.invoke(it) })
            adapter = initAdapter(context)
        }.also(::addView)
    }

    private fun initAdapter(context: Context): ContactGalleryAdapter {
        return ContactGalleryAdapter(context)
            .apply { listenerBinder = ::bindListener }
            .also { adapter = it }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun bindListener(item: ContactGalleryItem, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is ContactGalleryItem.ViewHolder) {
            viewHolder.setOnGalleryClickListener {
                if (isGalleryClickEnabled) {
                    onGalleryClicked?.invoke(viewPager.currentItem)
                }
            }
        }
    }
}
