package ir.awlrhm.modules.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awrhm.modules.R
import ir.awlrhm.modules.base.BaseDialogFragment
import ir.awlrhm.modules.extentions.configImageButton
import ir.awlrhm.modules.extentions.hideKeyboard
import ir.awlrhm.modules.models.ItemModel
import ir.awlrhm.modules.models.Adapter
import kotlinx.android.synthetic.main.awlrhm_searchable_dialog.*

class SearchableDialog(
    private val list: MutableList<ItemModel>,
    private val callback: (ItemModel) -> Unit
) : BaseDialogFragment() {

    override fun setup() {
        rclItems
            .layoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
            .verticalDecoration()

        setDefaultList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.awlrhm_searchable_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun handleOnClickListener()  {
        btnBack.setOnClickListener {
            if (edtSearch.text.toString().isEmpty())
                dismiss()
            else {
                edtSearch.setText("")
                setDefaultList()
                activity?.hideKeyboard(edtSearch)
            }
        }
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let { text ->
                    when (text.length) {
                        0 -> {
                            showSearchLogo(false)
                            setDefaultList()
                        }
                        1 -> showSearchLogo(true)
                        else -> {
                            val result = list.filter { city ->
                                city.title.contains(text, ignoreCase = false)
                            }
                            if (result.isEmpty())
                                showNoData()
                            else {
                                showSearchLogo(false)
                                rclItems.view?.adapter =
                                    Adapter(result as MutableList<ItemModel>) {
                                        callback.invoke(it)
                                        dismiss()
                                    }
                            }
                        }
                    }
                }
            }
        })
    }

    private fun setDefaultList() {
        rclItems.view?.adapter = Adapter(list) { model ->
            callback.invoke(model)
            dismiss()
        }
    }

    private fun showSearchLogo(visible: Boolean) {
        if (visible) {
            rclItems.visibility = View.GONE
            layoutNoData.visibility = View.GONE
            layoutSearch.visibility = View.VISIBLE
        } else {
            layoutSearch.visibility = View.GONE
            layoutNoData.visibility = View.GONE
            rclItems.visibility = View.VISIBLE
        }
    }

    private fun showNoData() {
        rclItems.visibility = View.GONE
        layoutSearch.visibility = View.GONE
        layoutNoData.visibility = View.VISIBLE
    }

    private fun setButtonsColor(color: Int) {
        activity?.configImageButton(btnBack, color)
        activity?.configImageButton(btnSearch, color)
    }

    private fun setBackgroundColor(color: Int){
        val activity = activity ?: return
        search.setCardBackgroundColor(ContextCompat.getColor(activity, color))
    }

    fun backgroundColor(color: Int) = apply { setBackgroundColor(color) }
    fun buttonsColor(color: Int) = apply { setButtonsColor(color) }

    companion object {
        val TAG = "module: ${SearchableDialog::class.java.simpleName}"
    }
}