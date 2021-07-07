package ir.awlrhm.modules.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.awlrhm.modules.base.BaseDialogFragment
import ir.awrhm.modules.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.yToast
import kotlinx.android.synthetic.main.dialog_register_data.*

class RegisterDataDialog(
    private val title: String,
    private val action: String,
    private val callback: (String) -> Unit
) : BaseDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_register_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity ?: return

        this.txtTitle.text = if(title.isNotEmpty()) title else getString(R.string.enter_data)
        this.txtAction.text = if(action.isNotEmpty()) action else getString(R.string.send)
        this.layoutAction.setOnClickListener {
            val name = edtValue.text.toString()
            if (name.isNotEmpty()) {
                callback.invoke(name)
                dismiss()
            } else
                activity.yToast(
                    getString(R.string.enter_data),
                    MessageStatus.ERROR
                )
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    companion object {
        val TAG = "automation: ${RegisterDataDialog::class.java.simpleName}"
    }
}