package ir.awrhm.modules.extensions

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan

fun boldString(str: String, start: Int, end: Int): SpannableStringBuilder {
    val spannable = SpannableStringBuilder(str)
    spannable.setSpan(
        StyleSpan(Typeface.BOLD),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable
}