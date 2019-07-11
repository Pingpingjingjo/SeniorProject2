package com.example.seniorproject


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView



class MsgAdapter(val mContext: Context, val layoutResId:Int, val messageList:List<TestRecord>): ArrayAdapter<TestRecord>(mContext,layoutResId,messageList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
        val view:View = layoutInflater.inflate(layoutResId,null)
        val msg = messageList[position]
        val msgText = view.findViewById<TextView>(R.id.msgView1)

//        msgText.text = msg.one  + " "  + " " + msg.two + " " + msg.three

        return view
    }
}