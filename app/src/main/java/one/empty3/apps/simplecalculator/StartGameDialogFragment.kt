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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        val builder: AlertDialog.Builder = activity.let {
            AlertDialog.Builder(it)
        }
        // 2. Chain together various setter methods to set the dialog characteristics
        val selectedItems = ArrayList<CharSequence>()
        val mathList : Array<out CharSequence> = ListMathDoubleFunction.getList()
        return builder
            .setMessage(R.string.dialog_message).setTitle(R.string.dialog_title)
            .setMultiChoiceItems(mathList, null,
                DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                    if (isChecked) {
                        // If the user checked the item, add it to the selected items
                        selectedItems.add(which.toString())
                    } else if (selectedItems.contains(which.toString())) {
                        // Else, if the item is already in the array, remove it
                        selectedItems.remove(which.toString())
                    }
                })
            .setPositiveButton(R.string.ok_button,
                DialogInterface.OnClickListener { dialog, id ->
                    // START THE GAME!
                })
            .setNegativeButton(R.string.cancel_button,
            DialogInterface.OnClickListener { dialog, id ->
                // START THE GAME!
            }).create()
    }
}

