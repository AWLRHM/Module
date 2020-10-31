package ir.financialworld.masterstock.utils

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class VerticalItemDecoration(
    context: Context,
    resId: Int): RecyclerView.ItemDecoration(){

    private val ATTRS = intArrayOf(R.attr.listDivider)

    private var divider: Drawable? = null

   init {
       val styledAttributes: TypedArray = context.obtainStyledAttributes(ATTRS)
//       divider = styledAttributes.getDrawable(0)
       divider = ContextCompat.getDrawable(context, resId)
       styledAttributes.recycle()
   }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params =
                child.layoutParams as RecyclerView.LayoutParams
            val top: Int = child.bottom + params.bottomMargin
            val bottom: Int = top + (divider?.intrinsicHeight ?: 0)
            divider?.setBounds(left, top, right, bottom)
            divider?.draw(c)
        }
    }
}