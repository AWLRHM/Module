package ir.awlrhm.modules.log

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.fragment.app.FragmentActivity
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.log.database.AWLRHMDatabaseHelper
import ir.awlrhm.modules.log.investigation.Crash
import ir.awlrhm.modules.log.viewmodel.CrashViewModel
import ir.awlrhm.modules.view.ActionDialog
import ir.awlrhm.modules.enums.MessageStatus

class CrashLog(
    private val activity: FragmentActivity
) {
    private val database = AWLRHMDatabaseHelper(activity)
    private val crashes = database.crashes
    private val log = StringBuilder()

    init {
        val viewModel = CrashViewModel(crashes[0])
        log.append("device info : ${viewModel.deviceName}\n")
        log.append("device brand : ${viewModel.deviceBrand}\n")
        log.append("android version : ${viewModel.deviceAndroidApiVersion}\n\n")
        log.append("Place of Crash ==================\n")
        log.append(crashes[0].placeOfCrash)
        log.append("\n\n")
        log.append("Reason of Crash ==================\n")
        log.append(crashes[0].reasonOfCrash)
        log.append("\n\n")
        log.append("Stack Trace ==================\n")
        log.append(crashes[0].stackTrace)
    }

    fun isExistCrashLog(): Boolean {
        return !crashes[0].stackTrace.isNullOrEmpty()
    }

    val crashLogs: List<Crash>
        get() = crashes

    fun copyToClipboard() {
        val clipboard: ClipboardManager =
            activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Stack trace", log.toString())
        clipboard.setPrimaryClip(clip)
        activity.yToast("Copy to Clipboard", MessageStatus.INFORMATION)
    }

    val fullCrashLog: String
        get() = log.toString()

    fun showCrashLog() {
        ActionDialog.Builder()
            .description(fullCrashLog)
            .build()
            .show(activity.supportFragmentManager, ActionDialog.TAG)
    }

    fun deleteCrashes(): Boolean {
        return database.deleteCrashes() > 0
    }
}