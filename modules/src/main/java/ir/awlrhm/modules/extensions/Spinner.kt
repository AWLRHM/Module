package ir.awlrhm.modules.extensions

import ir.awlrhm.modules.view.Spinner

fun Spinner.isValidTitle(title: String?){
    text = title ?: "داده ای برای نمایش وجود ندارد"
}