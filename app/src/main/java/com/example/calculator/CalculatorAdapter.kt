package com.example.calculator

import android.content.Context
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CalculatorAdapter(
    private var numAndSymList: ArrayList<NumberLabel>,
    private val context: Context,
    private var result: TextView
) : RecyclerView.Adapter<CalculatorAdapter.CalculatorViewHolder>() {

    var clickListener: ((NumberLabel) -> Unit)? = null
    private var temp : String = ""
    inner class CalculatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var numberTextView: TextView
        var numberLabel: TextView
        init {
            numberTextView = itemView.findViewById(R.id.tv_number_label) as TextView
            numberLabel = itemView.findViewById(R.id.tv_number_label) as TextView
        }
        fun bind() : TextView {
            return itemView.findViewById(R.id.tv_output) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorViewHolder {
        return CalculatorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_calculator_number, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CalculatorViewHolder, position: Int) {
        holder.numberTextView.text = numAndSymList[position].numAndSym
        holder.numberLabel.setOnClickListener{
//            if (temp!= null && position == ) {
//                temp = temp.dropLast(1)
//            } else {
//                temp += numAndSymList[position].numAndSym
//                result.text = temp
//            }
            clickListener?.invoke(numAndSymList[position])
        }
    }

    override fun getItemCount(): Int {
        return numAndSymList.size
    }
}