package com.example.seniorproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class History : AppCompatActivity() {

    private lateinit var listView : ListView

    private var dataList  = HashMap<String, List< Pair<String, Pair<Int,Int> > >> ()

    private var dataShow = ArrayList<String> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        listView = findViewById(R.id.history)
        populateDataList { show() }

    }

    fun populateDataList(callback: ()->Unit){

        val dataReference = FirebaseDatabase.getInstance().getReference("Data4")

        dataReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {

                    val disorder = item.key.toString()

                    val scoreList   = item.child("Score")

                    val resultList  = item.child("Result").children

                    var tempList = ArrayList< Pair<String,Pair<Int,Int>>>()

                    for(result in resultList){

                        val resultKey = result.key.toString()

                        val score = scoreList.child(resultKey)

                        if(result.value!=null && score.value!=null){

                            var minScore = score.child("min").value.toString().toInt()

                            if(minScore == 1){

                                minScore = 0
                            }
                            val maxScore = score.child("max").value.toString().toInt()

                            val scorePair = Pair(minScore,maxScore)

                            val resultScorePair = Pair(result.value.toString(),scorePair)

                            tempList.add(resultScorePair)

                        }
                    }

                    dataList[disorder] = tempList
                }

                Log.d("DataList",dataList.toString())
                callback()
            }

        })

    }

    fun show(){

        val usrReference = FirebaseDatabase.getInstance().getReference("History")
            .child(MyAppApplication.globalUser.toString())

        usrReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for(p1 in p0.children){

                    var data  =""

                    for (p2 in p1.child("disorderScores").children){

                        val disorder = p2.key.toString()

                        val resultScoreList =  dataList[disorder]

                       // Log.d("test",resultScoreList.toString())

                        for (resultScore in resultScoreList!!){
                           if ( resultScore.second.first <= p2.value.toString().toInt()
                               && resultScore.second.second >= p2.value.toString().toInt()){
                                data = data + "\n"+disorder + " : " +resultScore.first + " "
                                break;
                            }
                        }
                    }
                    data = p1.child("lastTry").value.toString() + "  " + data

                    dataShow.add(data)
                }

                val adapter =

                    ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1,dataShow)

                listView.adapter = adapter;

            }
        })

    }

}
