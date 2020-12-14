package ir.awlrhm.modules.extensions

import androidx.fragment.app.FragmentActivity
import ir.awlrhm.modules.view.ActionDialog
import ir.awlrhm.modules.view.downloadVersion.DownloadNewVersion
import ir.awlrhm.modules.view.downloadVersion.OnDownloadListener
import ir.awlrhm.modules.view.progress.ProgressDialog
import ir.awrhm.modules.R
import ir.awrhm.modules.enums.MessageStatus
import kotlin.system.exitProcess

fun FragmentActivity.showSecurityErrorDialog(title: String, message: String) {
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