package ir.awlrhm.modules.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.awrhm.modules.R
import kotlinx.android.synthetic.main.awlrhm_item_dialog.view.*

class Adapter(
    private val list: MutableList<ItemModel>,
    private var callback: (ItemModel) -> Unit
) : RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.awlrhm_item_dialog, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(model: ItemModel) {
            itemView.txtName.text = model.title
            itemView.setOnClickListener {
                callback.invoke(model)
            }
        }
    }
}