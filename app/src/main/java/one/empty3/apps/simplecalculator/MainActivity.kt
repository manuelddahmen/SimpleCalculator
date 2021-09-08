package one.empty3.apps.simplecalculator
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import one.empty3.apps.simplecalculator.R
import one.empty3.apps.tree.altree.*;
import java.lang.Exception

class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            val buttonsNumbers = arrayListOf<Int>(R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.dotButton,
                R.id.divideButton, R.id.multButton, R.id.addButton, R.id.substractButton, R.id.expButton, R.id.sqrtButton
            )

            val textAnswer : EditText = findViewById<EditText>(R.id.answerText)
            val editText = findViewById<EditText>(R.id.editTextCalculus)

            var j:Int = 0

            for(j in buttonsNumbers) {
                findViewById<Button>(j)!!.setOnClickListener {
                    editText.setText(editText.getText().append(findViewById<Button>(j)!!.getText()))
                    println(j)
                    val tree: AlgebricTree = AlgebricTree(editText.text.toString());

                    try {
                        tree.construct()
                        val d: Double = tree.eval()
                        val labelAnswer: String = d.toString()
                        textAnswer.setText(labelAnswer)

                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }

                }
            }
        }
}
