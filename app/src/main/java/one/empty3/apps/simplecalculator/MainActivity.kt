package one.empty3.apps.simplecalculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import one.empty3.apps.tree.altree.AlgebraicFormulaSyntaxException
import one.empty3.apps.tree.altree.AlgebricTree

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout_table)
        val buttonsNumbers = arrayListOf(
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
            R.id.delButton,
            R.id.buttonParenthesis,
            R.id.buttonParenthesisA
        )

        val textAnswer: TextView = findViewById<EditText>(R.id.answerText)
        val editText = findViewById<EditText>(R.id.editTextCalculus)

        for (j: Int in buttonsNumbers) {
            val findViewById: Button = findViewById(j)
            findViewById.setOnClickListener {
                if (findViewById == findViewById<Button>(R.id.delButton)) {
                    val toString: String = editText.text.toString()
                    if (toString.length > 1) {
                        editText.setText(editText.text.substring(0, toString.length - 1))
                    } else if (toString.length == 1) {
                        editText.setText("")
                    }
                    return@setOnClickListener
                }
                editText.text = editText.text.append(findViewById.text)
                val tree = AlgebricTree(editText.text.toString())

                try {
                    tree.construct()
                    val d: Double = tree.eval()
                    val labelAnswer: String = d.toString()
                    textAnswer.text = labelAnswer
                    Toast.makeText(applicationContext, "Valide V", Toast.LENGTH_LONG).show()

                } catch (ex: AlgebraicFormulaSyntaxException) {
                    //Toast.makeText(getApplicationContext(), "Syntaxe invalide", Toast.LENGTH_SHORT).show()
                } catch (ex: IndexOutOfBoundsException) {
                    //Toast.makeText(getApplicationContext(), "Erreur autre (array index)", Toast.LENGTH_SHORT).show()
                    ex.printStackTrace()
                } catch (ex: NullPointerException) {
                    //Toast.makeText(getApplicationContext(), "Erreur : null", Toast.LENGTH_SHORT).show()
                    ex.printStackTrace()
                }
            }
        }
        val buttonFunctionAdd: Button = findViewById(R.id.buttonFunction)
        buttonFunctionAdd.setOnClickListener {
            // Create an instance of the dialog fragment and show it
            val dialog = ChooseFunctionDialogFragment()
            dialog.show(
                getSupportFragmentManager(),
                "one.empty3.apps.simplecalculator.ChooseFunctionDialogFragment"
            )
            editText.text = editText.text.append(dialog.function)

        }
        findViewById<Button>(R.id.AboutButton).setOnClickListener {
            openUserData(it)
        }
    }


    private fun openUserData(view: View) {
        val intent: Intent = Intent(view.context, LicenceUserData::class.java).apply {
            putExtra("class", "")
        }
        startActivity(intent)
    }
}
