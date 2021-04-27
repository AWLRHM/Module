package ir.awlrhm.modules.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.modules.models.DynamicAdapter
import ir.awlrhm.modules.models.DynamicModel
import ir.awrhm.modules.R
import kotlinx.android.synthetic.main.awlrhm_dialog_choose.*

class CustomChooseDialog<T> private constructor(
    private val builder: Builder<T>
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.awlrhm_dialog_choose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
//        setBackgroundColor(builder.backgroundColor)
        setTitleColor(builder.titleColor)
        builder.callback?.let { setSource(builder.list, it) }

        rclItems.layoutManager(
            LinearLayoutManager(context))
            .verticalDecoration()
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

    private fun setSource(
        list: MutableList<DynamicModel<T>>,
        callback: (DynamicModel<T>)-> Unit
    ){
        rclItems.view?.adapter = DynamicAdapter(list) { model ->
            callback.invoke(model)
            dismiss()
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

    class Builder<T> {
        var list: MutableList<DynamicModel<T>> = mutableListOf()
        var backgroundColor = R.color.cyan_500
        var titleColor = R.color.white
        var callback: ((DynamicModel<T>)-> Unit) ?= null

        fun source(list: MutableList<DynamicModel<T>>, callback:(DynamicModel<T>) -> Unit)  = apply{
            this.list = list
            this.callback = callback
        }

        fun backgroundColor(backgroundColor: Int)   = apply{
            this.backgroundColor = backgroundColor
        }

        fun titleColor(titleColor: Int)   = apply{
            this.titleColor = titleColor
        }

        fun build(): CustomChooseDialog<T> {
            return CustomChooseDialog(this)
        }
    }


    companion object {
        val TAG = "module: ${ChooseDialog::class.java.simpleName}"
    }
}