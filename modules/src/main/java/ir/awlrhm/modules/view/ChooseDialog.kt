package ir.awlrhm.modules.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.modules.models.Adapter
import ir.awlrhm.modules.models.ItemModel
import ir.awrhm.modules.R
import kotlinx.android.synthetic.main.awlrhm_dialog_choose.*

class ChooseDialog(
    private val list: MutableList<ItemModel>,
    private val titleColor: Int,
    private val backgroundColor: Int,
    private val callback: (ItemModel) -> Unit
) : DialogFragment() {

    constructor(
        list: MutableList<ItemModel>,
        callback: (ItemModel) -> Unit
    ) : this(
        list,
        R.color.white,
        callback
    )

    constructor(
        list: MutableList<ItemModel>,
        backgroundColor: Int,
        callback: (ItemModel) -> Unit
    ) : this(
        list,
        R.color.white,
        backgroundColor,
        callback
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.awlrhm_dialog_choose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setBackgroundColor(backgroundColor)
        setTitleColor(titleColor)
        rclItems.layoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
            .verticalDecoration()

        rclItems.view?.adapter = Adapter(list) { model ->
            callback.invoke(model)
            dismiss()
        }
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


    private fun setBackgroundColor(color: Int) {
        val activity = activity ?: return
        layoutHeader.setBackgroundColor(ContextCompat.getColor(activity, color))
    }

    private fun setTitleColor(color: Int) {
        val activity = activity ?: return
        txtTitle.setTextColor(ContextCompat.getColor(activity, color))
    }

    companion object {
        val TAG = "module: ${ChooseDialog::class.java.simpleName}"
    }
}