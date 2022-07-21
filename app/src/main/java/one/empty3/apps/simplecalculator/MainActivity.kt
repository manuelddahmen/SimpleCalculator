package one.empty3.apps.simplecalculator

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import one.empty3.apps.simplecalculator.R
import one.empty3.apps.tree.altree.*;
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonsNumbers = arrayListOf<Int>(
            R.id.button0,
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
            R.id.dotButton,
            R.id.equalButton,
            R.id.divideButton,
            R.id.multButton,
            R.id.addButton,
            R.id.substractButton,
            R.id.expButton,
            R.id.delButton
        )

        val textAnswer: TextView = findViewById<EditText>(R.id.answerText)
        val editText = findViewById<EditText>(R.id.editTextCalculus)

        for (j: Int in buttonsNumbers) {
            if (j != null) {
                val findViewById: Button = findViewById<Button>(j)
                findViewById!!.setOnClickListener {
                    if (findViewById == findViewById<Button>(R.id.delButton)) {
                        val toString: String = editText.text.toString()
                        if (toString.length > 1) {
                            editText.setText(editText.text.substring(0, toString.length - 1))
                        } else if (toString.length == 1) {
                            editText.setText("")
                        }
                        return@setOnClickListener
                    }
                    editText.setText(editText.text.append(findViewById.text))
                    val tree: AlgebricTree = AlgebricTree(editText.text.toString());

                    try {
                        tree.construct()
                        val d: Double = tree.eval()
                        val labelAnswer: String = d.toString()
                        textAnswer.text = labelAnswer

                    } catch (ex: AlgebraicFormulaSyntaxException) {

                    } catch (ex: kotlin.IndexOutOfBoundsException) {
                        ex.printStackTrace()
                    } catch (ex: kotlin.NullPointerException) {
                        ex.printStackTrace()
                    }
                }
            }
        }
        findViewById<Button>(R.id.AboutButton).setOnClickListener { it ->
            openUserData(it)
        }
    }

    private fun openUserData(view: View) {
        val message: String = ""
        val intent: Intent = Intent(view.context, LicenceUserData::class.java).apply {
            putExtra("class", "")
        }
        startActivity(intent)
    }
}
