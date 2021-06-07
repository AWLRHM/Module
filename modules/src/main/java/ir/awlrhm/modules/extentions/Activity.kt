package ir.awlrhm.modules.extentions

import android.Manifest
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
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import ir.awlrhm.modules.enums.FlashbarDuration
import ir.awlrhm.modules.utils.OnPermissionListener
import ir.awrhm.awrhm_flashbar.Flashbar
import ir.awrhm.awrhm_flashbar.Flashbar.Companion.DURATION_LONG
import ir.awrhm.modules.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awrhm.awrhm_flashbar.Flashbar.Companion.DURATION_SHORT
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


fun Activity.checkWriteStoragePermission(
    callback: () -> Unit
){
    Dexter.withActivity(this)
        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                callback.invoke()
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse) {
                yToast(
                    getString(R.string.set_permission_add_attachment),
                    MessageStatus.ERROR
                )
            }

            override fun onPermissionRationaleShouldBeShown(
                permission: PermissionRequest,
                token: PermissionToken
            ) {
                token.continuePermissionRequest()
            }
        }).check()
}

fun Activity.checkReadWriteStoragePermission(
    callback: () -> Unit
){
    Dexter.withActivity(this)
        .withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    if (report.areAllPermissionsGranted()) {
                        callback.invoke()

                    } else {
                        yToast(
                            getString(R.string.set_permission_add_attachment),
                            MessageStatus.ERROR
                        )
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }
        })
        .withErrorListener {
            yToast(
                getString(R.string.set_permission_add_attachment),
                MessageStatus.ERROR
            )
        }
        .check()
}

fun Activity.checkReadPhoneState(callback: OnPermissionListener){
    Dexter.withActivity(this)
        .withPermission(Manifest.permission.READ_PHONE_STATE)
        .withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                callback.onPermissionGranted()
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse) {
                callback.onPermissionDenied()
            }

            override fun onPermissionRationaleShouldBeShown(
                permission: PermissionRequest,
                token: PermissionToken
            ) {
                token.continuePermissionRequest()
            }
        }).check()
}

fun Activity.successOperation(message: String? = getString(R.string.success_operation)){
    yToast(
        message ?: getString(R.string.success_operation) ,
        MessageStatus.SUCCESS
    )
}

fun Activity.failureOperation(message: String? = getString(R.string.failed_operation)){
    yToast(
        message ?: getString(R.string.failed_operation),
        MessageStatus.ERROR
    )
}

fun Activity.showFlashbarWithBackPressed(
    title: String,
    message: String,
    status: MessageStatus
) {
    Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title(if (title.isEmpty()) getString(R.string.error) else title)
        .icon(
            when (status) {
                MessageStatus.ERROR -> R.drawable.ic_warning
                MessageStatus.SUCCESS -> R.drawable.ic_success
                MessageStatus.INFORMATION -> R.drawable.ic_information
            }
        )
        .titleColorRes(R.color.black)
        .titleSizeInSp(16f)
        .message(message)
        .messageColorRes(R.color.black)
        .backgroundDrawable(
            when (status) {
                MessageStatus.SUCCESS -> R.color.green_A400
                MessageStatus.INFORMATION -> R.color.blue_500
                MessageStatus.ERROR -> R.color.orange_500
            }
        )
        .listenOutsideTaps(object : Flashbar.OnTapListener {
            override fun onTap(flashbar: Flashbar) {
                    onBackPressed()
                    flashbar.dismiss()
            }
        })
        .build()
        .show()
}


fun Activity.showFlashbar(
    title: String,
    message: String,
    status: MessageStatus,
    duration: FlashbarDuration
) {
    Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title(if (title.isEmpty()) getString(R.string.error) else title)
        .icon(
            when (status) {
                MessageStatus.ERROR -> R.drawable.ic_warning
                MessageStatus.SUCCESS -> R.drawable.ic_success
                MessageStatus.INFORMATION -> R.drawable.ic_information
            }
        )
        .titleColorRes(R.color.black)
        .titleSizeInSp(16f)
        .message(message)
        .messageColorRes(R.color.black)
        .backgroundDrawable(
            when (status) {
                MessageStatus.SUCCESS -> R.color.green_A400
                MessageStatus.INFORMATION -> R.color.blue_500
                MessageStatus.ERROR -> R.color.orange_500
            }
        )
        .duration(if(duration == FlashbarDuration.LONG) DURATION_LONG else DURATION_SHORT)
        .build()
        .show()


}

fun Activity.showActionFlashbar(
    title: String,
    message: String,
    okCallback: () -> Unit
) {
    Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title(if (title.isEmpty()) getString(R.string.warning) else title)
        .icon(R.drawable.ic_warning)
        .titleColorRes(R.color.black)
        .titleSizeInSp(16f)
        .message(message)
        .messageColorRes(R.color.black)
        .backgroundDrawable(R.color.orange_500)
        .positiveActionText(R.string.ok)
        .negativeActionText(R.string.cancel)
        .positiveActionTapListener(object : Flashbar.OnActionTapListener {
            override fun onActionTapped(bar: Flashbar) {
                okCallback.invoke()
                bar.dismiss()
            }
        })
        .negativeActionTapListener(object : Flashbar.OnActionTapListener {
            override fun onActionTapped(bar: Flashbar) {
                bar.dismiss()
            }
        })
        .build()
        .show()
}

