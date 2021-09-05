package one.empty3.apps.simplecalculator
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import one.empty3.apps.simplecalculator.R

class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            var buttonsNumbers = arrayListOf<Int>(R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9)
            val i:Int = 0;

            var j: Int = 0
            val editText : EditText = findViewById(R.id.editTextCalculus)

            for (i:Int in buttonsNumbers) {
                var buttonActive = findViewById<Button>(i)
                buttonActive.setOnClickListener({
                    editText.setText(editText.getText().append(""+j))
                })
                j = j+1
            }
        }
}
