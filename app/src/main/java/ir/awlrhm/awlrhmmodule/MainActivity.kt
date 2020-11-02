package ir.awlrhm.awlrhmmodule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.awlrhm.modules.extensions.showDatePicker
import ir.awlrhm.modules.extensions.showSecurityErrorDialog
import ir.awrhm.awlrhmmodule.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        picker.setOnClickListener {
            showDatePicker{

            }
        }
    }
}
