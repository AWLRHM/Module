package ir.awlrhm.modules.security

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager

class CheckEmulatorUtil {

    fun isEmulator(activity: Activity): Boolean {
        return checkBuildConfiguration()
                || checkIMEI(activity)
                || checkLineNumber(activity)
                || checkSimCountryIso(activity)
                || checkSubscriberId(activity)
                || checkVoiceMailNumber(activity)
    }


    private fun checkBuildConfiguration(): Boolean {
        try {
            return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith(
                "generic"
            )
                    || Build.FINGERPRINT.startsWith("generic")
                    || Build.FINGERPRINT.startsWith("unknown")
                    || Build.HARDWARE.contains("goldfish")
                    || Build.HARDWARE.contains("ranchu")
                    || Build.MODEL.contains("google_sdk")
                    || Build.MODEL.contains("Emulator")
                    || Build.MODEL.contains("Android SDK built for x86")
                    || Build.BOARD == "QC_Reference_Phone" || Build.MANUFACTURER.contains("Genymotion")
                    || Build.HOST.startsWith("Build") //MSI App Player
                    || Build.PRODUCT.contains("sdk_google")
                    || Build.PRODUCT.contains("google_sdk")
                    || Build.PRODUCT.contains("sdk")
                    || Build.PRODUCT.contains("sdk_x86")
                    || Build.PRODUCT.contains("vbox86p")
                    || Build.PRODUCT.contains("emulator")
                    || Build.PRODUCT.contains("simulator"))
        } catch (e: Exception) {
            return false
        }
    }

    @SuppressLint("HardwareIds", "MissingPermission")
    private fun checkIMEI(activity: Activity): Boolean {
        return try {
            val telephonyMgr =
                activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val imei = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                telephonyMgr.imei
            } else {
                telephonyMgr.deviceId
            }
            imei.toLong() == 0L
        } catch (e: Exception) {
            false
        }
    }

    @SuppressLint("HardwareIds", "MissingPermission")
    private fun checkLineNumber(activity: Activity): Boolean {
        return try {
            val telephonyMgr =
                activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyMgr.line1Number.contains("155552155")
        } catch (e: Exception) {
            false
        }
    }

    @SuppressLint("HardwareIds", "MissingPermission")
    private fun checkSimCountryIso(activity: Activity): Boolean {
        val telephonyMgr =
            activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyMgr.simCountryIso == "89014103211118510720"
    }

    @SuppressLint("HardwareIds", "MissingPermission")
    private fun checkSubscriberId(activity: Activity): Boolean {
        return try {
            val telephonyMgr =
                activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyMgr.subscriberId == "310260000000000"
        } catch (e: Exception) {
            false
        }
    }

    @SuppressLint("HardwareIds", "MissingPermission")
    private fun checkVoiceMailNumber(activity: Activity): Boolean {
        return try {
            val telephonyMgr =
                activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyMgr.voiceMailNumber == "15552175049"
        } catch (e: Exception) {
            false
        }
    }
}