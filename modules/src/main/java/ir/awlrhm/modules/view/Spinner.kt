package ir.awlrhm.modules.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import ir.awlrhm.modules.extensions.configProgressbar
import ir.awlrhm.modules.models.ItemModel
import ir.awrhm.modules.R
import kotlinx.android.synthetic.main.awlrhm_spinner.view.*

class Spinner(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var progress: ProgressBar? = null
    private var icDropDown: ImageView? = null
    private var txtSpinner: TextView? = null
    private var titleSpinner: TextView? = null
    private var border: ConstraintLayout? = null

    init {

        val view = View.inflate(context, R.layout.awlrhm_spinner, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.Spinner)

        progress = view.findViewById(R.id.prcSpinner)
        icDropDown = view.findViewById(R.id.icDropDown)
        txtSpinner = view.findViewById(R.id.txtSpinner)
        titleSpinner = view.findViewById(R.id.txtTitleSpinner)
        border = view.findViewById(R.id.layoutBorder)

        txtSpinner?.text = attributes.getString(R.styleable.Spinner_sp_hint)
        titleSpinner?.text = attributes.getString(R.styleable.Spinner_sp_title)
        titleSpinner?.setTextColor(
            attributes.getColor(
                R.styleable.Spinner_sp_titleColor,
                ContextCompat.getColor(context, R.color.cyan_800)
            )
        )
        border?.background = ContextCompat.getDrawable(
            context,
            attributes.getResourceId(R.styleable.Spinner_sp_border, R.drawable.border)
        )
        spinner?.setBackgroundColor(ContextCompat.getColor(
            context,
            attributes.getResourceId(R.styleable.Spinner_sp_background, R.color.white)
        ))
        titleSpinner?.setBackgroundColor(ContextCompat.getColor(
            context,
            attributes.getResourceId(R.styleable.Spinner_sp_background, R.color.white)
        ))
        progress?.let {  context.configProgressbar(it,  attributes.getResourceId(R.styleable.Spinner_sp_background, R.color.cyan_500)) }
    }

    val isLoading: Boolean
        get() {
            return progress?.visibility == View.VISIBLE
        }

    fun loading(visible: Boolean) {
        if (visible) {
            progress?.visibility = View.VISIBLE
            icDropDown?.visibility = View.GONE

        } else {
            progress?.visibility = View.GONE
            icDropDown?.visibility = View.VISIBLE
        }
    }

    fun showData(
        list: MutableList<ItemModel>,
        activity: FragmentActivity,
        color: Int = R.color.cyan_500,
        callback: (ItemModel) -> Unit
    ) {
        loading(false)
        when {
            list.isEmpty() -> txtSpinner?.text = activity.getString(R.string.no_data)
            list.size == 1 -> {
                txtSpinner?.text = list[0].title
                callback.invoke(list[0])

            }
            else -> ChooseDialog(list, color) {
                txtSpinner?.text = it.title
                callback.invoke(it)
            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
        }
    }

    fun showSearchableData(
        list: MutableList<ItemModel>,
        activity: FragmentActivity,
        callback: (ItemModel) -> Unit
    ) {
        loading(false)
        when {
            list.isEmpty() -> txtSpinner?.text = activity.getString(R.string.no_data)
            list.size == 1 -> {
                txtSpinner?.text = list[0].title
                callback.invoke(list[0])

            }
            else -> SearchableDialog(list) {
                txtSpinner?.text = it.title
                callback.invoke(it)
            }.show(activity.supportFragmentManager, SearchableDialog.TAG)
        }
    }

    var text: String?
        get() {
            return txtSpinner?.text.toString()
        }
        set(value) {
            txtSpinner?.text = value ?: "داده ای برای نمایش وجود ندارد"
        }
}