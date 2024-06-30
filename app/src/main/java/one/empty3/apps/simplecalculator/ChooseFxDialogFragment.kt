/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
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
 *
 *
 */

package one.empty3.apps.simplecalculator


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import one.empty3.library1.tree.functions.ListVecDoublesFunction

class ChooseFxDialogFragment : DialogFragment() {
    public var isExited: Boolean = false
    var functionName = ""
    private lateinit var rv: RecyclerView
    private lateinit var main2022: ListVecDoublesFunction
    var function : String = ""
    var selectedItem = -1


    public fun setMainAnd(main2022 : ListVecDoublesFunction, rv: RecyclerView) {
        this.main2022 = main2022
        ListVecDoublesFunction.functionName = ""
        this.rv = rv
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val ret =  activity?.let {
            val mathList : Array<String> = ListVecDoublesFunction.getList()

            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            val inflate = inflater.inflate(R.layout.fragment_item_list, null)
            builder.setView(inflate)
                // Add action buttons
                .setPositiveButton(R.string.fragment_function_ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        this.functionName =  ListVecDoublesFunction.functionName
                        isExited = true;

                    })
                .setNegativeButton(R.string.fragment_function_cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        this.functionName = ""
                        isExited = true;
                    })




            val  rv: RecyclerView =  inflate.findViewById(R.id.list)

            val myStringRecyclerViewAdapterFunctionList =
                MyStringRecyclerViewAdapterFunctionList(mathList)
            rv.adapter = myStringRecyclerViewAdapterFunctionList
            myStringRecyclerViewAdapterFunctionList.setMainAnd(mathList, rv)

            return builder.create()

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
        const val TAG = "one.empty3.apps.simplecalculator.ChooseFxDialogFragment"
    }
}
