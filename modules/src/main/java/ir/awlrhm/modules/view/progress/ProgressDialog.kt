package ir.awlrhm.modules.view.progress

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.awlrhm.modules.base.BaseDialogFragment
import ir.awrhm.modules.R
import kotlinx.android.synthetic.main.awlrhm_dialog_progress.*

class ProgressDialog private constructor(
    private val builder: Builder
) : BaseDialogFragment() {


    override fun setup() {
        dialog?.setCancelable(false)
        setProgress(builder.progress)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.awlrhm_dialog_progress, container, false)
    }

    @SuppressLint("SetTextI18n")
    fun setProgress(progress: Int) {
        val dialog = dialog ?: return

        if(dialog.isShowing) {
            this.txtPercentage.text = "$progress %"
            progressbar.progress = progress
        }
    }

    class Builder {
        var progress: Int = 0

        fun setProgress(progress: Int) = apply {
            this.progress = progress
        }

        fun build(): ProgressDialog {
            return ProgressDialog(this)
        }
    }

    companion object {
        val TAG = "automation: ${ProgressDialog::class.java.simpleName}"
    }
}