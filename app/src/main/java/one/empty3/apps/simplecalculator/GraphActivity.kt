package one.empty3.apps.simplecalculator // Remplacez par votre nom de package réel

import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
// import android.widget.EditText // Plus nécessaire si on utilise TextInputEditText partout
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
// import androidx.core.view.isVisible // Plus utilisé directement, remplacé par View.VISIBLE/GONE
import com.google.android.material.textfield.TextInputEditText
// Assurez-vous d'importer votre fichier R correctement
import one.empty3.apps.simplecalculator.R
import one.empty3.library1.tree.AlgebraicFormulaSyntaxException
import one.empty3.library1.tree.AlgebraicTree
import one.empty3.libs.Image
import java.io.File


class GraphActivity : AppCompatActivity() {
    private lateinit var editTextYMax: TextInputEditText
    private lateinit var editTextYMin: TextInputEditText
    private var imageWidth: Float = 0.0f
    private var xScale: Float = 1.0f
    private var yScale: Float = 0.0f
    private var yMinLogical: Float = 0.0f
    private var imageHeight: Float = 0.0f
    private var xMin: Double = 0.0
    private var xMax: Double = 0.0
    private lateinit var bitmap: Bitmap
    private lateinit var editTextFormulaX: TextInputEditText
    private lateinit var editTextFormulaFx: TextInputEditText
    private lateinit var editTextXMin: TextInputEditText // Nouveau
    private lateinit var editTextXMax: TextInputEditText // Nouveau

    private lateinit var buttonPlot: Button
    private lateinit var imageViewGraph: ImageView
    private lateinit var buttonDownloadImage: Button
    private lateinit var frameLayoutImageView: FrameLayout

    // États de validation pour chaque champ
    private val validationStates = mutableMapOf(
        FIELD_FORMULA_X to false,
        FIELD_FORMULA_FX to false,
        FIELD_X_MIN to false, // Nouveau
        FIELD_X_MAX to false  // Nouveau
    )

    companion object {
        private const val FIELD_FORMULA_X = 1
        private const val FIELD_FORMULA_FX = 3
        private const val FIELD_X_MIN = 4 // Nouveau
        private const val FIELD_X_MAX = 5 // Nouveau
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        title = getString(R.string.title_activity_graph)

        editTextFormulaX = findViewById(R.id.editTextFormulaX)
        editTextFormulaFx = findViewById(R.id.editTextFormulaFx)
        editTextXMin = findViewById(R.id.editTextXMin) // Nouveau
        editTextXMax = findViewById(R.id.editTextXMax) // Nouveau
        editTextYMin = findViewById(R.id.editTextYMin) // Nouveau
        editTextYMax = findViewById(R.id.editTextYMax) // Nouveau

        buttonPlot = findViewById(R.id.buttonPlot)
        imageViewGraph = findViewById(R.id.imageViewGraph)
        buttonDownloadImage = findViewById(R.id.buttonDownloadImage)
        frameLayoutImageView = findViewById(R.id.frameLayoutImageView)

        setupTextWatchers()

        buttonPlot.setOnClickListener {
            plotGraph()
        }

        buttonDownloadImage.setOnClickListener {
            downloadImage()
        }

        // Vérification initiale pour l'état du bouton Plot
        checkAllFieldsValidated()
    }

    private fun setupTextWatchers() {
        editTextFormulaX.addTextChangedListener(
            GenericTextWatcher(
                FIELD_FORMULA_X,
                editTextFormulaX
            )
        )

        editTextFormulaFx.addTextChangedListener(
            GenericTextWatcher(
                FIELD_FORMULA_FX,
                editTextFormulaFx
            )
        )
        editTextXMin.addTextChangedListener(
            GenericTextWatcher(
                FIELD_X_MIN,
                editTextXMin
            )
        ) // Nouveau
        editTextXMax.addTextChangedListener(
            GenericTextWatcher(
                FIELD_X_MAX,
                editTextXMax
            )
        ) // Nouveau
    }

    private fun validate(fieldId: Int, content: String): Boolean {
        var isValid = content.isNotBlank() // Validation de base non vide

        when (fieldId) {
            FIELD_FORMULA_X, FIELD_FORMULA_FX -> {
                // Logique de validation pour les formules (syntaxe, etc.)
                // Pour l'instant, on utilise votre logique existante avec AlgebraicTree
                if (isValid) { // Ne tentez de parser que si ce n'est pas vide
                    try {
                        val aTree = AlgebraicTree(content)
                        aTree.construct()
                        // Optionnel : vous pouvez ajouter plus de vérifications sur aTree ici
                    } catch (e: RuntimeException) {
                        isValid = false
                        // Log.e("GraphActivity", "Validation error for field $fieldId: ${e.message}")
                    } catch (e: AlgebraicFormulaSyntaxException) {

                    }
                }
            }

            FIELD_X_MIN, FIELD_X_MAX -> {
                // Logique de validation pour X Min et X Max (doivent être des nombres valides)
                if (isValid) { // Ne tentez de convertir que si ce n'est pas vide
                    isValid = content.toDoubleOrNull() != null
                }
                if (isValid && fieldId == FIELD_X_MAX) {
                    // Optionnel : Vérifier si X Max > X Min
                    val xMinStr = editTextXMin.text.toString()
                    val xMaxStr = editTextXMax.text.toString()
                    val xMin = xMinStr.toDoubleOrNull()
                    val xMax = xMaxStr.toDoubleOrNull()
                    if (xMin != null && xMax != null && xMax <= xMin) {
                        // isValid = false // Décommentez pour activer cette validation
                        // editTextXMax.error = "X Max must be greater than X Min" // Ou afficher un message d'erreur
                    } else {
                        // editTextXMax.error = null // Effacer l'erreur si valide
                    }
                }
            }
        }

        validationStates[fieldId] = isValid

        val editText = when (fieldId) {
            FIELD_FORMULA_X -> editTextFormulaX
            FIELD_FORMULA_FX -> editTextFormulaFx
            FIELD_X_MIN -> editTextXMin
            FIELD_X_MAX -> editTextXMax
            else -> null
        }
        // Afficher l'erreur seulement si le champ n'est pas valide ET qu'il n'est pas vide
        // (pour éviter d'afficher "Invalid" sur un champ vide dès le début)
        editText?.error = if (!isValid && content.isNotEmpty()) "Invalid input" else null


        checkAllFieldsValidated()
        return isValid
    }

    private fun checkAllFieldsValidated() {
        // Vérifier si tous les états de validation sont true
        val allValid = validationStates.values.all { it }
        buttonPlot.visibility = if (allValid) View.VISIBLE else View.GONE
    }
    fun logicalToScreenX(logicalX: Double): Float {
        return ((logicalX - xMin) * xScale).toFloat()
    }

    fun logicalToScreenY(logicalY: Double): Float {
        // Y-axis is inverted on screen (0 is top, height is bottom)
        return imageHeight - ((logicalY - yMinLogical) * yScale).toFloat()
    }
    private fun plotGraph() {
        val formulaX = editTextFormulaX.text.toString()
        val formulaFx = editTextFormulaFx.text.toString()
        xMin = editTextXMin.text.toString().toDoubleOrNull()
            ?: -10.0 // Valeur par défaut si invalide/vide
        xMax = editTextXMax.text.toString().toDoubleOrNull() ?: 10.0 // Valeur par défaut
        val xRange = xMax - xMin
        yMinLogical =
            (editTextYMin.text.toString().toDoubleOrNull() ?: -10.0).toFloat() // Valeur par défaut  // Example: Set your desired logical Y max
        val yMaxLogical = editTextYMax.text.toString().toDoubleOrNull() ?: 10.0 // Valeur par défaut  // Example: Set your desired logical Y max
        val yRange = yMaxLogical - yMinLogical

        if (xMin >= xMax) {
            Toast.makeText(this, "X Min must be less than X Max.", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(
            this,
            "Plotting: X=$formulaX, Y=$formulaFx, XMin=$xMin, XMax=$xMax",
            Toast.LENGTH_LONG
        ).show()

        try {
            bitmap = Bitmap.createBitmap(
                imageViewGraph.width.coerceAtLeast(100),
                imageViewGraph.height.coerceAtLeast(100),
                Bitmap.Config.ARGB_8888
            )
            imageWidth = bitmap.width.toFloat()
            imageHeight = bitmap.height.toFloat()
            val canvas = android.graphics.Canvas(bitmap)
            val paint = android.graphics.Paint().apply {
                color = android.graphics.Color.BLUE
                strokeWidth = 5f
            }
            canvas.drawColor(android.graphics.Color.WHITE)

            val centerX = bitmap.width / 2f
            val centerY = bitmap.height / 2f
            try {
                val fX = AlgebraicTree(formulaX)
                fX.construct()
                val fXY = AlgebraicTree(formulaFx)
                fXY.construct()

                var x1paint :Float = 0f
                var y1paint :Float = 1f
                var x0paint :Float = 0f
                var y0paint :Float = 1f

                // Inside your plotGraph() method, after you have bitmap dimensions

// Logical range for X

// Assume you have yMin and yMax for your Y-axis
// For example, if you iterate through your function once to find these:
// var yMinActual = Double.POSITIVE_INFINITY
// var yMaxActual = Double.NEGATIVE_INFINITY
// for (xValue in xMin..xMax step someStep) {
//    val yValue = calculateY(xValue) // Your function f(x)
//    yMinActual = minOf(yMinActual, yValue)
//    yMaxActual = maxOf(yMaxActual, yValue)
// }
// Or set fixed yMin, yMax if you know the typical output range

// Scale factors: pixels per logical unit
                xScale = if (xRange != 0.0) imageWidth / xRange.toFloat() else 1f
                yScale = if (yRange != 0.0) imageHeight / yRange.toFloat() else 1f
                for (x0 in 0 until bitmap.width) {
                    var x =
                        xMin + x0.toFloat() /bitmap.width * (xMax - xMin)
                    val pc = (x-xMin) / (xMax - xMin)
                    fX.setParameter("x", x)
                    x = fX.eval().getElem()
                    fXY.setParameter("x", x)
                    val y = fXY.eval().getElem()
                    //x1paint = pc.toFloat()
                    //y1paint =(imageViewGraph.height / 2 - y).toFloat()
                    x1paint = logicalToScreenX(x)
                    y1paint = logicalToScreenY(y)
                    if(x0>0) {
                        canvas.drawLine(
                            x0paint,
                            y0paint,
                            x1paint,
                            y1paint,
                            paint.apply { color = android.graphics.Color.BLACK }) // Axe X
                    } else {

                        canvas.drawPoint(x1paint, y1paint, paint.apply { color = android.graphics.Color.BLACK }) // Axe X

                    }
                    x0paint = x1paint
                    y0paint = y1paint

                    println("logical (x,y) ($x,$y) screen (x,y) ($x1paint,$y1paint)")
                }


                val axisPaint = android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    strokeWidth = 4f
                }

                // Draw X-Axis
                val xAxisScreenY = logicalToScreenY(0.0).coerceIn(0f, this.imageHeight)
                canvas.drawLine(0f, xAxisScreenY, this.imageWidth, xAxisScreenY, axisPaint)

                // Draw Y-Axis
                val yAxisScreenX = logicalToScreenX(0.0).coerceIn(0f, this.imageWidth)
                canvas.drawLine(yAxisScreenX, 0f, yAxisScreenX, this.imageHeight, axisPaint)

            } catch (e: AlgebraicFormulaSyntaxException) {
                e.printStackTrace()
                // Show a Snackbar with the exception message
                val rootView = findViewById<View>(android.R.id.content) // Get the root view
                Snackbar.make(rootView, "Syntax Error: ${e.localizedMessage ?: "Invalid formula"}", Snackbar.LENGTH_LONG)
                    .setAction("Details") {
                        // Optionally, you can add an action, e.g., to show more details
                        // or guide the user.
                        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                    }
                    .show()
            } catch (e1: RuntimeException) {
                e1.printStackTrace()
                // Show a Snackbar for other runtime exceptions
                val rootView = findViewById<View>(android.R.id.content)
                Snackbar.make(
                    rootView,
                    "Error: ${e1.localizedMessage ?: "An unexpected error occurred"}",
                    Snackbar.LENGTH_LONG
                )
            }

            canvas.drawText(
                "Graph ($formulaFx) from $xMin to $xMax",
                50f,
                50f,
                paint.apply { textSize = 20f; color = android.graphics.Color.BLACK })


            imageViewGraph.setImageBitmap(bitmap)
            buttonDownloadImage.visibility = View.VISIBLE
        } catch (e: Exception) {
            Toast.makeText(this, "Error generating graph: ${e.message}", Toast.LENGTH_SHORT).show()
            imageViewGraph.setImageBitmap(null)
            buttonDownloadImage.visibility = View.GONE
        }
    }

    private fun downloadImage() {
        // ... (votre code existant pour downloadImage) ...
        // Assurez-vous que le code de downloadImage est présent et fonctionnel
        imageViewGraph.isDrawingCacheEnabled = true
        val bitmap = imageViewGraph.drawingCache
        if (bitmap != null) {
            val dir = File(applicationContext.externalMediaDirs[0].absolutePath+File.separator+ this.packageName)
            if(!dir.exists()) {
                dir.mkdir()
            }

            val i = dir.listFiles().size
            Image(bitmap).saveFile(File(dir.absolutePath+File.separator+ "graph"+(i+1)+".png"))
            Toast.makeText(this, "Download image logic to be implemented.", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(this, "No image to download.", Toast.LENGTH_SHORT).show()
        }
        imageViewGraph.isDrawingCacheEnabled = false
    }

    private inner class GenericTextWatcher(
        private val fieldId: Int,
        private val editText: TextInputEditText
    ) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            validate(fieldId, s.toString())
        }
    }
}