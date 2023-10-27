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

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import one.empty3.apps.simplecalculator.databinding.ActivityScrollingBinding
import one.empty3.library1.tree.ListInstructions


/*
    c=1
    b=c+4
    a=c/b
 */
class ScrollingActivity : AppCompatActivity() {

    private var text: EditText? = null
    private lateinit var binding: ActivityScrollingBinding
    private var variables = ListInstructions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        text = findViewById<EditText>(R.id.textCalculator)

        binding.fab.setOnClickListener { view ->
            parseText(text!!.text.toString())
        }

        val prefs: SharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(this)

        text!!.setText(prefs.getString("autoSave", "pi="+Math.PI))

        text!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                prefs.edit().putString("autoSave", s.toString()).apply()
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun parseText(textIns: String) {
        try {
            variables = ListInstructions()
            variables.addInstructions(textIns)
            val errors = variables.runInstructions()

            var strNewText : String = ""

            errors.forEach {
                if(it!=null && !it.startsWith("##")) {
                    strNewText+= ""+('\n')+it+"\n"
                }
            }

            text!!.setText(strNewText)

        } catch (ex : RuntimeException) {
            ex.printStackTrace()
        } catch (ex : NullPointerException) {
            ex.printStackTrace()
        }
    }

}