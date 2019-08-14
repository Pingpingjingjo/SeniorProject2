package com.example.seniorproject

import android.content.Context
import android.graphics.Color
import android.util.Log
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

        val textView = rowView.findViewById<TextView>(R.id.result_text)

        val textView2 = rowView.findViewById<TextView>(R.id.result_percent)

        textView2.text = dataSource[position].percent.toString()
        textView.text = dataSource[position].alias + "  : " + dataSource[position].valueAlias
        textView.setTextColor(Color.BLACK);
        //
        val percent = dataSource[position].percenDouble
        Log.d("fuck",percent.toString())
        if (percent>=0 && percent <20){
            //textView2.setBackgroundColor(Color.rgb (0,204,0))
            textView2.setBackgroundColor(Color.parseColor("#2ca705"))
            textView2.setTextColor(Color.WHITE)
        }
        else if (percent>=20 && percent <40){
            textView2.setBackgroundColor(Color.rgb (204,204,0))
            textView2.setTextColor(Color.WHITE)

        }
        else if (percent>=40 && percent <60){
            textView2.setBackgroundColor(Color.rgb(204,102,0))
            textView2.setTextColor(Color.WHITE)

        }
        else if (percent >=60 && percent<80){
            textView2.setBackgroundColor(Color.rgb(204,0,102))
            textView2.setTextColor(Color.WHITE)
        }
        else {
            textView2.setBackgroundColor(Color.rgb(204,0,0))
            textView2.setTextColor(Color.WHITE)
        }

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