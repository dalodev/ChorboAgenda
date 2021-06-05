package es.littledavity.commons.ui.extensions

import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

val ViewPager2.recyclerView: RecyclerView?
    get() = (children.firstOrNull() as? RecyclerView)


inline fun ViewPager2.registerOnPageChangeCallback(
    crossinline onPageSelected: (position: Int) -> Unit = {},
    crossinline onPageScrollStateChanged: (state: Int) -> Unit = {},
    crossinline onPageScrolled: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit = { _, _, _ -> }
): ViewPager2.OnPageChangeCallback {
    return object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) = onPageSelected(position)
        override fun onPageScrollStateChanged(state: Int) = onPageScrollStateChanged(state)
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            onPageScrolled(position, positionOffset, positionOffsetPixels)
        }
    }.also(::registerOnPageChangeCallback)
}