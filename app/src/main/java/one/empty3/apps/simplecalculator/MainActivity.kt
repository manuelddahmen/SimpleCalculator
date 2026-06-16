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


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.preference.PreferenceManager
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.PurchasesUpdatedListener
import com.google.android.play.agesignals.AgeSignalsManagerFactory
import com.google.android.play.agesignals.AgeSignalsRequest
import com.google.android.play.agesignals.model.AgeSignalsVerificationStatus
import one.empty3.library.StructureMatrix
import one.empty3.library1.tree.AlgebraicTree

class MainActivity : AppCompatActivity() {

    private lateinit var purchasesUpdatedListener: PurchasesUpdatedListener
    private lateinit var billingClient: BillingClient
    private var isKidsMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        
        // Priority: Check if age selection is locked in settings
        val isLocked = prefs.getBoolean("lock_age", false)
        if (isLocked) {
            val agePref = prefs.getString("user_age", "standard")
            isKidsMode = agePref == "kids"
        } else {
            isKidsMode = prefs.getBoolean("kids_mode_active", false)
        }

        setupUI()
        if (!isLocked) {
            checkAgeSignals()
        }
        initBilling()
    }

    private fun setupUI() {
        if (isKidsMode) {
            setContentView(R.layout.kids_layout_table)
        } else {
            setContentView(R.layout.main_layout_table)
        }

        val yourRootView = findViewById<View>(R.id.root_activity_calc)
        ViewCompat.setOnApplyWindowInsetsListener(yourRootView) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                left = insets.left,
                top = insets.top,
                right = insets.right,
                bottom = insets.bottom
            )
            WindowInsetsCompat.CONSUMED
        }

        val textAnswer: EditText = findViewById(R.id.answerText)
        val editText: EditText = findViewById(R.id.editTextCalculus)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        editText.setText("")
        val savedText = prefs.getString("autoSaveEditText", "")
        if (!savedText.isNullOrEmpty()) {
            editText.setText(savedText)
            val tree = AlgebraicTree(savedText)
            compute(tree, textAnswer)
        }

        val buttonsNumbers = arrayListOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
            R.id.dotButton, R.id.equalButton, R.id.divideButton, R.id.multButton,
            R.id.addButton, R.id.substractButton, R.id.expButton, R.id.delButton,
            R.id.buttonParenthesis, R.id.buttonParenthesisA, R.id.buttonComa
        )

        for (id in buttonsNumbers) {
            findViewById<Button>(id)?.setOnClickListener { btn ->
                val button = btn as Button
                editText.requestFocus()
                if (id == R.id.delButton) {
                    handleDelete(editText, textAnswer)
                } else {
                    handleInsert(button.text.toString(), editText, textAnswer)
                }
            }
        }

        // Standard Mode only buttons
        findViewById<Button>(R.id.buttonFunction)?.setOnClickListener {
            showFunctionDialog(editText, textAnswer, false)
        }
        findViewById<Button>(R.id.buttonFunctionMultiple)?.setOnClickListener {
            showFunctionDialog(editText, textAnswer, true)
        }
        findViewById<Button>(R.id.your_button_id_to_open_graph_activity)?.setOnClickListener {
            startActivity(Intent(this, GraphActivity::class.java))
        }
        findViewById<Button>(R.id.textCalculatorButton)?.setOnClickListener {
            startActivity(Intent(this, ScrollingActivity::class.java))
        }

        // Kids Mode only: open the exercises prototype
        findViewById<Button>(R.id.kidsExercisesButton)?.setOnClickListener {
            startActivity(Intent(this, KidsExercisesActivity::class.java))
        }

        // Common buttons
        findViewById<Button>(R.id.AboutButton)?.setOnClickListener {
            openUserData(it)
        }

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        toolbar?.inflateMenu(R.menu.menu_main)
        toolbar?.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_help) {
                showHelp()
                true
            } else {
                false
            }
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val toString = s.toString()
                compute(AlgebraicTree(toString), textAnswer)
                prefs.edit().putString("autoSaveEditText", toString).apply()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun handleDelete(editText: EditText, textAnswer: EditText) {
        val toString = editText.text.toString()
        if (toString.length > 1) {
            val start = editText.selectionStart
            val end = editText.selectionEnd
            if (start == end) {
                editText.text.replace(Math.max(start - 1, 0), start, "")
            } else {
                editText.text.replace(Math.min(start, end), Math.max(start, end), "")
            }
        } else {
            editText.setText("")
        }
        compute(AlgebraicTree(editText.text.toString()), textAnswer)
    }

    private fun handleInsert(text: String, editText: EditText, textAnswer: EditText) {
        val start = editText.selectionStart
        val end = editText.selectionEnd
        editText.text.replace(Math.min(start, end), Math.max(start, end), text)
        compute(AlgebraicTree(editText.text.toString()), textAnswer)
    }

    private fun showFunctionDialog(editText: EditText, textAnswer: EditText, isMultiple: Boolean) {
        val dialog = if (isMultiple) ChooseFxDialogFragment() else ChooseFunctionDialogFragment()
        dialog.show(supportFragmentManager, dialog.javaClass.name)
        
        Thread {
            while (if (isMultiple) !(dialog as ChooseFxDialogFragment).isExited else !(dialog as ChooseFunctionDialogFragment).isExited) {
                Thread.sleep(100)
            }
            runOnUiThread {
                val result = if (isMultiple) (dialog as ChooseFxDialogFragment).functionName else (dialog as ChooseFunctionDialogFragment).functionName
                if (result.isNotEmpty()) {
                    handleInsert(result, editText, textAnswer)
                }
            }
        }.start()
    }

    private fun checkAgeSignals() {
        val ageSignalsManager = AgeSignalsManagerFactory.create(applicationContext)
        ageSignalsManager.checkAgeSignals(AgeSignalsRequest.builder().build())
            .addOnSuccessListener { ageSignalsResult ->
                val status = ageSignalsResult.userStatus()
                val upperRange = ageSignalsResult.ageUpper()
                
                // Trigger Kids Mode if user is supervised OR verified as 12 or under
                val shouldBeKidsMode = (status == AgeSignalsVerificationStatus.SUPERVISED || 
                                       (upperRange != null && upperRange <= 12))
                
                val prefs = PreferenceManager.getDefaultSharedPreferences(this)
                if (shouldBeKidsMode != isKidsMode) {
                    isKidsMode = shouldBeKidsMode
                    prefs.edit().putBoolean("kids_mode_active", isKidsMode).apply()
                    // Recreate to apply new layout
                    recreate()
                }
            }
    }

    private fun initBilling() {
        purchasesUpdatedListener = PurchasesUpdatedListener { _, _ -> }
        billingClient = BillingClient.newBuilder(applicationContext)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases(PendingPurchasesParams.newBuilder()
                .enablePrepaidPlans().enableOneTimeProducts().build())
            .build()
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) { }
            override fun onBillingServiceDisconnected() { }
        })
    }

    private fun compute(tree: AlgebraicTree, textAnswer: EditText) {
        try {
            tree.construct()
            val d: StructureMatrix<Double>? = tree.eval()
            val str = stringFromEval(d)
            runOnUiThread {
                textAnswer.setText(str)
                textAnswer.setTextColor(Color.GRAY)
            }
        } catch (ex: Exception) {
            runOnUiThread {
                textAnswer.setTextColor(Color.RED)
            }
        }
    }

    private fun stringFromEval(d: StructureMatrix<Double>?): String {
        if (d == null) return ""
        if (d.dim == 0) return "" + (d.getElem() ?: 0.0)
        if (d.dim == 1 && d.data1d != null) {
            return d.data1d.joinToString(prefix = "(", postfix = ")", separator = ",")
        }
        return ""
    }

    private fun openUserData(view: View) {
        startActivity(Intent(view.context, PrivacyPolicyActivity::class.java))
    }

    private fun showHelp() {
        val helpResId = if (isKidsMode) R.raw.help_kids else R.raw.help_standard
        val helpText = try {
            resources.openRawResource(helpResId).bufferedReader().use { it.readText() }
        } catch (_: Exception) {
            "Help content unavailable."
        }

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(R.string.dialog_help_title)
            .setMessage(helpText)
            .setPositiveButton(R.string.ok_button, null)
            .show()
    }
}
