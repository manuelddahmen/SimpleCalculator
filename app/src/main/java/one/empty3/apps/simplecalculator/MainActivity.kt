/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.apps.simplecalculator

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils.replace
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import one.empty3.library.StructureMatrix
import one.empty3.library1.tree.AlgebraicFormulaSyntaxException
import one.empty3.library1.tree.AlgebricTree
import one.empty3.library1.tree.functions.ListMathDoubleFunction

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
            R.id.buttonParenthesisA,
            R.id.buttonComa
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
                    val d: StructureMatrix<Double>? = tree.eval()


                    textAnswer.text = stringFromEval(d)
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
//            val stringFragment : StringFragment= StringFragment()
//                val ft: FragmentTransaction = supportFragmentManager.beginTransaction();
//                ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            val dialog = ChooseFunctionDialogFragment()
            val stringArrayAdapter = StringArrayAdapter()
            dialog.show(
                supportFragmentManager,
                "one.empty3.apps.simplecalculator.ChooseFunctionDialogFragment"
            )
            Thread {
                run {
                    while (!dialog.isExited) {
                        Thread.sleep(100)
                    }

                    val result: String = dialog.function_name
                    if (result.isNotEmpty())
                        editText.text = editText.text.append(result)
                }
            }.start()
        }
        // Create an instance of the dialog fragment and show it
            //val dialog = ChooseFunctionDialogFragment()
            //dialog.show(
            //    getSupportFragmentManager(),
            //    "one.empty3.apps.simplecalculator.ChooseFunctionDialogFragment"
            //)
        //       this.setContentView(R.layout.fragment_item_list)
//            editText.text = editText.text.append(dialog.function)
        findViewById<Button>(R.id.AboutButton).setOnClickListener {
            openUserData(it)
        }
        findViewById<Button>(R.id.textCalculatorButton).setOnClickListener(View.OnClickListener {
            val intentText:Intent = Intent(Intent.ACTION_EDIT)
            intentText.setClass(applicationContext, ScrollingActivity::class.java)
            startActivity(intentText)
        })

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val tree = AlgebricTree(editText.text.toString())

                try {
                    tree.construct()
                    val d: StructureMatrix<Double>? = tree.eval()
                    var labelAnswer: String = stringFromEval(d)
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
            override fun beforeTextChanged(s:CharSequence, start:Int, count:Int, after:Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    private fun stringFromEval(d: StructureMatrix<Double>?): String {
        var labelAnswer: String = ""+ (d?.getElem() ?: 0.0)
        if(d!=null && d.dim==0)
            labelAnswer =""+ d.getElem()
        else if(d!=null &&d.dim==1 && d.data1d!=null) {
            labelAnswer = "("
            for (i in 0 until d.data1d.size) {
                labelAnswer+=d.data1d[i]
                if(i<d.data1d.size-1)
                    labelAnswer+=","
            }
            labelAnswer+=")"
        }
        return labelAnswer
    }


    private fun openUserData(view: View) {
        val intent: Intent = Intent(view.context, LicenceUserData::class.java).apply {
            putExtra("class", "")
        }
        startActivity(intent)
    }
}
