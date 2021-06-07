package ir.awlrhm.modules.extentions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.awlrhm.modules.view.datePicker.CalendarActionListener
import ir.awlrhm.modules.view.datePicker.PersianDatePickerDialog
import ir.awrhm.modules.R
import ir.awlrhm.modules.enums.MessageStatus

fun Context.showKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.yToast(message: String, status: MessageStatus) {
    val toast = Toast(this)
    toast.duration = Toast.LENGTH_LONG

    val inflater: LayoutInflater =
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val view: View = inflater.inflate(R.layout.awlrhm_layout_toast, null)
    val parent = view.findViewById<ConstraintLayout>(R.id.parent)
    val icon = view.findViewById<AppCompatImageView>(R.id.icon)

    when (status) {
        MessageStatus.SUCCESS -> {
            parent.background = ContextCompat.getDrawable(this, R.drawable.toast_success)
            icon.background = ContextCompat.getDrawable(this, R.drawable.ic_success)
        }
        MessageStatus.ERROR -> {
            parent.background = ContextCompat.getDrawable(this, R.drawable.toast_warning)
            icon.background = ContextCompat.getDrawable(this, R.drawable.ic_warning)
        }
        else -> {
            parent.background = ContextCompat.getDrawable(this, R.drawable.toast_information)
            icon.background = ContextCompat.getDrawable(this, R.drawable.ic_info)
        }
    }
    val text = view.findViewById<TextView>(R.id.txtMessage)
    text.text = message
    toast.view = view
    toast.show()
}

fun Context.showDatePicker(callback:(String)->Unit){
    PersianDatePickerDialog(this)
        .setPositiveButtonString("تایید")
        .setNegativeButton("انصراف")
        .setTodayButton("امروز")
        .setTodayButtonVisible(true)
        .setMinYear(1350)
        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
//        .setInitDate(initDate)
        .setActionTextColor(Color.BLUE)
        .setTypeFace(ResourcesCompat.getFont(this, R.font.iran_sans_mobile))
        .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
        .setShowInBottomSheet(true)
        .setListener(object :
            CalendarActionListener {
            @SuppressLint("SetTextI18n")
            override fun onDateSelected(persianCalendar: PersianCalendar) {
               callback.invoke(
                    formatDate(
                        persianCalendar.persianYear,
                        persianCalendar.persianMonth,
                        persianCalendar.persianDay
                    )
               )
            }

            override fun onDismissed() {}
        }).show()
}

fun Context.configProgressbar(progress: ProgressBar, color: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        val wrapDrawable = DrawableCompat.wrap(progress.indeterminateDrawable)
        DrawableCompat.setTint(
            wrapDrawable,
            ContextCompat.getColor(this, color)
        )
        progress.indeterminateDrawable = DrawableCompat.unwrap(
            wrapDrawable
        )
    } else {
        progress.indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(this, color),
            PorterDuff.Mode.SRC_IN
        )
    }
}

fun Context.configImageButton(btn: ImageButton, color: Int){
    btn.setColorFilter(ContextCompat.getColor(this, color), PorterDuff.Mode.SRC_IN)
}


fun Context.configSearchView(searchView: androidx.appcompat.widget.SearchView) {
    val searchEditText =
        searchView.findViewById(R.id.search_src_text) as EditText
    searchEditText.setTextColor(ContextCompat.getColor(this, R.color.cyan_500))
    searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.grey_500))
    (searchView.findViewById(R.id.search_plate) as View).background = null
}


/*fun Context.yGetSettingsPermissionMessage(view: View) {
    val snackbar = Snackbar
        .make(
            view,
            getString(R.string.access_settings),
            Snackbar.LENGTH_INDEFINITE
        )
        .setAction(getString(R.string.settings)) {
            showSettings()
        }

    snackbar.setActionTextColor(
        ContextCompat.getColor(
            this,
            R.color.light_green_A700
        )
    )
    val sbView = snackbar.view
    val textView: TextView =
        sbView.findViewById<View>(R.id.snackbar_text) as TextView
    textView.setTextColor(ContextCompat.getColor(this, R.color.white))
    snackbar.show()
}*/

/*
fun Context.showSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri: Uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}*/

fun Context.isValidTitle(title: String?): String{
    return if(title.isNullOrEmpty())
        getString(R.string.no_data)
    else title
}