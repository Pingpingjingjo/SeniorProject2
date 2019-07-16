package com.example.seniorproject

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ResultAdapter(private val context: Context,
    private val dataSource: List<ResultData>
) : BaseAdapter(){
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val rowView = convertView ?: inflater.inflate(R.layout.result_list_view, parent, false)

        val value = (255*dataSource[position].value)

        rowView.setBackgroundColor(Color.rgb(value,255-value,0))

        val textView = rowView.findViewById<TextView>(R.id.result_text)

        val textView2 = rowView.findViewById<TextView>(R.id.result_percent)

        textView2.text = dataSource[position].percent.toString()

        textView.text = dataSource[position].alias + " : " + dataSource[position].valueAlias

        return rowView
    }

    override fun getItem(position: Int): Any {
       return dataSource.get((position))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
       return dataSource.size
    }

}