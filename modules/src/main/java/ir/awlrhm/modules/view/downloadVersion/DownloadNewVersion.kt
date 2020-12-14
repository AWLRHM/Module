package ir.awlrhm.modules.view.downloadVersion

/**
 * put this snippet code in AndroidManifest.xml

$$
<provider
android:name="androidx.core.content.FileProvider"
android:authorities="${applicationId}.provider"
android:exported="false"
android:grantUriPermissions="true">
<meta-data
android:name="android.support.FILE_PROVIDER_PATHS"
android:resource="@xml/external_files" />
</provider>
$$

and put this permission in there

$$
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
$$

 **/

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class DownloadNewVersion(
    private val context: Context,
    private val downloadUrl: String,
    private val downloadListener: OnDownload?
) : AsyncTask<Void, Int, String?>() {

    private var input: InputStream? = null
    private var output: OutputStream? = null
    private var connection: HttpURLConnection? = null
    private var downloadFile: File = File(
        context.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
        System.currentTimeMillis().toString() + ".apk"
    )

    override fun onPreExecute() {
        super.onPreExecute()
        downloadListener?.onStart()
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        downloadListener?.onProgress(values[0] ?: 0)
    }

    override fun doInBackground(vararg params: Void): String{
        val fileSize: Int
        try {

            val url = URL(downloadUrl)
            fileSize = getFileSize(url)

            connection = url.openConnection() as HttpURLConnection
            connection?.connect()
            input = connection?.inputStream
            output = FileOutputStream(downloadFile)

            input = connection?.inputStream
            output = FileOutputStream(downloadFile)

            // download the file

            input?.let {
                val data = ByteArray(1024)
                var total: Long = 0
                var count = 0
                var progress: Double
                while ({ count = it.read(data); count }() != -1) {
                    total += count.toLong()
                    output?.write(data, 0, count)
                    progress = 100.0 * total / fileSize
                    publishProgress(progress.toInt())
                }
            }


        } catch (e: Exception) {
            return e.toString()
        } finally {
            try {
                output?.close()
                input?.close()
            } catch (e: IOException) {
                return e.toString()
            }
        }

        return downloadFile.toString()
    }

    override fun onPostExecute(res: String?) {
        val result = res ?: return
        if (File(result).exists() && File(result).length().div(1024) > 1000) {

            //install the downloaded apk file
            val apk = File(result)

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(
                    Uri.fromFile(apk)
                    , "application/vnd.android.package-archive")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            /* val builder = StrictMode.VmPolicy.Builder()
                StrictMode.setVmPolicy(builder.build())
                val fileUri = FileProvider.getUriForFile(
                    context,
                    context.packageName + ".provider",
                    apk
                )
                val intent1 = Intent(Intent.ACTION_VIEW)
                intent1.setDataAndType(
                    fileUri,
                    "application/vnd.android.package-archive"
                )
                intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent1.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                intent1.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)*/

            } else {
                val fileUri = FileProvider.getUriForFile(
                    context,
                    context.packageName + ".provider",
                    apk
                )
                val intent = Intent(Intent.ACTION_VIEW, fileUri)
                intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(fileUri, "application/vnd.android.package-archive")

                context.startActivity(intent)
            }
        } else {
            downloadListener?.onFailed(result)
        }
    }

    private fun getFileSize(url: URL): Int {
        var conn: HttpURLConnection? = null
        return try {
            conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "HEAD"
            conn.inputStream
            conn.contentLength
        } catch (e: IOException) {
            -1
        } finally {
            conn?.disconnect()
        }
    }

    interface OnDownload {
        fun onStart()
        fun onProgress(progress: Int)
        fun onFailed(error: String?)
    }
}
