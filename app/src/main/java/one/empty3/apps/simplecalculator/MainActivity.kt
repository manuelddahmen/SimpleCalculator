package one.empty3.apps.simplecalculator
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            var buttonsNumbers = arrayListOf<Int>(R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                    R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9)
            var j:Int=0

            val editText = findViewById<EditText>(R.id.editTextCalculus)

            for (i:Int in buttonsNumbers) {
                val buttonActive = findViewById<Button>(i)
                buttonActive.setOnClickListener({
                    editText.text = editText.text.append(""+j)
                    println("Number on button : "+j)
                })
                j=j+1
            }
            setVisible(true)
        }
}
