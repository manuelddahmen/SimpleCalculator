package one.empty3.apps.simplecalculator

import android.content.Intent
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
import com.google.android.material.button.MaterialButton // Ajouté
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import one.empty3.library1.tree.AlgebraicFormulaSyntaxException
import one.empty3.library1.tree.AlgebraicTree
// Assurez-vous que cette classe existe et gère la sauvegarde
// import one.empty3.libs.Image
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GraphActivity : AppCompatActivity() {
    // ... (autres propriétés comme imageWidth, imageHeight etc.)
    private var imageWidth: Float = 0.0f
    private var xScale: Float = 0.0f
    private var yScale: Float = 0.0f
    private var yMinLogicalUi: Float = 0.0f
    private var imageHeight: Float = 0.0f
    private var xMinUi: Double = -10.0
    private var xMaxUi: Double = 10.0
    private var yMaxLogicalUi: Float = 0.0f

    private lateinit var bitmap: Bitmap
    private lateinit var editTextFormulaX: TextInputEditText
    private lateinit var editTextFormulaFx: TextInputEditText
    private lateinit var editTextXMin: TextInputEditText
    private lateinit var editTextXMax: TextInputEditText
    private lateinit var editTextYMin: TextInputEditText
    private lateinit var editTextYMax: TextInputEditText

    private lateinit var buttonPlot: Button
    private lateinit var imageViewGraph: ImageView
    private lateinit var frameLayoutImageView: FrameLayout // Gardez cela si vous en avez besoin pour d'autres raisons

    // Nouveaux boutons et leur layout
    private lateinit var layoutImageActions: LinearLayout
    private lateinit var buttonSaveImage: MaterialButton
    private lateinit var buttonShareImage: MaterialButton

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
        setContentView(R.layout.content_graph) // Assurez-vous que c'est le bon layout principal

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

    private fun plotGraph() {
        val formulaXStr = editTextFormulaX.text.toString()
        val formulaFxStr = editTextFormulaFx.text.toString()
        val xMin = editTextXMin.text.toString().toDoubleOrNull() ?: DEFAULT_X_MIN
        val xMax = editTextXMax.text.toString().toDoubleOrNull() ?: DEFAULT_X_MAX
        val yMinUser = editTextYMin.text.toString().toDoubleOrNull() ?: DEFAULT_Y_MIN
        val yMaxUser = editTextYMax.text.toString().toDoubleOrNull() ?: DEFAULT_Y_MAX

        if (xMin >= xMax) { /* ... gestion d'erreur ... */ return }
        if (yMinUser >= yMaxUser) { /* ... gestion d'erreur ... */ return }

        this.xMinUi = xMin; this.xMaxUi = xMax
        this.yMinLogicalUi = yMinUser.toFloat(); this.yMaxLogicalUi = yMaxUser.toFloat()

        try {
            bitmap = Bitmap.createBitmap(
                imageViewGraph.width.coerceAtLeast(100),
                imageViewGraph.height.coerceAtLeast(100),
                Bitmap.Config.ARGB_8888
            )
            val canvas = android.graphics.Canvas(bitmap)
            canvas.drawColor(android.graphics.Color.LTGRAY)
            this.imageWidth = bitmap.width.toFloat(); this.imageHeight = bitmap.height.toFloat()
            val xRangeLogical = xMax - xMin; val yRangeLogical = yMaxUser - yMinUser
            this.xScale = if (xRangeLogical != 0.0) this.imageWidth / xRangeLogical.toFloat() else 1f
            this.yScale = if (yRangeLogical != 0.0) this.imageHeight / yRangeLogical.toFloat() else 1f

            fun logicalToScreenX(logicalX: Double): Float { return ((logicalX - this.xMinUi) * this.xScale).toFloat() }
            fun logicalToScreenY(logicalY: Double): Float { return this.imageHeight - ((logicalY - this.yMinLogicalUi) * this.yScale).toFloat() }

            val axisPaint = android.graphics.Paint().apply { color = android.graphics.Color.BLACK; strokeWidth = 2f }
            val dataPaint = android.graphics.Paint().apply { color = android.graphics.Color.BLUE; strokeWidth = 3f }

            val xAxisScreenY = logicalToScreenY(0.0).coerceIn(0f, this.imageHeight)
            canvas.drawLine(0f, xAxisScreenY, this.imageWidth, xAxisScreenY, axisPaint)
            val yAxisScreenX = logicalToScreenX(0.0).coerceIn(0f, this.imageWidth)
            canvas.drawLine(yAxisScreenX, 0f, yAxisScreenX, this.imageHeight, axisPaint)

            val treeX = AlgebraicTree(formulaXStr); treeX.construct()
            val treeFx = AlgebraicTree(formulaFxStr); treeFx.construct()
            var previousScreenX = -1f; var previousScreenY = -1f
            val numberOfPoints = this.imageWidth.toInt().coerceAtLeast(200)

            for (i in 0 until numberOfPoints) {
                val currentParamForX = xMin + (xRangeLogical * i / (numberOfPoints - 1).toDouble())
                treeX.setParameter("t", currentParamForX)
                val logicalX = treeX.eval().getElem()
                treeFx.setParameter("x", logicalX)
                val logicalY = treeFx.eval().getElem()

                if (logicalX!=null && logicalY!=null &&logicalX.isFinite() && logicalY.isFinite()) {
                    val screenX = logicalToScreenX(logicalX); val screenY = logicalToScreenY(logicalY)
                    if (previousScreenX != -1f) {
                        if (!((previousScreenY < 0 && screenY < 0) || (previousScreenY > this.imageHeight && screenY > this.imageHeight) ||
                                    (previousScreenX < 0 && screenX < 0) || (previousScreenX > this.imageWidth && screenX > this.imageWidth))) {
                            canvas.drawLine(previousScreenX, previousScreenY, screenX, screenY, dataPaint)
                        }
                    }
                    previousScreenX = screenX; previousScreenY = screenY
                } else { previousScreenX = -1f; previousScreenY = -1f }
            }
            imageViewGraph.setImageBitmap(bitmap)
            currentImageFile = null // Réinitialiser le fichier sauvegardé car le bitmap a changé
            layoutImageActions.visibility = View.VISIBLE // Afficher les boutons Save/Share

        } catch (e: AlgebraicFormulaSyntaxException) {
            handlePlottingError("Syntax Error: Algebraic formula syntax error", null)
        } catch (e: RuntimeException) {
            handlePlottingError("Plotting Error: ${e.localizedMessage ?: "An unexpected error occurred"}", e)
        }
    }

    private fun handlePlottingError(message: String, exception: Exception?) {
        if(exception!=null) {
            exception.printStackTrace()
        }
        val rootView = findViewById<View>(android.R.id.content)
        val exceptionString = if(exception!=null) exception.toString() else ""
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)

            .setAction("Details") { Toast.makeText(this, exceptionString, Toast.LENGTH_LONG).show() }
            .show()
        imageViewGraph.setImageBitmap(null)
        layoutImageActions.visibility = View.GONE // Cacher les boutons si erreur
        currentImageFile = null
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