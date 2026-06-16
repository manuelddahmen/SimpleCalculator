/*
 * Copyright (c) 2026.
 *
 *  Copyright 2012-2026 Manuel Daniel Dahmen
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
 */

package one.empty3.apps.simplecalculator

import android.animation.ObjectAnimator
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlin.random.Random

/**
 * Exercices pour enfants — contenu généré localement, trois niveaux.
 *
 * Tout-petits (compter), 6-7 ans (add/sous) et 8-9 ans (multiplications).
 * Retour sonore et animations à chaque réponse. Étoiles et niveau par défaut
 * conservés dans les SharedPreferences ; le niveau par défaut se règle via une
 * petite « porte parentale » (une multiplication à résoudre).
 */
class KidsExercisesActivity : AppCompatActivity() {

    private val generator = ExerciseGenerator()
    private var tier = AgeTier.TODDLER
    private var current: Exercise? = null
    private var stars = 0
    private var streak = 0

    private var toneGenerator: ToneGenerator? = null

    private lateinit var promptText: TextView
    private lateinit var visualText: TextView
    private lateinit var feedbackText: TextView
    private lateinit var starsText: TextView
    private lateinit var toggle: MaterialButtonToggleGroup
    private lateinit var choiceButtons: List<MaterialButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kids_exercises)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        stars = prefs.getInt(PREF_STARS, 0)
        tier = readTier(prefs.getString(PREF_DEFAULT_TIER, AgeTier.TODDLER.name))

        toneGenerator = runCatching { ToneGenerator(AudioManager.STREAM_MUSIC, TONE_VOLUME) }.getOrNull()

        findViewById<MaterialToolbar>(R.id.kidsToolbar).apply {
            setNavigationOnClickListener { finish() }
            inflateMenu(R.menu.menu_kids_exercises)
            setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.action_parents) {
                    showParentalGate(); true
                } else false
            }
        }

        promptText = findViewById(R.id.kidsPrompt)
        visualText = findViewById(R.id.kidsVisual)
        feedbackText = findViewById(R.id.kidsFeedback)
        starsText = findViewById(R.id.kidsStars)
        choiceButtons = listOf(
            findViewById(R.id.kidsChoice0),
            findViewById(R.id.kidsChoice1),
            findViewById(R.id.kidsChoice2),
            findViewById(R.id.kidsChoice3),
        )
        choiceButtons.forEach { button ->
            button.setOnClickListener {
                val pressed = it as MaterialButton
                onChoice(pressed, pressed.text.toString().toIntOrNull())
            }
        }

        toggle = findViewById(R.id.kidsTierToggle)
        toggle.check(buttonIdFor(tier))
        toggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                tier = tierFor(checkedId)
                nextExercise()
            }
        }

        updateStars()
        nextExercise()
    }

    override fun onDestroy() {
        toneGenerator?.release()
        toneGenerator = null
        super.onDestroy()
    }

    private fun nextExercise() {
        feedbackText.text = ""
        val exercise = generator.generate(tier).also { current = it }

        promptText.text = when (tier) {
            AgeTier.TODDLER -> getString(R.string.kids_ex_prompt_count)
            else -> exercise.prompt
        }
        visualText.text = exercise.visual
        visualText.visibility = if (exercise.visual.isEmpty()) View.GONE else View.VISIBLE

        choiceButtons.forEachIndexed { index, button ->
            if (index < exercise.choices.size) {
                button.text = exercise.choices[index].toString()
                button.isEnabled = true
                button.visibility = View.VISIBLE
            } else {
                button.visibility = View.GONE
            }
        }
    }

    private fun onChoice(button: MaterialButton, value: Int?) {
        val exercise = current ?: return
        if (value == null) return

        if (value == exercise.answer) {
            stars++
            streak++
            updateStars()
            PreferenceManager.getDefaultSharedPreferences(this)
                .edit().putInt(PREF_STARS, stars).apply()
            playTone(ToneGenerator.TONE_PROP_BEEP)
            feedbackText.text = getString(R.string.kids_ex_correct)
            popIn(feedbackText)
            choiceButtons.forEach { it.isEnabled = false }
            feedbackText.postDelayed({ nextExercise() }, AUTO_NEXT_DELAY_MS)
        } else {
            streak = 0
            updateStars()
            playTone(ToneGenerator.TONE_PROP_NACK)
            feedbackText.text = getString(R.string.kids_ex_try_again)
            shake(button)
        }
    }

    private fun updateStars() {
        starsText.text = getString(R.string.kids_ex_stars, stars, streak)
    }

    // --- Retour sensoriel -------------------------------------------------

    private fun playTone(toneType: Int) {
        runCatching { toneGenerator?.startTone(toneType, TONE_DURATION_MS) }
    }

    /** Apparition « rebond » : utilisée pour féliciter. */
    private fun popIn(view: View) {
        view.scaleX = 0.6f
        view.scaleY = 0.6f
        view.alpha = 0f
        view.animate()
            .scaleX(1f).scaleY(1f).alpha(1f)
            .setInterpolator(OvershootInterpolator())
            .setDuration(320)
            .start()
    }

    /** Secousse horizontale : signale une mauvaise réponse. */
    private fun shake(view: View) {
        ObjectAnimator.ofFloat(
            view, View.TRANSLATION_X,
            0f, 24f, -24f, 16f, -16f, 8f, -8f, 0f
        ).setDuration(400).start()
    }

    // --- Sélecteur parental ----------------------------------------------

    /** Petit verrou : il faut résoudre une multiplication pour ouvrir les réglages. */
    private fun showParentalGate() {
        val a = Random.nextInt(6, 10)
        val b = Random.nextInt(6, 10)
        val input = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_NUMBER
            hint = "?"
        }
        AlertDialog.Builder(this)
            .setTitle(R.string.kids_ex_parent_gate_title)
            .setMessage(getString(R.string.kids_ex_parent_gate_q, a, b))
            .setView(input)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                if (input.text.toString().toIntOrNull() == a * b) {
                    showParentSettings()
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun showParentSettings() {
        val labels = arrayOf(
            getString(R.string.kids_ex_tier_toddler),
            getString(R.string.kids_ex_tier_kid),
            getString(R.string.kids_ex_tier_junior),
        )
        var selected = tier.ordinal
        AlertDialog.Builder(this)
            .setTitle(R.string.kids_ex_parent_settings_title)
            .setSingleChoiceItems(labels, selected) { _, which -> selected = which }
            .setPositiveButton(R.string.kids_ex_save) { _, _ ->
                val chosen = AgeTier.values()[selected]
                PreferenceManager.getDefaultSharedPreferences(this)
                    .edit().putString(PREF_DEFAULT_TIER, chosen.name).apply()
                toggle.check(buttonIdFor(chosen)) // déclenche nextExercise via le listener
            }
            .setNeutralButton(R.string.kids_ex_reset_stars) { _, _ ->
                stars = 0
                streak = 0
                updateStars()
                PreferenceManager.getDefaultSharedPreferences(this)
                    .edit().putInt(PREF_STARS, 0).apply()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    // --- Correspondances niveau <-> bouton du toggle ----------------------

    private fun buttonIdFor(tier: AgeTier): Int = when (tier) {
        AgeTier.TODDLER -> R.id.kidsTierToddler
        AgeTier.KID -> R.id.kidsTierKid
        AgeTier.JUNIOR -> R.id.kidsTierJunior
    }

    private fun tierFor(buttonId: Int): AgeTier = when (buttonId) {
        R.id.kidsTierKid -> AgeTier.KID
        R.id.kidsTierJunior -> AgeTier.JUNIOR
        else -> AgeTier.TODDLER
    }

    private fun readTier(name: String?): AgeTier =
        runCatching { AgeTier.valueOf(name ?: AgeTier.TODDLER.name) }.getOrDefault(AgeTier.TODDLER)

    companion object {
        private const val PREF_STARS = "kids_exercises_stars"
        private const val PREF_DEFAULT_TIER = "kids_exercises_default_tier"
        private const val AUTO_NEXT_DELAY_MS = 900L
        private const val TONE_VOLUME = 80
        private const val TONE_DURATION_MS = 180
    }
}
