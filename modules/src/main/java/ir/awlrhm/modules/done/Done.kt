package ir.awlrhm.modules.done

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import ir.awlrhm.modules.extensions.configProgressbar
import ir.awrhm.modules.R

class Done(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var hasBorder: Int = 0
    private var color: Int = R.color.white
    private var done: ImageView?= null
    private var prcDone: ProgressBar?= null

    init {
        val view = View.inflate(context, R.layout.awlrhm_done, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.Done)
        done = view.findViewById(R.id.imgDone)
        prcDone = view.findViewById(R.id.prcDone)

        hasBorder = attributes.getInteger(R.styleable.Done_dn_hasBorder, 0)
        if(hasBorder == 1)
            done?.setBackgroundColor(Color.TRANSPARENT)

        done?.setBackgroundColor(ContextCompat.getColor(context, attributes.getInt(R.styleable.Done_dn_color, R.color.white)))
        prcDone?.let {
            context.configProgressbar(it, ContextCompat.getColor(context, attributes.getInt(R.styleable.Done_dn_color, R.color.white)))
        }
    }

    fun loading(visible: Boolean){
        if(visible){
            done?.isVisible = false
            prcDone?.isVisible = true
        }else{
            done?.isVisible = true
            prcDone?.isVisible = false
        }
    }
}