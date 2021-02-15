package ir.awlrhm.modules.fingerprint

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import ir.awlrhm.modules.extensions.yToast
import ir.awrhm.modules.R
import ir.awrhm.modules.enums.MessageStatus

class FingerPrint(
    private val context: Context
) {

    private lateinit var cryptographyManager: CryptographyManager
    private val TAG = "EnableBiometricLogin"
    private var canAuthenticate: Int = BiometricManager.from(context).canAuthenticate()

    val hasFingerprint: Boolean
        get() {
            return canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS
        }

    fun AppCompatActivity.use() {
        if (hasFingerprint) {
            val secretKeyName = getString(R.string.secret_key_name)
            cryptographyManager = CryptographyManager()
            val cipher = cryptographyManager.getInitializedCipherForEncryption(secretKeyName)
            val biometricPrompt =
                BiometricPromptUtils.createBiometricPrompt(this, ::encryptAndStoreServerToken)
            val promptInfo = BiometricPromptUtils.createPromptInfo(this)
            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
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
            context.yToast("ورود با موفقیت انجام شد", MessageStatus.SUCCESS)
        }
    }
}