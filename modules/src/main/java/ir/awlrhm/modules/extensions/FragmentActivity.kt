package ir.awlrhm.modules.extensions

import androidx.fragment.app.FragmentActivity
import ir.awlrhm.modules.security.CheckEmulatorUtil
import ir.awlrhm.modules.security.CheckReverseEngineeringToolsUtil
import ir.awlrhm.modules.security.CheckRootUtil
import ir.awlrhm.modules.security.CloneApp
import ir.awlrhm.modules.view.ActionDialog
import ir.awlrhm.modules.view.downloadVersion.DownloadNewVersion
import ir.awlrhm.modules.view.downloadVersion.OnDownloadListener
import ir.awlrhm.modules.view.progress.ProgressDialog
import ir.awrhm.modules.R
import ir.awrhm.modules.enums.MessageStatus
import kotlin.system.exitProcess

fun FragmentActivity.downloadNewVersion(
    downloadUrl: String?,
    listener: OnDownloadListener
) {

    val progressDialog = ProgressDialog.Builder().build()

    DownloadNewVersion(
        this,
        downloadUrl ?: "",
        object : DownloadNewVersion.OnDownload {

            override fun onStart() {
                progressDialog.show(supportFragmentManager, ProgressDialog.TAG)
            }

            override fun onProgress(progress: Int) {
                if (!progressDialog.showsDialog)
                    progressDialog.show(supportFragmentManager, ProgressDialog.TAG)
                progressDialog.setProgress(progress)
                if (progress == 100 && progressDialog.showsDialog) {
                    progressDialog.dismiss()
                    listener.onSuccess()
                }
            }

            override fun onFailed(error: String?) {
                if (progressDialog.showsDialog) {
                    progressDialog.dismiss()
                    listener.onFailed()
                }
            }
        }
    ).execute()
}

fun FragmentActivity.checkSecurity(callback: ()->Unit) {
    val isRooted = CheckRootUtil().isDeviceRooted
    val isEmulator = CheckEmulatorUtil().isEmulator(this)
    val isExistReverseEngTools =
        CheckReverseEngineeringToolsUtil().isExistTools(this)
    val isCloneAppInstalled = CloneApp().isInstalled(this)

    var isSecure = false
    var title = ""
    var message = ""
    when {
        isEmulator -> {
            title = getString(R.string.emulator_detection)
            message = getString(R.string.app_not_run_on_emulator)
        }
        isRooted -> {
            title = getString(R.string.root_detection)
            message = getString(R.string.your_device_is_root)
        }
        isExistReverseEngTools -> {
            title = getString(R.string.reverse_engineering_tools_detection)
            message = getString(R.string.is_exist_reverse_engineering_tools)
        }
        isCloneAppInstalled -> {
            title = getString(R.string.parallel_space_app_exist)
            message = getString(R.string.is_exist_parallel_space_app)
        }
        else -> isSecure = true
    }
    if(isSecure)
        callback.invoke()
    else
        ActionDialog.Builder()
            .action(MessageStatus.ERROR)
            .title(if (title.isEmpty()) getString(R.string.error) else title)
            .description(if (message.isEmpty()) getString(R.string.security_problem) else message)
            .negative(getString(R.string.ok)) {
                exitProcess(0)
            }
            .build()
            .show(supportFragmentManager, ActionDialog.TAG)
}