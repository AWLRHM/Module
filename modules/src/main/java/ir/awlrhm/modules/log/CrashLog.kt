package ir.awlrhm.modules.log

import androidx.fragment.app.FragmentActivity
import ir.awlrhm.modules.log.database.AWLRHMDatabaseHelper
import ir.awlrhm.modules.log.investigation.Crash
import ir.awlrhm.modules.log.viewmodel.CrashViewModel
import ir.awlrhm.modules.view.ActionDialog

class CrashLog(
    private val activity: FragmentActivity
){
    private val database = AWLRHMDatabaseHelper(activity)
    private val crashes = database.crashes

    fun isExistCrashLog(): Boolean {
        return !crashes[0].stackTrace.isNullOrEmpty()
    }

    val crashLogs: List<Crash>
        get() = crashes

    fun showCrashLog() {
        val viewModel = CrashViewModel(crashes[0])
        val log = StringBuilder()
        log.append("stack trace: \n")
        log.append(crashes[0].stackTrace)
        log.append("\n\n")
        log.append("place of crash : \n")
        log.append(crashes[0].placeOfCrash)
        log.append("\n\n")
        log.append("reason of crash : \n")
        log.append(crashes[0].reasonOfCrash)
        log.append("\n\n")
        log.append("device info : ${viewModel.deviceName}\n")
        log.append("device brand : ${viewModel.deviceBrand}\n")
        log.append("android version : ${viewModel.deviceAndroidApiVersion}")

        ActionDialog.Builder()
            .description(log.toString())
            .build()
            .show(activity.supportFragmentManager, ActionDialog.TAG)
    }

    fun deleteCrashes(): Boolean{
        return database.deleteCrashes() > 0
    }
}