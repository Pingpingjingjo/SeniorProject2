package com.example.seniorproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_info.*

class Info : AppCompatActivity() {


    lateinit var dataReference: DatabaseReference
    var disorderNameList: MutableList<String> = mutableListOf()
    var scores: MutableMap<String, Int> = mutableMapOf()
    var rangeLists: MutableMap<String, MutableList<Range>> = mutableMapOf()
    var resultLists: MutableMap<String, MutableList<String>> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)


        dataReference = FirebaseDatabase.getInstance().getReference("Data4")

        dataReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (item in p0.children) {
                    val c = item.getKey()
                    disorderNameList.add(c!!)

                    var tempRangList: MutableList<Range> = mutableListOf()
                    for ( score in item.child("Score").children){
                        var c = score.getValue(Range::class.java)
                        tempRangList.add(c!!)
                    }
                    rangeLists[c] = tempRangList


                    var tempResultList: MutableList<String> = mutableListOf()
                    for ( score in item.child("Result").children){
                        var c = score.getValue(String::class.java)
                        tempResultList.add(c!!)
                    }
                    resultLists[c] = tempResultList
                }


                var dataShow = ArrayList<String> ()
                for (disorder in disorderNameList){
                    dataShow.add(disorder)
                    dataShow.add("Total Score \t Result")
                    dataShow.add( "0-" + (rangeLists[disorder]!![0].min-1).toString()+"\t" +resultLists[disorder]!![0])
                    for (i in 1 until resultLists[disorder]!!.size){
                        dataShow.add(rangeLists[disorder]!![i-1].min.toString() + "-" + rangeLists[disorder]!![i-1].max.toString()+"\t" +resultLists[disorder]!![i])
                    }
                }

                val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1,dataShow)
                infoListView.adapter = adapter;
            }
        })

    }



}
