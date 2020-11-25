package ir.awlrhm.modules.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import ir.awlrhm.modules.extensions.configProgressbar
import ir.awrhm.modules.R
import ir.awrhm.modules.utils.GridItemDecoration
import ir.financialworld.masterstock.utils.VerticalItemDecoration
import kotlinx.android.synthetic.main.awlrhm_recycler_view.view.*
import kotlinx.android.synthetic.main.loading.view.*

class RecyclerView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private var recyclerView: RecyclerView? = null
    private var progress: ProgressBar? = null
    private var noData: View? = null
    private var btnRetry: MaterialButton? = null
    var isOnLoading: Boolean = true
    private var listener: OnActionListener?= null

    init {
        val view = View.inflate(context, R.layout.awlrhm_recycler_view, this)
        recyclerView = view.findViewById(R.id.recyclerView)
        progress = view.findViewById(R.id.recyclerProgress)
        noData = view.findViewById(R.id.noData)
        btnRetry = view.findViewById(R.id.btnRetry)
    }

    private fun configRecyclerView(
        layoutManager: LinearLayoutManager
    ) {
        isOnLoading = true
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
    }

    private fun setVerticalItemDecoration(resId: Int = 0) {
        recyclerView?.addItemDecoration(
            VerticalItemDecoration(
                context,
                if (resId == 0) R.drawable.divider else resId
            )

        )
    }

    private fun setGridItemDecoration(horizontalDivider: Int = 0, verticalDevider: Int = 0) {
        recyclerView?.layoutDirection = View.LAYOUT_DIRECTION_LTR
        recyclerView?.addItemDecoration(
            GridItemDecoration(
                context,
                if (horizontalDivider == 0) R.drawable.divider else horizontalDivider,
                if (verticalDevider == 0) R.drawable.divider else verticalDevider
            )
        )
    }

    private fun setAnimation(animation: Int = 0) {
        val controller =
            AnimationUtils.loadLayoutAnimation(
                context,
                if (animation == 0) R.anim.layout_up_to_down else animation
            )
        recyclerView?.layoutAnimation = controller
    }

    val view: RecyclerView?
        get() {
            isOnLoading = false
            progress?.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
            noData?.visibility = View.GONE
            return recyclerView
        }

    fun showNoData() {
        progress?.visibility = View.GONE
        recyclerView?.visibility = View.GONE
        noData?.visibility = View.VISIBLE
    }

    fun showLoading() {
        noData?.visibility = View.GONE
        progress?.visibility = View.VISIBLE
        recyclerView?.visibility = View.INVISIBLE
    }

    fun showActionLoading(visible: Boolean){
        waitLoading.isVisible = visible
    }

    fun clear(){
        recyclerView?.removeAllViews()
    }

    fun onActionRecyclerViewListener(listener: OnRecyclerViewListener) {
        recyclerView?.setOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                listener.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }



    fun verticalDecoration(resId: Int = 0) = apply { setVerticalItemDecoration(resId) }

    fun gridDecoration(horizontalResId: Int = 0, verticalResId: Int = 0) = apply {
        setGridItemDecoration(horizontalResId, verticalResId)
    }

    fun themeColor(color: Int) = apply { progress?.let {
        waitLoading.txtWait.setTextColor(ContextCompat.getColor(context, color))
        context.configProgressbar(waitLoading.prcWait, color)

        context.configProgressbar(it, color)
    }}
    fun layoutManager(manager: LinearLayoutManager) = apply { configRecyclerView(manager) }

    fun animation(animation: Int = 0) = apply { setAnimation(animation) }

    fun onActionListener(listener: OnActionListener) = apply {
        this.listener = listener
    }

    interface OnRecyclerViewListener {
        fun onScrolled(
            recyclerView: RecyclerView,
            dx: Int,
            dy: Int
        )
    }
    interface OnActionListener{
        fun onRefresh()
    }
}