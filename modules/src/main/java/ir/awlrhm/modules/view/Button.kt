package ir.awlrhm.modules.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import ir.awrhm.modules.R

class Button(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var button: ConstraintLayout? = null

    init {
        val view = View.inflate(context, R.layout.awlrhm_custom_button, this)
        button = view.findViewById(R.id.layoutButton)
        val constraintSet = ConstraintSet()
        constraintSet.clone(button)


        val attributes = context.obtainStyledAttributes(attrs, R.styleable.Button)
        val text = TextView(context)
        text.text = attributes.getString(R.styleable.Button_btn_title)
        text.setTextColor(
            attributes.getColor(
                R.styleable.Button_btn_textColor,
                ContextCompat.getColor(context, R.color.black)
            )
        )
        val textSize = attributes.getDimension(R.styleable.Button_btn_textSize, 12f)
        if (textSize > 0)
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

        button?.addView(text)

        val icon = ImageView(context)
        icon.setImageResource(
            /*attributes.getInteger(R.styleable.Button_btn_srcCompat, */R.drawable.ic_warning
        )

        button?.addView(icon)


        /*val direction = attributes.getInteger(R.styleable.Button_btn_icon_direction, 0)
        if (direction == 0)
            constraintSet.connect(icon.id, ConstraintSet.END, text.id, ConstraintSet.START,0)
        else
            constraintSet.connect(icon.id, ConstraintSet.START, text.id, ConstraintSet.END,0)

        constraintSet.applyTo(button)*/
    }
}