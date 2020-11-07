package ir.nikafarinegan.automation.view.changesDialog

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.awlrhm.modules.view.changesDialog.ReleaseChangeModel
import ir.awrhm.modules.R
import kotlinx.android.synthetic.main.awlrhm_item_changes.view.*
import kotlinx.android.synthetic.main.awlrhm_item_changes_record.view.*

class Adapter(
    private val list: List<ReleaseChangeModel>
): RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.awlrhm_item_changes, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    inner class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        @SuppressLint("SetTextI18n")
        fun onBind(model: ReleaseChangeModel){
            val context = itemView.context

            itemView.txtReleaseTitle.text = " نسخه    ${model.title}"
            context.resources.getStringArray(model.list).forEach {
                val record = LayoutInflater.from(context).inflate(R.layout.awlrhm_item_changes_record, itemView.layoutRecords, false)
                record.txtRecord.text = it
                itemView.layoutRecords.addView(record)
            }
        }
    }
}