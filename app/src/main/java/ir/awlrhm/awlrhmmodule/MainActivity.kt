package ir.awlrhm.awlrhmmodule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.modules.extensions.showDatePicker
import ir.awlrhm.modules.extensions.yToast
import ir.awlrhm.modules.fingerprint.FingerPrint
import ir.awrhm.awlrhmmodule.R
import ir.awrhm.modules.enums.MessageStatus
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        picker.setOnClickListener {
            showDatePicker{

            }
        }
        rclItem.layoutManager(LinearLayoutManager(this))
        rclItem.view?.adapter = Adapter(mutableListOf(
            TestModel("test 1"),
            TestModel("test 2")
        ))
        btn.setOnClickListener {
            val fingerPrint = FingerPrint(this, object : FingerPrint.OnActionListener{
                override fun onSuccess() {
                    yToast("ورود با موفقیت انجام شد", MessageStatus.SUCCESS)
                }
            })
            if(fingerPrint.hasFingerPrint)
                fingerPrint.use()
            else
                yToast("no Fingerprint", MessageStatus.ERROR)
        }
    }
}
