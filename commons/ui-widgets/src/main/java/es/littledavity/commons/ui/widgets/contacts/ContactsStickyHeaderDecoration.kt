package es.littledavity.commons.ui.widgets.contacts

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.commons.ui.extensions.getPixels
import es.littledavity.commons.ui.widgets.databinding.ViewContactsStickyHeaderBinding

internal class ContactsStickyHeaderDecoration(
    val adapter: ContactsAdapter,
    root: View
) : RecyclerView.ItemDecoration() {

    private val headerBinding by lazy { ViewContactsStickyHeaderBinding.inflate(LayoutInflater.from(root.context)) }

    private val headerView: View
        get() = headerBinding.root

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        // this will be further customized below

        val topChild = parent.getChildAt(0)
        val secondChild = parent.getChildAt(1)

        parent.getChildAdapterPosition(topChild).let { topPosition ->
            val header = adapter.getHeaderForCurrentPosition(topPosition)
            headerBinding.tvStickyHeader.text = header

            layoutHeaderView(topChild)
            canvas.drawHeaderView(topChild, secondChild)
        }
    }

    private fun layoutHeaderView(topView: View?) {
        topView?.let {
            headerView.measure(
                MeasureSpec.makeMeasureSpec(topView.width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
            )
            headerView.layout(topView.left, 0, topView.right, headerView.measuredHeight)
        }
    }

    private fun Canvas.drawHeaderView(topView: View, secondChild: View?) {
        save()
        translate(0f, calculateHeaderTop(topView, secondChild))
        headerView.draw(this)
        restore()
    }

    private fun calculateHeaderTop(topView: View?, secondChild: View?): Float =
        secondChild?.let { secondView ->
            val threshold = getPixels(8, headerBinding.root.context) + headerView.bottom
            if (secondView.findViewById<View>(headerView.id)?.visibility != View.GONE && secondView.top <= threshold) {
                (secondView.top - threshold).toFloat()
            } else {
                maxOf(topView?.top ?: 0, 0).toFloat()
            }
        } ?: maxOf(topView?.top ?: 0, 0).toFloat()
}