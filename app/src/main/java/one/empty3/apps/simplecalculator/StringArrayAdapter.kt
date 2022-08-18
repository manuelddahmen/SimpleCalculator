package one.empty3.apps.simplecalculator

import android.graphics.drawable.Icon
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import one.empty3.apps.tree.altree.functions.ListMathDoubleFunction

class StringArrayAdapter() : RecyclerView.Adapter<StringArrayAdapter.ViewHolder>(), Parcelable {
    private var mathList: Array<String> = ListMathDoubleFunction.getList()

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        //val item = itemView.findViewById(R.id.icon) as Icon
        val name = itemView.findViewById(R.id.text_view_recyclerview_function) as TextView
    }

    constructor(parcel: Parcel) : this()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val from = LayoutInflater.from(parent.context)
        val viewItem = from.inflate(R.layout.item_view, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val function = mathList[position]
        holder.name.text =  function
    }

    override fun getItemCount(): Int {
        return mathList.size
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<StringArrayAdapter> {
        override fun createFromParcel(parcel: Parcel): StringArrayAdapter {
            return StringArrayAdapter(parcel)
        }

        override fun newArray(size: Int): Array<StringArrayAdapter?> {
            return arrayOfNulls(size)
        }
    }


}