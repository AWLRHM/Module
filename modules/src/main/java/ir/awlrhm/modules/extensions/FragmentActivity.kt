package ir.awlrhm.modules.extensions

import androidx.fragment.app.FragmentActivity
import ir.awlrhm.modules.view.ActionDialog
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