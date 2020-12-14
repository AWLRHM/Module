package ir.awlrhm.modules.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.StrictMode
import android.util.DisplayMetrics
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import ir.awrhm.modules.R
import java.io.File

fun Activity.configBottomSheet(view: View, divide: Float) {
    val params = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams
    //val behavior = params.behavior

    val parent = view.parent as View
    parent.fitsSystemWindows = true

    val bottomSheetBehavior = BottomSheetBehavior.from(parent)
    view.measure(0, 0)
    val dm = DisplayMetrics()
    windowManager?.defaultDisplay?.getMetrics(dm)
    val screenHeight = dm.heightPixels
    bottomSheetBehavior.peekHeight = (screenHeight / divide).toInt()
    params.height = screenHeight
    parent.layoutParams = params
}

fun Activity.openPdf(path: String, filename: String?) {
    val file = File(path + File.separator + filename)

    val builder = StrictMode.VmPolicy.Builder()
    StrictMode.setVmPolicy(builder.build())
    val uri = Uri.fromFile(file)

    val intent = Intent(Intent.ACTION_VIEW)
    if (uri.toString().contains(".pdf")) {
        intent.setDataAndType(uri, "application/pdf")
    }
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}

fun Activity.ySnake(message: String, action: String) {
    val snackbar = Snackbar
        .make(
            this.findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_INDEFINITE
        )
    snackbar.setAction(action) {
        snackbar.dismiss()
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
}


