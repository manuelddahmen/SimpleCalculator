package one.empty3.apps.simplecalculator

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import one.empty3.apps.tree.altree.functions.ListMathDoubleFunction

class ChooseFunctionDialogFragment : DialogFragment() {
    var function : String = ""
    var selectedItem = -1
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        val mathList : Array<String> = ListMathDoubleFunction.getList()
        //mathList.forEach { println(it) }
        return builder
            .setMessage(R.string.dialog_message).setTitle(R.string.dialog_title)
            .setSingleChoiceItems(mathList, selectedItem) { _, which ->
                this.function = mathList[which].toString()

            }
            .setPositiveButton(R.string.ok_button) { _, _ ->

            }
            .setNegativeButton(R.string.cancel_button) { _, _ ->
                this.function = ""
            }.create()
    }

    companion object {
        const val TAG = "one.empty3.apps.simplecalculator.ChooseFunctionDialogFragment"
    }
}

