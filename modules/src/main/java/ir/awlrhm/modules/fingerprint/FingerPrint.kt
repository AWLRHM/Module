package ir.awlrhm.modules.fingerprint

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import ir.awlrhm.modules.extentions.yToast
import ir.awrhm.modules.R
import ir.awlrhm.modules.enums.MessageStatus


class FingerPrint(
    private val context: AppCompatActivity,
    private val listener: OnActionListener
) {

    private lateinit var cryptographyManager: CryptographyManager
    private val TAG = "EnableBiometricLogin"
    private var canAuthenticate: Int = BiometricManager.from(context).canAuthenticate()

    val hasFingerPrint: Boolean
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS
            else false
        }

    fun show() {
        try {
            if (hasFingerPrint) {
                val secretKeyName = context.getString(R.string.secret_key_name)
                cryptographyManager = CryptographyManager()
                val cipher = cryptographyManager.getInitializedCipherForEncryption(secretKeyName)
                val biometricPrompt =
                    BiometricPromptUtils.createBiometricPrompt(context, ::encryptAndStoreServerToken)
                val promptInfo = BiometricPromptUtils.createPromptInfo(context)
                biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
            }
        } catch (e: Exception) {
            context.yToast(context.getString(R.string.error_operation), MessageStatus.ERROR)
        }
    }

    private fun encryptAndStoreServerToken(authResult: BiometricPrompt.AuthenticationResult) {
        authResult.cryptoObject?.cipher?.apply {
            SampleAppUser.fakeToken?.let { token ->
                Log.d(TAG, "The token from server is $token")
                val encryptedServerTokenWrapper = cryptographyManager.encryptData(token, this)
                cryptographyManager.persistCiphertextWrapperToSharedPrefs(
                    encryptedServerTokenWrapper,
                    context,
                    SHARED_PREFS_FILENAME,
                    Context.MODE_PRIVATE,
                    CIPHERTEXT_WRAPPER
                )
            }
            listener.onSuccess()
        }
    }

    interface OnActionListener{
        fun onSuccess()
    }
}