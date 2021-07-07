package ir.awlrhm.awlrhmmodule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import ir.awrhm.awlrhmmodule.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      spinner.setOnClickListener {
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
