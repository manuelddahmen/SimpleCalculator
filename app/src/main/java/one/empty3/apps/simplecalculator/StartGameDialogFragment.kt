package one.empty3.apps.simplecalculator

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import one.empty3.apps.tree.altree.functions.ListMathDoubleFunction

class StartGameDialogFragment : DialogFragment() {
    var function : String = ""
    var selectedItem = -1
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = activity.let {
            AlertDialog.Builder(it)
        }
        val mathList : Array<out CharSequence> = ListMathDoubleFunction.getList()
        mathList.forEach { println(it) }
        return builder
            .setMessage(R.string.dialog_message).setTitle(R.string.dialog_title)
            .setSingleChoiceItems(mathList, selectedItem) { dialog, which ->
                this.function = mathList[which].toString()

            }
            .setPositiveButton(R.string.ok_button) { _, _ ->

            }
            .setNegativeButton(R.string.cancel_button) { _, _ ->
                this.function = ""
            }.create()
    }

}

