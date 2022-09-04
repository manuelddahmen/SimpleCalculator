package one.empty3.apps.simplecalculator
/*
package one.empty3.apps.simplecalculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import one.empty3.apps.simplecalculator.placeholder.PlaceholderContent
import one.empty3.apps.tree.altree.functions.ListMathDoubleFunction

*/
/**
 * A fragment representing a list of Items.
 *//*

class StringFragment() : Fragment() {
    private lateinit var list : ArrayList<PlaceholderContent.PlaceholderItem>
    private var columnCount = 1

    init {
        val mathList : Array<String> = ListMathDoubleFunction.getList()
        list = ArrayList()
        val i:Int
        for (i in mathList.indices) {
            list.add(PlaceholderContent.PlaceholderItem(""+mathList[i].hashCode(),
                mathList[i], mathList[i]))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewFragment = inflater.inflate(R.layout.fragment_item_list, container, false)

        val view : RecyclerView = viewFragment.findViewById(R.id.list)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyStringRecyclerViewAdapter(myListPlaceHolder())
            }
        }
        return viewFragment
    }

    private fun myListPlaceHolder(): List<PlaceholderContent.PlaceholderItem> {
        return list

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            StringFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}

*/
