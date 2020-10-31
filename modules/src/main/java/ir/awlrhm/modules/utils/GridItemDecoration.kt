package ir.awrhm.modules.utils

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class GridItemDecoration(
    context: Context,
    horizontalResId: Int,
    verticalResId: Int
) :
    ItemDecoration() {
    private val verticalDivider: Drawable?
    private val horizontalDivider: Drawable?
    private val mInsets: Int
    override fun onDrawOver(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        drawVertical(c, parent)
        drawHorizontal(c, parent)
    }

    /**
     * Draw dividers at each expected grid interval
     */
    fun drawVertical(
        c: Canvas?,
        parent: RecyclerView
    ) {
        if (parent.childCount == 0) return
        val childCount = parent.childCount
        val counter = if(childCount % 2 == 0) childCount - 2 else childCount - 1
        for (i in 0 until counter) {
            val child = parent.getChildAt(i)
            val params =
                child.layoutParams as RecyclerView.LayoutParams
            val left = child.left - params.leftMargin - mInsets
            val right = child.right + params.rightMargin + mInsets
            val top = child.bottom + params.bottomMargin + mInsets
            val bottom = top + verticalDivider!!.intrinsicHeight
            verticalDivider.setBounds(left, top, right, bottom)
            c?.let {
                verticalDivider.draw(it)
            }
        }
    }

    /**
     * Draw dividers to the right of each child view
     */
    fun drawHorizontal(
        c: Canvas?,
        parent: RecyclerView
    ) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params =
                child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin + mInsets
            val right = left + horizontalDivider!!.intrinsicWidth
            val top = child.top - params.topMargin - mInsets
            val bottom = child.bottom + params.bottomMargin + mInsets
            horizontalDivider.setBounds(left, top, right, bottom)
            c?.let {
                horizontalDivider.draw(it)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        //We can supply forced insets for each item view here in the Rect
        outRect[mInsets, mInsets, mInsets] = mInsets
    }

    companion object {
        private val ATTRS = intArrayOf(R.attr.listDivider)
    }

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        //        mDivider = a.getDrawable(0);
        verticalDivider = ContextCompat.getDrawable(context, verticalResId)
        horizontalDivider = ContextCompat.getDrawable(context, horizontalResId)
        a.recycle()
        mInsets = 1
    }
}