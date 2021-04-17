package ir.awlrhm.modules.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import ir.awrhm.modules.R
import kotlinx.android.synthetic.main.awlrhm_date_picker.view.*

class DatePicker(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private var txtTitle: TextView? = null
    private var txtDate: TextView? = null
    private var picker: FrameLayout? = null
    private var border: ConstraintLayout? = null

    init {
        val view = View.inflate(context, R.layout.awlrhm_date_picker, this)

        txtTitle = view.findViewById(R.id.txtTitle)
        txtDate = view.findViewById(R.id.txtDateTime)
        picker = view.findViewById(R.id.layoutDateView)
        border = view.findViewById(R.id.layoutBorder)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.DatePicker)
        txtTitle?.text = attributes.getString(R.styleable.DatePicker_dp_title)
        txtTitle?.setTextColor(
            attributes.getColor(
                R.styleable.DatePicker_dp_titleColor,
                ContextCompat.getColor(context, R.color.cyan_800)
            )
        )
        txtDate?.text = attributes.getString(R.styleable.DatePicker_dp_hint)
        border?.background = ContextCompat.getDrawable(
            context,
            attributes.getResourceId(R.styleable.DatePicker_dp_border, R.drawable.border)
        )
        layoutDateView?.setBackgroundColor(
            ContextCompat.getColor(
                context,
                attributes.getResourceId(R.styleable.DatePicker_dp_background, R.color.white)
            )
        )
        txtTitle?.setBackgroundColor(
            ContextCompat.getColor(
                context,
                attributes.getResourceId(R.styleable.DatePicker_dp_background, R.color.white)
            )
        )
        /* picker?.setOnClickListener {
             PersianDatePickerDialog(context)
                 .setPositiveButtonString("تایید")
                 .setNegativeButton("انصراف")
                 .setTodayButton("امروز")
                 .setTodayButtonVisible(true)
                 .setMinYear(1350)
                 .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
 //        .setInitDate(initDate)
                 .setActionTextColor(Color.BLUE)
                 .setTypeFace(ResourcesCompat.getFont(context, R.font.iran_sans_mobile))
                 .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                 .setShowInBottomSheet(true)
                 .setListener(object : Listener {

                     override fun onDateSelected(persianCalendar: PersianCalendar) {
                         val date = formatDate(
                             persianCalendar.persianYear,
                             persianCalendar.persianMonth,
                             persianCalendar.persianDay
                         )

                         txtDate?.text = date
                         listener?.onPick(date)


                     }

                     override fun onDismissed() {}
                 }).show()
         }*/
    }


    var date: String
        get() = txtDate?.text.toString()
        set(value) {
            txtDate?.text = value
        }

    private var listener: OnDatePickerListener? = null
    fun setOnDatePickerListener(callback: OnDatePickerListener) {
        listener = callback
    }

    interface OnDatePickerListener {
        fun onPick(date: String)
    }
}