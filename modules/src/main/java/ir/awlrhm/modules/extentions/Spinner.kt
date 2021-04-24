package ir.awlrhm.modules.extentions

import ir.awlrhm.modules.view.Spinner

fun Spinner.isValidTitle(title: String?){
    text = title ?: "داده ای برای نمایش وجود ندارد"
}

fun Spinner.failedData(){
    loading(false)
    text = "داده ای برای نمایش وجود ندارد"
}