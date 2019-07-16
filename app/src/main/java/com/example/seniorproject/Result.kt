package com.example.seniorproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.seniorproject.MyAppApplication.Companion.globalUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_result.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

class Result : AppCompatActivity() {


    lateinit var dataReference: DatabaseReference
    var disorderNameList: MutableList<String> = mutableListOf()
    var scores: MutableMap<String, Int> = mutableMapOf()
    var rangeLists: MutableMap<String, MutableList<Range>> = mutableMapOf()
    var resultLists: MutableMap<String, MutableList<String>> = mutableMapOf()
    var timeList: MutableMap<String, MutableList<String>> = mutableMapOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

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


                for (disorder in disorderNameList) {
                    var rangeList: MutableList<Range> = mutableListOf()
                    var rangeReference: DatabaseReference =
                        FirebaseDatabase.getInstance().getReference("Datas/" + disorder + "/Score")
                    rangeReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(p0: DataSnapshot) {

                            for (item in p0.children) {
                                var c = item.getValue(Range::class.java)

                                rangeList.add(c!!)
                            }
                        }
                    })
                    rangeLists[disorder] = rangeList


                    var resultList: MutableList<String> = mutableListOf()


                    var resultReference: DatabaseReference =
                        FirebaseDatabase.getInstance().getReference("Datas/" + disorder + "/Result")
                    resultReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(p0: DataSnapshot) {

                            for (item in p0.children) {
                                var c = item.getValue(String::class.java)
                                resultList.add(c!!)
                            }
                        }
                    })
                    resultLists[disorder] = resultList

                }

                Thread.sleep(300)


                var outputList = mutableListOf<ResultData>()
                for (disorder in disorderNameList) {

                    var herpReference: DatabaseReference =
                        FirebaseDatabase.getInstance().getReference("Datas/" + disorder + "/Result")
                    herpReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(p0: DataSnapshot) {

                            for (item in p0.children) {

                            }
                            var rangeList = rangeLists[disorder]
                            var resultList = resultLists[disorder]
                            var score = scores[disorder]!!
                            for (i in 0 until rangeList!!.size) {
                                if (i == 0 && score < rangeList[0].min) {

                                    val resultData = ResultData(disorder,score,resultList!![resultList.size-1],0.00);
                                   // calculate percent here
                                    outputList.add(resultData);
                                }
                                if (score >= rangeList[i].min && score <= rangeList[i].max) {

                                    //calculate percent here
                                    val resultData = ResultData(disorder,score,resultList!![i],0.00);
                                    outputList.add(resultData)
                                }

                            }



                            val adapter =

                                ResultAdapter(applicationContext, outputList)

                            results.adapter = adapter



                        }

                    })
                }


                Thread.sleep(300)

                var lolReference: DatabaseReference =
                    FirebaseDatabase.getInstance().getReference("Datas")
                lolReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        //history bullshi

                        val sdf = SimpleDateFormat("dd-M-yyyy_hh:mm:ss")
                        val currentDate = sdf.format(Date())

                        var addingReference = FirebaseDatabase.getInstance()
                            .getReference("History/" + globalUser )

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

                })

            }
        })






    } //end overide


}// finish program
