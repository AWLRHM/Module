package ir.awlrhm.modules.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.modules.base.BaseDialogFragment
import ir.awlrhm.modules.enums.Status
import ir.awlrhm.modules.models.DynamicAdapter
import ir.awlrhm.modules.models.DynamicModel
import ir.awrhm.modules.R
import ir.awrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extensions.hideKeyboard
import ir.awlrhm.modules.extensions.yToast
import kotlinx.android.synthetic.main.awlrhm_searchable_dialog.*

class SearchablePagingDialog<T>(
    private val listener: OnActionListener<T>
) : BaseDialogFragment() {

    private var totalCount: Long = 0
    private var counter: Int = 1

    private lateinit var adapter: DynamicAdapter<T>
    private var callbackStatus: CallbackStatus = CallbackStatus.NONE
    private var listModel: MutableList<DynamicModel<T>> = mutableListOf()

    override fun setup() {
        rclItems
            .layoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
            .verticalDecoration()
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
        initAdapter()
        handleEvents()
    }

    private fun initAdapter() {
        adapter = DynamicAdapter { model ->
            listener.onChoose(model)
            dismiss()
        }
        rclItems.onActionRecyclerViewListener(object : RecyclerView.OnRecyclerViewListener {
            override fun onScrolled(
                recyclerView: androidx.recyclerview.widget.RecyclerView,
                dx: Int,
                dy: Int
            ) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisible = layoutManager.findLastCompletelyVisibleItemPosition()
                val endHasBeenReached = lastVisible + 1 >= totalItemCount
                if (totalItemCount > 0 && endHasBeenReached && totalCount > adapter.itemCount) {
                    status(Status.LOADING, isPaging = true)
                    counter += 1
                    callbackStatus = CallbackStatus.PAGING
                    listener.onSearchPaging(counter, edtSearch.text.toString())
                }
            }
        })
        adapter.setSource(listModel)
        rclItems.view?.adapter = adapter
    }

    override fun handleEvents() {
        val activity = activity ?: return

        btnBack.setOnClickListener {
            dismiss()
        }
        btnSearch.setOnClickListener {
            if (!rclItems.isOnLoading) {
                val search = edtSearch.text.toString()
                if (search.isNotEmpty()) {
                    activity.hideKeyboard(edtSearch)
                    status(Status.LOADING, isPaging = false)
                    callbackStatus = CallbackStatus.SEARCH
                    listener.onSearchPaging(1, search)
                } else
                    activity.yToast(getString(R.string.enter_search_word), MessageStatus.ERROR)
            } else
                activity.yToast(getString(R.string.please_wait), MessageStatus.INFORMATION)
        }
    }

    fun setSource(totalCount: Long, list: MutableList<DynamicModel<T>>) {
        this.totalCount = totalCount
        if (list.isNotEmpty()) {
            when (callbackStatus) {
                CallbackStatus.PAGING -> {
                    status(Status.SUCCESS)
                    listModel.addAll(list)
                    if (isAdded)
                        adapter.setSource(listModel)
                }
                CallbackStatus.SEARCH -> {
                    listModel = list
                    if (isAdded) {
                        adapter.setSource(listModel)
                        rclItems.view?.adapter = adapter
                    }
                }
                CallbackStatus.NONE -> {
                    listModel = list
                }
            }
        } else
            showNoData()
    }

    private fun status(status: Status, isPaging: Boolean = false) {
        if (isAdded)
            when (status) {
                Status.FAILURE -> {
                    prcDialog.isVisible = false
                }
                Status.SUCCESS -> {
                    prcDialog.isVisible = false
                }
                Status.LOADING -> {
                    if (layoutNoData.isVisible)
                        layoutNoData.isVisible = false
                    if (isPaging)
                        prcDialog.isVisible = true
                    else {
                        if (!rclItems.isVisible)
                            rclItems.isVisible = true
                        rclItems.showLoading()
                    }
                }
            }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        edtSearch.setText("")
        listener.onDismiss()
    }

    fun showNoData() {
        rclItems.isVisible = false
        layoutSearch.isVisible = false
        layoutNoData.isVisible = true
    }

    interface OnActionListener<T> {
        fun onSearchPaging(count: Int, search: String)
        fun onChoose(model: DynamicModel<T>)
        fun onDismiss()
    }

    enum class CallbackStatus {
        SEARCH,
        PAGING,
        NONE
    }

    companion object {
        val TAG = "module: ${SearchablePagingDialog::class.java.simpleName}"
    }
}