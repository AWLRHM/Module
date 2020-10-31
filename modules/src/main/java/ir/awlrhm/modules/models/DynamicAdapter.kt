package ir.awlrhm.modules.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.awrhm.modules.R
import kotlinx.android.synthetic.main.awlrhm_item_dialog.view.*

class DynamicAdapter<T>(
    private val callback: (DynamicModel<T>) -> Unit
) : RecyclerView.Adapter<DynamicAdapter<T>.CustomViewHolder>() {

    constructor(
        list: MutableList<DynamicModel<T>>,
        callback: (DynamicModel<T>) -> Unit
    ) : this(callback) {
        this.list = list
    }

    private var list: MutableList<DynamicModel<T>> = mutableListOf()

    fun setSource(listItem: MutableList<DynamicModel<T>>) {
        list = listItem
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.awlrhm_item_dialog, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(model: DynamicModel<T>) {
            itemView.txtName.text = model.title
            itemView.setOnClickListener {
                callback.invoke(model)
            }
        }
    }
}