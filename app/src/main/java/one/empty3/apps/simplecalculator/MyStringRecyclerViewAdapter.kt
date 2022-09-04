package one.empty3.apps.simplecalculator

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

import one.empty3.apps.simplecalculator.placeholder.PlaceholderContent.PlaceholderItem
import one.empty3.apps.simplecalculator.databinding.FragmentItemBinding
import one.empty3.apps.tree.altree.functions.ListMathDoubleFunction


class MyStringRecyclerViewAdapter(private var values: Array<String> = ListMathDoubleFunction.getList()) :
    RecyclerView.Adapter<MyStringRecyclerViewAdapter.ViewHolder>(),
    Parcelable {

    private var items: ArrayList<String> = ArrayList<String>()
    private var item: String = ""

    private lateinit var main2022: Array<String>
    private lateinit var rv: RecyclerView

    constructor(parcel: Parcel) : this() {
        item = parcel.readString().toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(

            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }


    public fun setMainAnd(main2022: Array<String>, rv: RecyclerView) {
        this.main2022 = main2022
        this.rv = rv
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemId: Int = getItemId(position).toInt()
        item = values[position]
        val tv1: TextView = (holder.itemView.findViewById(R.id.item_number) as TextView)
        val tv2: TextView = (holder.itemView.findViewById(R.id.content) as TextView)
        (tv1).text = item
        (tv2).text = item
        holder.text = item
        holder.itemView.setOnClickListener {
            ListMathDoubleFunction.functionName = tv2.text as String
            holder.itemView.setBackgroundColor(Color.Blue.toArgb())
            Toast.makeText(rv.context, item, Toast.LENGTH_LONG)
        }
        tv1.setOnClickListener {
            ListMathDoubleFunction.functionName = tv2.text as String
            tv1.setBackgroundColor(Color.Blue.toArgb())
            Toast.makeText(rv.context, item, Toast.LENGTH_LONG)
        }
        tv2.setOnClickListener {
            ListMathDoubleFunction.functionName = tv2.text as String
            tv2.setBackgroundColor(Color.Blue.toArgb())
            Toast.makeText(rv.context, item, Toast.LENGTH_LONG)
        }
    }


    override fun getItemCount(): Int = values.size

    class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var text: String
        val idView: TextView = binding.itemNumber
        private val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    override fun describeContents(): Int {
        return Parcelable.CONTENTS_FILE_DESCRIPTOR
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(item)
    }

    companion object CREATOR : Parcelable.Creator<MyStringRecyclerViewAdapter> {
        override fun createFromParcel(parcel: Parcel): MyStringRecyclerViewAdapter {
            return MyStringRecyclerViewAdapter(parcel)
        }

        override fun newArray(size: Int): Array<MyStringRecyclerViewAdapter?> {
            return arrayOfNulls(size)
        }
    }

}