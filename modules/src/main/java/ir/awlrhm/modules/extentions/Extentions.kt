package ir.awlrhm.modules.extentions

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Base64
import android.util.Base64OutputStream
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.awlrhm.modules.view.datePicker.CalendarActionListener
import ir.awlrhm.modules.view.datePicker.PersianDatePickerDialog
import ir.awrhm.modules.R
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.util.*

fun convertToEnglishDigits(value: String): String {
    return value.replace("١", "1").replace("٢", "2").replace("٣", "3").replace("٤", "4")
        .replace("٥", "5")
        .replace("٦", "6").replace("٧", "7").replace("٨", "8").replace("٩", "9")
        .replace("٠", "0")
        .replace("۱", "1").replace("۲", "2").replace("۳", "3").replace("۴", "4")
        .replace("۵", "5")
        .replace("۶", "6").replace("۷", "7").replace("۸", "8").replace("۹", "9")
        .replace("۰", "0")
}

fun formatDate(y: Int, m: Int, d: Int): String {
    val month = if (m < 10) "0${m}" else m
    val day = if (d < 10) "0${d}" else d
    return "${y}/${month}/${day}"
}

fun formatDate(d: String): String {
    val date = d.split("/")
    val year = date[0]
    val month = if (date[1].toInt() < 10) "0${date[1].toInt()}" else date[1]
    val day = if (date[2].toInt() < 10) "0${date[2].toInt()}" else date[2]
    return "${year}/${month}/${day}"
}

fun formatTime(t: String): String {
    val time = t.split(":")
    val hour = if (time[0].trim().toInt() < 10) "0${time[0].trim()}" else time[0].trim()
    val minute = if (time[1].replace(" ", "").toInt() < 10) "0${time[1].replace(
        " ",
        ""
    )}" else time[1].replace(" ", "")
    return "$hour:$minute"
}

fun calculateDurationTime(
    startTime: String,
    endTime: String
): Pair<Int, Int> {

    val startHour = startTime.split(':')[0].toInt()
    val startMinute = startTime.split(':')[1].toInt()
    val endHour = endTime.split(':')[0].toInt()
    val endMinute = endTime.split(':')[1].toInt()

    val calculateDuration = if (endHour == startHour && endMinute > startMinute)
        (60.0 - startMinute) + endMinute
    else if (endHour > startHour)
        ((endHour * 60.0) - (startHour * 60.0)) + (60.0 - startMinute) + endMinute
    else return Pair(0, 0)

    val hour = (calculateDuration / 60.0).toInt()
    val minute = (calculateDuration % 60.0).toInt()
    return Pair(
        first = hour - 1,
        second = minute
    )
}

fun calculateDurationDay(startDate: String, endDate: String): Int {
    val start = startDate.split('/')
    val end = endDate.split('/')

    val startYear = start[0].toInt()
    val startMonth = start[1].toInt()
    val startDay = start[2].toInt()

    val endYear = end[0].toInt()
    val endMonth = end[1].toInt()
    val endDay = end[2].toInt()

    return when {
        endYear == startYear -> calculateCurrentYear(startMonth, endMonth, startDay, endDay)
        endYear > startYear -> ((endYear - startYear - 1) * 365) + calculateNextYear(
            startMonth,
            endMonth,
            startDay,
            endDay
        )
        else -> -1
    }
}

fun calculateNextYear(startMonth: Int, endMonth: Int, startDay: Int, endDay: Int): Int {
    return if (startMonth < 12) {
        if (endMonth < 7)
            (((11 - startMonth) * 30) + (30 - startDay) + 29) + ((endMonth - 1) * 31 + endDay)
        else
            (((11 - startMonth) * 30) + (30 - startDay) + 29) + ((6 * 31) + (endMonth - 7) * 30 + endDay)
    } else {
        if (endMonth < 7)
            (29 - startDay) + ((endMonth - 1) * 31 + endDay)
        else
            (29 - startDay) + ((6 * 31) + (endMonth - 7) * 30 + endDay)
    }
}


fun calculateCurrentYear(startMonth: Int, endMonth: Int, startDay: Int, endDay: Int): Int {
    when {
        startMonth == endMonth -> {
            return if (endDay >= startDay)
                endDay - startDay
            else
                -1
        }
        endMonth > startMonth -> {
            return if (endMonth >= 7 && startMonth >= 7)
                ((endMonth - startMonth - 1) * 30) + (30 - startDay) + endDay
            else if (endMonth < 7 && startMonth < 7)
                ((endMonth - startMonth - 1) * 31) + (31 - startDay) + endDay
            else
                (((6 - startMonth) * 31) + (31 - startDay)) + (((endMonth - 7) * 30) + endDay)

        }
        else -> return -1
    }
}

fun convertToBitmap(imageString: String): Bitmap {
    val decodedByte = Base64.decode(imageString, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
}

fun convertToPdf(
    pdfStream: String,
    path: String,
    name: String,
    callback: (Boolean) -> Unit
) {
    try {
        val directory = File(path)
        if (!directory.exists())
            directory.mkdirs()

        val file = File(path, "$name.pdf")
        if (file.exists())
            file.delete()

        val pdfAsBytes: ByteArray = Base64.decode(pdfStream, Base64.DEFAULT)
        val os = FileOutputStream(file)
        os.write(pdfAsBytes)
        os.flush()
        os.close()
        callback.invoke(true)
    } catch (e: Exception) {
        e.printStackTrace()
        callback.invoke(false)
    }
}

fun convertToBase64(file: File): String {
    var inputStream: InputStream? = null //You can get an inputStream using any IO API

    inputStream = FileInputStream(file.absolutePath)
    val buffer = ByteArray(8192)
    var bytesRead: Int
    val output = ByteArrayOutputStream()
    val output64 = Base64OutputStream(output, Base64.DEFAULT)
    try {
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            output64.write(buffer, 0, bytesRead)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    output64.close()

    return output.toString()
}

fun convertBase64ToFile(
    stream: String,
    path: String,
    name: String,
    extensionType: String,
    callback: (Boolean) -> Unit
) {
    try {
        val directory = File(path)
        if (!directory.exists())
            directory.mkdirs()

        val file = File(path, "${name}.${extensionType.toLowerCase(Locale.getDefault())}")
        if (file.exists())
            file.delete()

        val pdfAsBytes: ByteArray = Base64.decode(stream, Base64.DEFAULT)
        val os = FileOutputStream(file)
        os.write(pdfAsBytes)
        os.flush()
        os.close()
        callback.invoke(true)
    } catch (e: Exception) {
        e.printStackTrace()
        callback.invoke(false)
    }
}

fun copyFile(input: InputStream, out: OutputStream) {
    val buffer = ByteArray(1024)
    var read: Int
    while (input.read(buffer).also { read = it } != -1) {
        out.write(buffer, 0, read)
    }
}

fun isPackageInstalled(packageName: String, pm: PackageManager): Boolean {
    return try {
        pm.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun <T> convertUTTModelToJson(list: MutableList<T>): String {
    val jsonArray = JSONArray()
    list.forEachIndexed { index, _ ->
        val str = Gson().toJson(list[index])
        val json = JSONObject(str)
        jsonArray.put(json)
    }
    return jsonArray.toString()
}
