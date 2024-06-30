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

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import javaAnd.awt.Color

import one.empty3.apps.simplecalculator.placeholder.PlaceholderContent.PlaceholderItem
import one.empty3.apps.simplecalculator.databinding.FragmentItemBinding
import one.empty3.library1.tree.functions.ListVecDoublesFunction

class MyStringRecyclerViewAdapterFunctionList(private var values: Array<String> = ListVecDoublesFunction.getList()) :
            RecyclerView.Adapter<MyStringRecyclerViewAdapterFunctionList.ViewHolder>(),
    Parcelable {

        private var items: ArrayList<String> = ArrayList<String>()
        private var item: String = ""

        private lateinit var main2022: Array<String>
        private lateinit var rvVec: RecyclerView

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


        public fun setMainAnd(main2022: Array<String>, rvVec: RecyclerView) {
            this.main2022 = main2022
            this.rvVec = rvVec
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
                ListVecDoublesFunction.functionName = tv2.text as String
                holder.itemView.setBackgroundColor(android.graphics.Color.BLUE)
                Toast.makeText(rvVec.context, item, Toast.LENGTH_LONG).show()
            }
            tv1.setOnClickListener {
                ListVecDoublesFunction.functionName = tv2.text as String
                tv1.setBackgroundColor(android.graphics.Color.BLUE)
                Toast.makeText(rvVec.context, item, Toast.LENGTH_LONG).show()
            }
            tv2.setOnClickListener {
                ListVecDoublesFunction.functionName = tv2.text as String
                tv2.setBackgroundColor(android.graphics.Color.BLUE)
                Toast.makeText(rvVec.context, item, Toast.LENGTH_LONG).show()
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

        companion object CREATOR : Parcelable.Creator<MyStringRecyclerViewAdapterFunctionList> {
            override fun createFromParcel(parcel: Parcel): MyStringRecyclerViewAdapterFunctionList {
                return MyStringRecyclerViewAdapterFunctionList(parcel)
            }

            override fun newArray(size: Int): Array<MyStringRecyclerViewAdapterFunctionList?> {
                return arrayOfNulls(size)
            }
        }

    }

