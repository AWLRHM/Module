package ir.awlrhm.awlrhmmodule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.modules.extensions.showDatePicker
import ir.awrhm.awlrhmmodule.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      spinner.setOnClickListener { spinner.loading(true) }
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
