package ir.awlrhm.modules.view.changesDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.modules.base.BaseDialogFragment
import ir.awrhm.modules.R
import ir.nikafarinegan.automation.view.changesDialog.Adapter
import kotlinx.android.synthetic.main.awlrhm_dialog_changes_list.*

class ChangesListDialog(
    private val list: List<ReleaseChangeModel>,
    private val callback: ()-> Unit
): BaseDialogFragment() {

    override fun setup() {
        rclChanges.layoutManager = LinearLayoutManager(context)
        rclChanges.setHasFixedSize(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.awlrhm_dialog_changes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rclChanges.adapter = Adapter(list.reversed())
        btnOk.setOnClickListener {
            dismiss()
        }
    }

    override fun handleEvents() {

    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callback.invoke()
    }

    companion object{
        val TAG = "automation: ${ChangesListDialog::class.java.simpleName}"
    }
}