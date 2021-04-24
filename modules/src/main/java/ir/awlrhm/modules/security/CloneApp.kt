package ir.awlrhm.modules.security

import android.content.Context
import ir.awlrhm.modules.extentions.isPackageInstalled

class CloneApp {
    fun isInstalled(context: Context): Boolean{
        val packageManager = context.packageManager

        val cloneApp = isPackageInstalled("com.cloneapp.parallelspace.dualspace", packageManager)
            val cloneApp64 = isPackageInstalled("com.cloneapp.parallelspace.dualspace.arm64", packageManager)
        val parallelApp = isPackageInstalled("com.lbe.parallel.intl", packageManager)
        val parallelApp64 = isPackageInstalled("com.lbe.parallel.intl.arm64", packageManager)

       return cloneApp || cloneApp64 || parallelApp || parallelApp64
    }
}