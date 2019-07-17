package com.example.seniorproject

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import kotlin.collections.HashMap

class Result : AppCompatActivity() {


    lateinit var dataReference: DatabaseReference
    var disorderNameList: MutableList<String> = mutableListOf()
    var scores: MutableMap<String, Int> = mutableMapOf()
    var rangeLists: MutableMap<String, MutableList<Range>> = mutableMapOf()
    var resultLists: MutableMap<String, MutableList<String>> = mutableMapOf()
    private lateinit var listView : ListView

    lateinit var context : Context


////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        context = this

        listView = findViewById(R.id.results)

        dataReference = FirebaseDatabase.getInstance().getReference("Datas")

        dataReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (item in p0.children) {
                    val c = item.getKey()

                    disorderNameList.add(c!!)

                }

                val bundle = intent.extras
                if (bundle != null) {
                    for (disorder in disorderNameList) {

                        scores[disorder] = bundle.getInt(disorder)

                    }
                }

                Log.d("Score",scores.toString())

                populateRangeList { populateResultList { populateOutput() } }
            }
        })

    } //end overide

    private fun saveData(){

        val sdf = SimpleDateFormat("dd-M-yyyy_hh:mm:ss")

        var addingReference = FirebaseDatabase.getInstance()
            .getReference("History/" + MyAppApplication.globalUser )

        addingReference.addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {


                /*
                    History{

                        userName : {

                            lastTry1:  disordersScore : {
                                {disorder1:score1}, {disorder2:score2},{ disorderN:scoreN}
                            } ,
                            lastTry2:  disordersScore : {
                                {disorder1:score1}, {disorder2:score2},{ disorderN:scoreN}
                            } ,


                        }
                    }
                 */


                val userMap = HashMap<String,Any>()

                val lastTry = DateTimeFormatter.ISO_INSTANT.format(Instant.now())

                userMap["lastTry"] = lastTry;

                userMap["disorderScores"] = scores

                addingReference.push().setValue(userMap)

            }

        })


    }
    private fun populateOutput(){

        var outputList = mutableListOf<ResultData>()

        var count : Int = disorderNameList.size

        for (disorder in disorderNameList) {

            val herpReference: DatabaseReference =
                FirebaseDatabase.getInstance()
                    .getReference("Datas/" + disorder + "/Result")
            herpReference.addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {

                    var rangeList2 = rangeLists[disorder]
                    var resultList2 = resultLists[disorder]
                    var score = scores[disorder]!!
                    for (i in 0 until rangeList2!!.size) {
                        if (i == 0 && score < rangeList2[0].min) {

                            val resultData = ResultData(
                                disorder,
                                score,
                                resultList2!![resultList2.size - 1],
                                0.00
                            );
                            // calculate percent here
                            outputList.add(resultData);
                        } else if (score >= rangeList2[i].min && score <= rangeList2[i].max) {

                            //calculate percent here
                            val resultData = ResultData(
                                disorder,
                                score,
                                resultList2!![i],
                                0.00
                            )
                            outputList.add(resultData)


                        }

                    }

                    count--


                    if(count ==0){

                        Log.d("Data",outputList.toString())

                        val adapter = ResultAdapter(context, outputList)

                        listView.adapter = adapter

                        saveData()
                    }

                }

            })



        }
    }

    private fun populateResultList(callback:()->Unit){

        var count : Int = disorderNameList.size;

        Log.d("Count @ Populate Result",count.toString())
        for (disorder in disorderNameList) {

            var tempResultList: MutableList<String> = mutableListOf()

            var resultReference: DatabaseReference =
                FirebaseDatabase.getInstance().getReference("Datas/" + disorder + "/Result")
            resultReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {

                    for (item in p0.children) {
                        var c = item.getValue(String::class.java)
                        tempResultList.add(c!!)
                    }

                    resultLists[disorder] = tempResultList

                    count--

                    if(count==0){

                        callback()
                    }
                }
            })
        }
    }

   private fun populateRangeList( callback: ()->Unit ) {

        var count : Int = disorderNameList.size

       for (disorder in disorderNameList) {

           var tempRangList: MutableList<Range> = mutableListOf()
           var rangeReference: DatabaseReference =
               FirebaseDatabase.getInstance().getReference("Datas/" + disorder + "/Score")
           rangeReference.addListenerForSingleValueEvent(object : ValueEventListener {
               override fun onCancelled(p0: DatabaseError) {
               }

               override fun onDataChange(p0: DataSnapshot) {

                   for (item in p0.children) {
                       var c = item.getValue(Range::class.java)

                       tempRangList.add(c!!)
                   }

                   rangeLists[disorder] = tempRangList

                   count--

                   if(count ==0 ){


                       callback()
                   }
               }
           })
       }
    }

}// finish program
