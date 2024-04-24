/*
 * Copyright (c) 2024.
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

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.preference.PreferenceManager
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import one.empty3.library.StructureMatrix
import one.empty3.library1.tree.AlgebraicFormulaSyntaxException
import one.empty3.library1.tree.AlgebraicTree
import java.io.FileInputStream
import java.io.StringBufferInputStream


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout_table)


        val serviceAccount :FileInputStream = FileInputStream("C:\\Users\\manue\\AndroidStudioProjects\\SimpleCalculator\\app\\simplecalculator-8d9b3-firebase-adminsdk-e04tz-e538e983e2.json");
        val options : FirebaseOptions = FirebaseOptions.Builder().setProjectId("simplecalculator-8d9b3").setApiKey("AIzaSyC7G9o1LEckCJ2jIC8gOc7sF2RZdd22avA")
            .build()
        FirebaseApp.initializeApp(applicationContext, options);



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
        val prefs = PreferenceManager
            .getDefaultSharedPreferences(this)

        val textAnswer: EditText = findViewById<EditText>(R.id.answerText)
        val editText :EditText = findViewById<EditText>(R.id.editTextCalculus)
        editText.setText("")
        var string: String? = prefs.getString("autoSaveEditText", "")
        if (string != null && string.isNotEmpty())
            editText.setText(string)
        if(string!=null&&string.isNotEmpty()) {
            val tree = AlgebraicTree(string)
            compute(tree, textAnswer)
        }

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
                    val tree = AlgebraicTree(editText.text.toString())
                    compute(tree, textAnswer)
                    return@setOnClickListener
                }
                editText.text = editText.text.append(findViewById.text)
                val tree = AlgebraicTree(editText.text.toString())

                compute(tree, textAnswer)
            }
        }
        val buttonFunctionAdd: Button = findViewById(R.id.buttonFunction)
        buttonFunctionAdd.setOnClickListener {
//            val stringFragment : StringFragment= StringFragment()
//                val ft: FragmentTransaction = supportFragmentManager.beginTransaction();
//                ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            val dialog = ChooseFunctionDialogFragment()
            dialog.show(
                supportFragmentManager,
                "one.empty3.apps.simplecalculator.ChooseFunctionDialogFragment"
            )
            Thread {
                run {
                    while (!dialog.isExited) {
                        Thread.sleep(100)
                    }

                    runOnUiThread {
                        val result: String = dialog.function_name
                        if (result.isNotEmpty() && editText!=null) {
                            editText.text = editText.text.append(result)
                        }
                    }
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
            val intentText: Intent = Intent(Intent.ACTION_EDIT)
            intentText.setClass(applicationContext, ScrollingActivity::class.java)
            startActivity(intentText)
        })

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s!=null&&s.toString().isNotEmpty()) {
                    val toString = s.toString()
                    val tree = AlgebraicTree(toString)
                    Thread() {
                    compute(tree, textAnswer)
                    }.start()
                } else if(textAnswer!=null) {
                    runOnUiThread {
                        textAnswer.setText("")
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var toStringSeq = s
                var toString = ""
                if(s==null)
                    toString = ""
                else
                    toString = s.toString()
                if (toString == null)
                    toString = ""
                runOnUiThread {
                    prefs.edit().putString("autoSaveEditText", toString).apply();
                }
            }

        })
    }

    private fun compute(
        tree: AlgebraicTree,
        textAnswer: EditText
    ) {

            runOnUiThread(Runnable() {
            fun run() {
                try {
                    if (tree == null) {
                        runOnUiThread {
                            textAnswer.setText("")
                        }
                        return
                    }
                    tree.construct()
                    val d: StructureMatrix<Double>? = tree.eval()
                    val str = stringFromEval(d)
                    if (str == null || str.equals("")) {
                        runOnUiThread {
                            textAnswer.setText("")
                        }
                        return
                    }
                    runOnUiThread {
                        textAnswer.setText(str)
                        textAnswer.setTextColor(Color.BLACK)
                    }
                    } catch (ex: AlgebraicFormulaSyntaxException) {
                    runOnUiThread {
                        textAnswer.setTextColor(Color.RED)
                        textAnswer.setText("")
                    }
                } catch (ex: IndexOutOfBoundsException) {
                    runOnUiThread {
                           textAnswer.setTextColor(Color.RED)
                        textAnswer.setText("")
                    }
                } catch (ex: NullPointerException) {
                    runOnUiThread {
                        textAnswer.setTextColor(Color.RED)
                        textAnswer.setText("")
                    }
                }
            }
        })
    }

    private fun stringFromEval(d: StructureMatrix<Double>?): String {
        if (d == null)
            return ""
        var labelAnswer: String = "" + (d.getElem() ?: 0.0)
        if (d.dim == 0)
            labelAnswer = "" + d.getElem()
        else if (d.dim == 1 && d.data1d != null) {
            labelAnswer = "("
            for (i in 0 until d.data1d.size) {
                labelAnswer += d.data1d[i]
                if (i < d.data1d.size - 1)
                    labelAnswer += ","
            }
            labelAnswer += ")"
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
