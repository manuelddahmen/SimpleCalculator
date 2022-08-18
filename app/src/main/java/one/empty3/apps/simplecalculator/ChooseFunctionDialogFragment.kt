package one.empty3.apps.simplecalculator

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.fragment.app.DialogFragment
import one.empty3.apps.tree.altree.functions.ListMathDoubleFunction
import android.widget.ArrayAdapter as ArrayAdapter

class ChooseFunctionDialogFragment : DialogFragment() {
    var function : String = ""
    var selectedItem = -1
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        val mathList : Array<String> = ListMathDoubleFunction.getList()

        //val adapter : MyStringRecyclerViewAdapter = MyStringRecyclerViewAdapter(mathList)
        //mathList.forEach { println(it) }
        return builder
            .setMessage(R.string.dialog_message).setTitle(R.string.dialog_title)
            .setSingleChoiceItems(mathList, selectedItem) { _, which ->
                this.function = mathList[which]
            }
            .setPositiveButton(R.string.ok_button) { _, _ ->
            }
            .setNegativeButton(R.string.cancel_button) { _, _ ->
                this.function = ""
            }.create()
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_item_list, container, false)
        return inflate
    }
    companion object {
        const val TAG = "one.empty3.apps.simplecalculator.ChooseFunctionDialogFragment"
    }
}

