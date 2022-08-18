package one.empty3.apps.simplecalculator

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import one.empty3.apps.tree.altree.functions.ListMathDoubleFunction
import android.widget.ArrayAdapter as ArrayAdapter

class ChooseFunctionDialogFragment : DialogFragment() {
    var function : String = ""
    var selectedItem = -1
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val mathList : Array<String> = ListMathDoubleFunction.getList()

            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.fragment_item_list, null))
                // Add action buttons
                .setPositiveButton(R.string.fragment_function_ok,
                    DialogInterface.OnClickListener { dialog, id ->
                    })
                .setNegativeButton(R.string.fragment_function_cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                    })

                builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
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

