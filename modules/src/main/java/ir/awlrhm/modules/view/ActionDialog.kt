package ir.awlrhm.modules.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import ir.awrhm.modules.R
import ir.awrhm.modules.enums.MessageStatus
import kotlinx.android.synthetic.main.awlrhm_action_dialog.*

class ActionDialog private constructor(
    private val builder: Builder
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.awlrhm_action_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        setTitle(builder.title ?: getString(R.string.warning))
        setAction(builder.status ?: MessageStatus.ERROR)
        cancelable(builder.cancelable ?: false)
        setDescription(builder.description ?: "")
        builder.positiveCallback?.let { setPositive(builder.positive ?: getString(R.string.yes), it) }
        builder.negativeCallback?.let { setNegative(builder.negative ?: getString(R.string.no), it) }
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

    private fun setAction(status: MessageStatus) {
        val activity = activity ?: return

        when (status) {
            MessageStatus.ERROR -> {
                icon.background = ContextCompat.getDrawable(activity, R.drawable.ic_warning)
                txtTitle.text =
                    if (txtTitle.text.toString().isEmpty()) getString(R.string.warning) else return
            }
            MessageStatus.INFORMATION -> {
                icon.background = ContextCompat.getDrawable(activity, R.drawable.ic_info)
                txtTitle.text = if (txtTitle.text.toString()
                        .isEmpty()
                ) getString(R.string.information) else return
            }
            MessageStatus.SUCCESS -> {
                icon.background = ContextCompat.getDrawable(activity, R.drawable.ic_success)
                txtTitle.text =
                    if (txtTitle.text.toString().isEmpty()) getString(R.string.success) else return
            }
        }
    }

    private fun setTitle(title: String) {
        txtTitle.text = title
    }

    private fun setDescription(description: String) {
        txtDescription.text = description
    }

    private fun setPositive(text: String, callback: () -> Unit) {
        layoutPositive.visibility = View.VISIBLE
        txtPositive.text = text
        layoutPositive.setOnClickListener {
            callback.invoke()
            dismiss()
        }
    }

    private fun setNegative(text: String, callback: () -> Unit) {
        txtNegative.text = text
        layoutNegative.visibility = View.VISIBLE
        layoutNegative.setOnClickListener {
            callback.invoke()
            dismiss()
        }
    }

    private fun cancelable(cancelable: Boolean) {
        val dialog = dialog ?: return
        dialog.setCancelable(cancelable)
    }

    class Builder {
        var title: String? = null
        var description: String? = null
        var positive: String? = null
        var negative: String? = null
        var status: MessageStatus? = null
        var positiveCallback: (() -> Unit)? = null
        var negativeCallback: (() -> Unit)? = null
        var cancelable: Boolean? = null

        fun title(title: String) = apply { this.title = title }

        fun description(description: String) = apply { this.description = description }

        fun action(status: MessageStatus) = apply {
            this.status = status
        }

        fun positive(text: String, callback: () -> Unit) = apply {
            this.positive = text
            this.positiveCallback = callback
        }

        fun negative(text: String, callback: () -> Unit) = apply {
            this.negative = text
            this.negativeCallback = callback
        }

        fun cancelable(isCancelable: Boolean) = apply {
            cancelable = isCancelable
        }

        fun build(): ActionDialog {
            return ActionDialog(this)
        }

    }

    companion object {
        val TAG = "module: ${ActionDialog::class.java.simpleName}"
    }
}