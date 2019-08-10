package com.example.seniorproject

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class History : AppCompatActivity() {

    lateinit var context : Context


    private lateinit var listView : ListView

    private var dataList  = HashMap<String, List< Pair<String, Pair<Int,Int> > >> ()

    private var dataShow = ArrayList<String> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        listView = this.findViewById(R.id.history)
        populateDataList { show() }

        context = this


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

                    dataShow.add(p1.child("lastTry").value.toString())

                    var percents: MutableMap<String, String> = mutableMapOf()


                    for (p2 in p1.child("disorderScores").children){
                        Log.d("lol","here")
                        for (p2 in p1.child("percents").children) {
                            Log.d("lol","hello")
                            val disorder = p2.key.toString()
                            val percent = p2.value.toString()
                            percents[disorder]=percent
                            Log.d("lol",disorder + percent)
                        }

                        val disorder = p2.key.toString()

                        val resultScoreList =  dataList[disorder]

                       // Log.d("test",resultScoreList.toString())

                        for (resultScore in resultScoreList!!){
                           if ( resultScore.second.first <= p2.value.toString().toInt()
                               && resultScore.second.second >= p2.value.toString().toInt()){
//                                dataShow.add(disorder + "\n" +resultScore.first + " ")
                                    dataShow.add(disorder + "  ::")
                                    dataShow.add(resultScore.first)
                               dataShow.add(percents[disorder]!!)
                               break;
                            }
                        }

                    }
                    dataShow.add("___________________________")


                }


                val adapter = ArrayAdapter(applicationContext, R.layout.history_list_view,dataShow)
                listView.adapter = adapter;



            }



        })


    }


}
