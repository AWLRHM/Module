package ir.awlrhm.awlrhmmodule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.modules.extensions.showDatePicker
import ir.awrhm.awlrhmmodule.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var paging = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        picker.setOnClickListener {
            showDatePicker {

            }
        }
        rclItem.layoutManager(LinearLayoutManager(this))
        rclItem.view?.adapter = Adapter(
            mutableListOf(
                TestModel("test 1"),
                TestModel("test 2")
            )
        )
        btn.setOnClickListener {
            if (paging) {
                rclItem.paging(false)
                paging = false
            }else{
                rclItem.paging(true)
                paging = true
            }
        }
    }
}
