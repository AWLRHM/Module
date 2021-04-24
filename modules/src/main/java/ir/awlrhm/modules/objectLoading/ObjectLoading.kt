package ir.awlrhm.modules.objectLoading

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import ir.awlrhm.modules.extentions.configProgressbar
import ir.awrhm.modules.R

class ObjectLoading(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var hasBorder: Int = 0
    private var color: Int = R.color.white
    private var imgObject: ImageView? = null
    private var progress: ProgressBar? = null

    companion object {
        private const val BORDER_WHITE = 0
        private const val BORDER_BLACK = 1
        private const val BORDER_BLUE = 2
        private const val BORDER_ORANGE = 3
    }

    init {
        val view = View.inflate(context, R.layout.awlrhm_object_loading, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ObjectLoading)
        imgObject = view.findViewById(R.id.imgObject)
        progress = view.findViewById(R.id.progress)

        progress?.let {
            context.configProgressbar(it, R.color.white)
        }

        imgObject?.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                attributes.getResourceId(
                    R.styleable.ObjectLoading_obl_source, R.drawable.ic_done
                )
            )
        )

        imgObject?.setColorFilter(
            ContextCompat.getColor(
                context,
                attributes.getResourceId(R.styleable.ObjectLoading_obl_color, R.color.white)
            ), android.graphics.PorterDuff.Mode.SRC_IN
        )

        val hasBorder = attributes.getInt(R.styleable.ObjectLoading_obl_hasBorder, 0)
        if (hasBorder == 0)
            imgObject?.setBackgroundColor(Color.TRANSPARENT)

        val borderColor = attributes.getInt(R.styleable.ObjectLoading_obl_borderColor, BORDER_WHITE)

        imgObject?.setBackgroundResource(
            when (borderColor) {
                BORDER_BLACK -> R.drawable.circle_black
                BORDER_BLUE -> R.drawable.circle_blue
                BORDER_ORANGE -> R.drawable.circle_orange
                else -> R.drawable.circle_white
            }
        )

    }

    fun loading(visible: Boolean) {
        if (visible) {
            imgObject?.isVisible = false
            progress?.isVisible = true
        } else {
            imgObject?.isVisible = true
            progress?.isVisible = false
        }
    }
}