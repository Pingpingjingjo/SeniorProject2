package com.example.seniorproject

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.result_list_view.*

class ResultAdapter(private val context: Context,
    private val dataSource: List<ResultData>
) : BaseAdapter(){

    var isStarted = false
    var progressStatus = 0
    var handler: Handler? = null

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val rowView = convertView ?: inflater.inflate(R.layout.result_list_view, parent, false)

        val textView = rowView.findViewById<TextView>(R.id.result_text)

        val textView2 = rowView.findViewById<TextView>(R.id.result_percent)

        val progressBarHorizontal = rowView.findViewById<ProgressBar>(R.id.progressBarHorizontal)
        val textViewHorizontalProgress = rowView.findViewById<TextView>(R.id.textViewHorizontalProgress)

        textView2.text = dataSource[position].percent.toString()
        textView.text = dataSource[position].alias + "  : " + dataSource[position].valueAlias
        textView.setTextColor(Color.BLACK);
        //
        val percent = dataSource[position].percenDouble
        Log.d("fuck",percent.toString())
        if (percent>=0 && percent <20){
            textView2.setBackgroundColor(Color.rgb (0,204,0))
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

        handler = Handler(Handler.Callback {
            if (isStarted) {
                progressStatus++
            }
            progressBarHorizontal.progress = progressStatus
//            textViewHorizontalProgress.text = "${progressStatus}/${progressBarHorizontal.max}"
            handler?.sendEmptyMessageDelayed(0, 100)

            true
        })
//        progressBarHorizontal.setOnClickListener(horizontalDeterminate(view = View))

        handler?.sendEmptyMessage(0)

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
    fun horizontalDeterminate(view: View) {
        isStarted = !isStarted
    }



}