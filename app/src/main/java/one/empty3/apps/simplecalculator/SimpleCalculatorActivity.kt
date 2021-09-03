import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import one.empty3.apps.simplecalculator.R

class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
        }
    var editText: EditText = EditText(null)
    var button0: Button = Button(null)
    var button1: Button = Button(null)
    var button2: Button = Button(null)
    var button3: Button = Button(null)
    var button4: Button = Button(null)
    var button5: Button = Button(null)
    var button6: Button = Button(null)
    var button7: Button = Button(null)
    var button8: Button = Button(null)
    var button9: Button = Button(null)
    var dotButton: Button = Button(null)
    var equalButton: Button = Button(null)
    var clearButtn: Button = Button(null)
    var addButton: Button = Button(null)
    var substractButton: Button = Button(null)
    var multiplyButton: Button = Button(null)
    var divideButton: Button = Button(null)

    fun SimpleCalculator() {

    }
    fun main() {
        val simpleCalculator = SimpleCalculator()
    }
}