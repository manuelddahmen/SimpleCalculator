package one.empty3.apps.simplecalculator

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout // Ajouté
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider // Ajouté
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.android.material.button.MaterialButton // Ajouté
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import one.empty3.library1.tree.AlgebraicFormulaSyntaxException
import one.empty3.library1.tree.AlgebraicTree
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GraphActivity : AppCompatActivity() {

    private lateinit var strings: List<String>
    private lateinit var prefs: SharedPreferences
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
    private lateinit var frameLayoutImageView: FrameLayout // Gardez cela si vous en avez besoin pour d'autres raisons

    // Nouveaux boutons et leur layout
    private lateinit var layoutImageActions: LinearLayout
    private lateinit var buttonSaveImage: MaterialButton
    private lateinit var buttonShareImage: MaterialButton
    private lateinit var buttonDownloadImage: MaterialButton

    private var currentImageFile: File? = null // Pour stocker le fichier image actuel

    private val validationStates = mutableMapOf(
        FIELD_FORMULA_X to false,
        FIELD_FORMULA_FX to false,
        FIELD_X_MIN to true,
        FIELD_X_MAX to true,
        FIELD_Y_MIN to true,
        FIELD_Y_MAX to true
    )

    companion object {
        private const val FIELD_FORMULA_X = 1
        private const val FIELD_FORMULA_FX = 3
        private const val FIELD_X_MIN = 4
        private const val FIELD_X_MAX = 5
        private const val FIELD_Y_MIN = 6
        private const val FIELD_Y_MAX = 7

        private const val DEFAULT_X_MIN = -10.0
        private const val DEFAULT_X_MAX = 10.0
        private const val DEFAULT_Y_MIN = -10.0
        private const val DEFAULT_Y_MAX = 10.0

        private const val AUTHORITY = "one.empty3.apps.simplecalculator.fileprovider" // Correspond à celui du Manifest
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false) // Essentiel pour le bord à bord




        setContentView(R.layout.activity_graph) // Assurez-vous que c'est le bon layout principal
        val yourRootView = findViewById<View>(R.id.root_activity_graph) // Ou la vue qui a besoin de padding

        ViewCompat.setOnApplyWindowInsetsListener(yourRootView) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Appliquer le padding pour éviter le chevauchement avec les barres système
            view.updatePadding(
                left = insets.left,
                top = insets.top,
                right = insets.right,
                bottom = insets.bottom
            )

            // Indiquer que les insets ont été consommés
            WindowInsetsCompat.CONSUMED
        }

        title = getString(R.string.title_activity_graph)

        editTextFormulaX = findViewById(R.id.editTextFormulaX)
        editTextFormulaFx = findViewById(R.id.editTextFormulaFx)
        editTextXMin = findViewById(R.id.editTextXMin)
        editTextXMax = findViewById(R.id.editTextXMax)
        editTextYMin = findViewById(R.id.editTextYMin)
        editTextYMax = findViewById(R.id.editTextYMax)

        buttonPlot = findViewById(R.id.buttonPlot)
        imageViewGraph = findViewById(R.id.imageViewGraph)
        frameLayoutImageView = findViewById(R.id.frameLayoutImageView)

        // Initialisation des nouveaux boutons et du layout
        layoutImageActions = findViewById(R.id.layoutImageActions)
        buttonSaveImage = findViewById(R.id.buttonSaveImage)
        buttonShareImage = findViewById(R.id.buttonShareImage)


        validateInitialFields()
        setupTextWatchers()


        buttonPlot.setOnClickListener {
            plotGraph()
        }

        buttonSaveImage.setOnClickListener {
            saveImageToStorage()
        }

        buttonShareImage.setOnClickListener {
            shareImage()
        }

        checkAllFieldsValidated()
    }

    private fun validateInitialFields() {
        validate(FIELD_FORMULA_X, editTextFormulaX.text.toString())
        validate(FIELD_FORMULA_FX, editTextFormulaFx.text.toString())
        validate(FIELD_X_MIN, editTextXMin.text.toString())
        validate(FIELD_X_MAX, editTextXMax.text.toString())
        validate(FIELD_Y_MIN, editTextYMin.text.toString())
        validate(FIELD_Y_MAX, editTextYMax.text.toString())
    }
    private fun setupTextWatchers() {
        editTextFormulaX.addTextChangedListener(GenericTextWatcher(FIELD_FORMULA_X, editTextFormulaX))
        editTextFormulaFx.addTextChangedListener(GenericTextWatcher(FIELD_FORMULA_FX, editTextFormulaFx))
        editTextXMin.addTextChangedListener(GenericTextWatcher(FIELD_X_MIN, editTextXMin))
        editTextXMax.addTextChangedListener(GenericTextWatcher(FIELD_X_MAX, editTextXMax))
        editTextYMin.addTextChangedListener(GenericTextWatcher(FIELD_Y_MIN, editTextYMin))
        editTextYMax.addTextChangedListener(GenericTextWatcher(FIELD_Y_MAX, editTextYMax))
    }

    private fun validate(fieldId: Int, content: String): Boolean {
        var isValid = content.isNotBlank()
        var specificError: String? = null

        when (fieldId) {
            FIELD_FORMULA_X, FIELD_FORMULA_FX -> {
                if (isValid) {
                    try {
                        AlgebraicTree(content).construct()
                    } catch (e: RuntimeException) {
                        isValid = false
                        specificError = e.localizedMessage ?: "Syntax error"
                    } catch (e: AlgebraicFormulaSyntaxException) {
                        isValid = false
                    }
                } else { isValid = false }
            }
            FIELD_X_MIN, FIELD_X_MAX, FIELD_Y_MIN, FIELD_Y_MAX -> {
                if (isValid) {
                    isValid = content.toDoubleOrNull() != null
                    if (!isValid) specificError = "Not a number"
                } else { isValid = false }

                if (isValid && (fieldId == FIELD_X_MAX || fieldId == FIELD_X_MIN)) {
                    val xMinVal = editTextXMin.text.toString().toDoubleOrNull()
                    val xMaxVal = editTextXMax.text.toString().toDoubleOrNull()
                    if (xMinVal != null && xMaxVal != null && xMaxVal <= xMinVal) {
                        isValid = false
                        if (fieldId == FIELD_X_MAX) specificError = "X Max must be > X Min"
                        else editTextXMax.error = "X Max must be > X Min"
                    } else {
                        if(fieldId == FIELD_X_MAX) editTextXMax.error = null else editTextXMin.error = null
                    }
                }
                if (isValid && (fieldId == FIELD_Y_MAX || fieldId == FIELD_Y_MIN)) {
                    val yMinVal = editTextYMin.text.toString().toDoubleOrNull()
                    val yMaxVal = editTextYMax.text.toString().toDoubleOrNull()
                    if (yMinVal != null && yMaxVal != null && yMaxVal <= yMinVal) {
                        isValid = false
                        if (fieldId == FIELD_Y_MAX) specificError = "Y Max must be > Y Min"
                        else editTextYMax.error = "Y Max must be > Y Min"
                    } else {
                        if(fieldId == FIELD_Y_MAX) editTextYMax.error = null else editTextYMin.error = null
                    }
                }
            }
        }
        validationStates[fieldId] = isValid
        val editText = when (fieldId) {
            FIELD_FORMULA_X -> editTextFormulaX; FIELD_FORMULA_FX -> editTextFormulaFx
            FIELD_X_MIN -> editTextXMin; FIELD_X_MAX -> editTextXMax
            FIELD_Y_MIN -> editTextYMin; FIELD_Y_MAX -> editTextYMax
            else -> null
        }
        if (!isValid && content.isNotEmpty()) {
            editText?.error = specificError ?: "Invalid input"
        } else if (isValid || content.isEmpty()) {
            if(specificError == null) editText?.error = null
        }
        checkAllFieldsValidated()
        return isValid
    }

    private fun checkAllFieldsValidated() {
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

                    //println("logical (x,y) ($x,$y) screen (x,y) ($x1paint,$y1paint)")
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
            buttonSaveImage.visibility = View.VISIBLE
            buttonShareImage.visibility = View.VISIBLE
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
    }


    private fun saveBitmapToFile(bitmapToSave: Bitmap): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "GRAPH_${timeStamp}_"
        // Utilise le répertoire de cache externe pour que FileProvider puisse y accéder facilement
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+"simplecalculator") // Ou getExternalCacheDir()

        return try {
            if(storageDir!=null &&!storageDir.exists())
                storageDir.mkdirs()

            val imageFile = File.createTempFile(imageFileName, ".png", storageDir)
            val fos = FileOutputStream(imageFile)
            bitmapToSave.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
            // Ajoute l'image à la galerie du téléphone pour qu'elle soit visible
            @Suppress("DEPRECATION")
            Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
                mediaScanIntent.data = Uri.fromFile(imageFile)
                sendBroadcast(mediaScanIntent)
            }
            imageFile
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }


    private fun saveImageToStorage() {
        if (!::bitmap.isInitialized) {
            Toast.makeText(this, R.string.error_no_image_to_share, Toast.LENGTH_SHORT).show()
            return
        }
        currentImageFile = saveBitmapToFile(bitmap)
        if (currentImageFile != null) {
            Toast.makeText(this, getString(R.string.image_saved_to, currentImageFile!!.absolutePath), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, getString(R.string.failed_to_save_image, "Could not create file"), Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareImage() {
        if (!::bitmap.isInitialized) {
            Toast.makeText(this, R.string.error_no_image_to_share, Toast.LENGTH_SHORT).show()
            return
        }

        // Si l'image n'a pas encore été sauvegardée pour le partage, la sauvegarder maintenant
        if (currentImageFile == null || !currentImageFile!!.exists()) {
            currentImageFile = saveBitmapToFile(bitmap)
        }

        if (currentImageFile != null && currentImageFile!!.exists()) {
            val imageUri: Uri = FileProvider.getUriForFile(
                this,
                AUTHORITY, // Doit correspondre à celui dans le Manifest et res/xml/file_paths.xml
                currentImageFile!!
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, imageUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Important pour les URI de FileProvider
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.title_activity_graph)) // Titre optionnel pour email/etc.
            }
            try {
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_image_title)))
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, R.string.error_failed_to_share_image, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, R.string.error_no_image_to_share, Toast.LENGTH_SHORT).show()
        }
    }


    private inner class GenericTextWatcher(
        private val fieldId: Int,
        private val editText: TextInputEditText
    ) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) { validate(fieldId, s.toString()) }
    }
}