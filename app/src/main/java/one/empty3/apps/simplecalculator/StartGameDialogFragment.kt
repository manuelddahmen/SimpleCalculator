package one.empty3.apps.simplecalculator

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.textclassifier.TextClassificationSessionId
import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.NonCancellable.start
import one.empty3.apps.tree.altree.functions.ListMathDoubleFunction

class StartGameDialogFragment : DialogFragment() {
    var function : String = ""
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        val builder: AlertDialog.Builder = activity.let {
            AlertDialog.Builder(it)
        }
        // 2. Chain together various setter methods to set the dialog characteristics
        val selectedItems = ArrayList<CharSequence>()
        val mathList : Array<out CharSequence> = ListMathDoubleFunction.getList()
        mathList.forEach { println(it) }
        return builder
            .setMessage(R.string.dialog_message).setTitle(R.string.dialog_title)
            .setItems(mathList) { dialog, which ->
                {
                    this.function = mathList[which].toString()
                }
            }
            .setPositiveButton(R.string.ok_button,
                DialogInterface.OnClickListener { dialog, id ->

                })
            .setNegativeButton(R.string.cancel_button,
            DialogInterface.OnClickListener { dialog, id ->
                this.function = ""
            }).create()
    }
}

