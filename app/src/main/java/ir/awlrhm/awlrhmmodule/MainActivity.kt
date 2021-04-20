package ir.awlrhm.awlrhmmodule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.modules.extensions.showDatePicker
import ir.awlrhm.modules.models.DynamicModel
import ir.awlrhm.modules.view.searchablePagingDialog.SearchablePagingDialog
import ir.awrhm.awlrhmmodule.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      spinner.setOnClickListener { SearchablePagingDialog(
          object : SearchablePagingDialog.OnActionListener<String>{
              override fun onSearchPaging(pageNumber: Int, search: String) {
                  
              }

              override fun onChoose(model: DynamicModel<String>) {
                  
              }

              override fun onDismiss() {
                  
              }
          }
      ).show(supportFragmentManager, SearchablePagingDialog.TAG)
      }
        rclItem
            .layoutManager(GridLayoutManager(this,2))
            .theme(R.color.pink_500)

        rclItem.view?.adapter = Adapter(
            mutableListOf(
                TestModel("test 1"),
                TestModel("test 2")
            )
        )
        btn.setOnClickListener {
           rclItem.actionLoading = true
        }
    }
}
