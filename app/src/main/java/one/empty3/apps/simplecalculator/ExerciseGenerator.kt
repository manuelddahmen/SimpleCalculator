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

import kotlin.random.Random

/**
 * Niveau de difficulté ciblé par un exercice (trois paliers).
 *
 * - [TODDLER] : moins de 5 ans — compter des objets.
 * - [KID]     : 6-7 ans — additions et soustractions simples.
 * - [JUNIOR]  : 8-9 ans — multiplications et opérations plus grandes.
 *
 * L'ordre des constantes est utilisé tel quel par l'interface (boutons et
 * sélecteur parental) ; ne pas le réordonner sans adapter l'activité.
 */
enum class AgeTier { TODDLER, KID, JUNIOR }

/**
 * Un exercice à choix multiples, généré localement (aucun appel réseau).
 *
 * @param prompt énoncé textuel (ex. "3 + 4 = ?"). Vide pour les tout-petits :
 *   l'écran affiche alors un libellé fixe ("Compte les objets").
 * @param visual chaîne d'émojis à compter, ou vide.
 * @param answer la bonne réponse.
 * @param choices propositions mélangées, incluant la bonne réponse.
 */
data class Exercise(
    val prompt: String,
    val visual: String,
    val answer: Int,
    val choices: List<Int>,
)

/**
 * Génère des exercices d'arithmétique adaptés à l'âge, hors-ligne.
 *
 * Le [random] est injectable pour rendre les tests déterministes.
 */
class ExerciseGenerator(private val random: Random = Random.Default) {

    private val toddlerEmojis = listOf("🍎", "🐱", "⭐", "🐟", "🌸", "🚗", "🍌", "🐸", "🦋", "🍓")
    private val multiplicationTables = listOf(2, 3, 4, 5, 10)

    fun generate(tier: AgeTier): Exercise = when (tier) {
        AgeTier.TODDLER -> generateToddler()
        AgeTier.KID -> generateKid()
        AgeTier.JUNIOR -> generateJunior()
    }

    /** Tout-petits : compter de 1 à 5 objets identiques. 3 propositions. */
    private fun generateToddler(): Exercise {
        val count = random.nextInt(1, 6) // 1..5
        val emoji = toddlerEmojis[random.nextInt(toddlerEmojis.size)]
        return Exercise(
            prompt = "",
            visual = emoji.repeat(count),
            answer = count,
            choices = buildChoices(answer = count, wanted = 3, min = 1, max = 5, spread = 2),
        )
    }

    /** 6-7 ans : addition (0..10 + 0..10) ou soustraction (résultat ≥ 0). 4 propositions. */
    private fun generateKid(): Exercise {
        val isAddition = random.nextBoolean()
        val a: Int
        val b: Int
        val answer: Int
        val symbol: String
        if (isAddition) {
            a = random.nextInt(0, 11)
            b = random.nextInt(0, 11)
            answer = a + b
            symbol = "+"
        } else {
            a = random.nextInt(0, 21)
            b = random.nextInt(0, a + 1) // garantit un résultat ≥ 0
            answer = a - b
            symbol = "−" // signe moins typographique, plus lisible
        }
        return Exercise(
            prompt = "$a $symbol $b = ?",
            visual = "",
            answer = answer,
            choices = buildChoices(answer = answer, wanted = 4, min = 0, max = 20, spread = 3),
        )
    }

    /** 8-9 ans : multiplication (tables courantes) ou add/sous plus grandes. 4 propositions. */
    private fun generateJunior(): Exercise {
        return if (random.nextBoolean()) {
            val table = multiplicationTables[random.nextInt(multiplicationTables.size)]
            val factor = random.nextInt(1, 11) // 1..10
            val answer = table * factor
            Exercise(
                prompt = "$table × $factor = ?",
                visual = "",
                answer = answer,
                choices = buildChoices(answer = answer, wanted = 4, min = 0, max = 120, spread = 6),
            )
        } else {
            val isAddition = random.nextBoolean()
            val a: Int
            val b: Int
            val answer: Int
            val symbol: String
            if (isAddition) {
                a = random.nextInt(0, 51)
                b = random.nextInt(0, 51)
                answer = a + b
                symbol = "+"
            } else {
                a = random.nextInt(0, 51)
                b = random.nextInt(0, a + 1)
                answer = a - b
                symbol = "−"
            }
            Exercise(
                prompt = "$a $symbol $b = ?",
                visual = "",
                answer = answer,
                choices = buildChoices(answer = answer, wanted = 4, min = 0, max = 100, spread = 6),
            )
        }
    }

    /**
     * Construit [wanted] propositions distinctes contenant [answer], les autres
     * étant proches de la réponse (± [spread]) et bornées à [min]..[max], puis
     * mélangées.
     */
    private fun buildChoices(answer: Int, wanted: Int, min: Int, max: Int, spread: Int): List<Int> {
        val choices = linkedSetOf(answer)
        var guard = 0
        while (choices.size < wanted && guard < 200) {
            guard++
            val candidate = (answer + random.nextInt(-spread, spread + 1)).coerceIn(min, max)
            choices.add(candidate)
        }
        // Filet de sécurité si l'intervalle est trop étroit pour le spread.
        var v = min
        while (choices.size < wanted && v <= max) {
            choices.add(v)
            v++
        }
        return choices.toList().shuffled(random)
    }
}
