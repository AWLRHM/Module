package ir.awlrhm.modules.device

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Build.VERSION_CODES
import android.provider.Settings
import android.telephony.TelephonyManager
import java.lang.reflect.Field
import java.util.*

@SuppressLint("HardwareIds", "MissingPermission")
fun Context.getDeviceIMEI(): String{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
    } else {
        val mTelephony = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (mTelephony.deviceId != null) {
            mTelephony.deviceId
        } else {
            Settings.Secure.getString(
                contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }
    }

}

/*: String = Build.VERSION.SDK_INT.toString()*/
fun getOSVersion(): String{
    val builder = StringBuilder()
    builder.append("android : ").append(Build.VERSION.RELEASE)

    val fields: Array<Field> = VERSION_CODES::class.java.fields
    for (field in fields) {
        val fieldName: String = field.name
        var fieldValue = -1
        try {
            fieldValue = field.getInt(Any())
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        if (fieldValue == Build.VERSION.SDK_INT) {
            builder.append(" : ").append(fieldName).append(" : ")
            builder.append("sdk=").append(fieldValue)
        }
    }
    return builder.toString()
}

fun getDeviceName(): String? {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL
    return if (model.toLowerCase(Locale.getDefault()).startsWith(manufacturer.toLowerCase(Locale.getDefault()))) {
        capitalize(model)
    } else {
        capitalize(manufacturer) + " " + model
    }
}

private fun capitalize(s: String?): String {
    if (s == null || s.isEmpty()) {
        return ""
    }
    val first = s[0]
    return if (Character.isUpperCase(first)) {
        s
    } else {
        Character.toUpperCase(first).toString() + s.substring(1)
    }
}

