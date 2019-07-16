package com.example.seniorproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListAdapter
import android.widget.TextView
import com.example.seniorproject.MyAppApplication.Companion.globalUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.activity_test.*
import java.text.SimpleDateFormat
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

        Log.d("name", MyAppApplication.globalUser)


        dataReference = FirebaseDatabase.getInstance().getReference("Datas")
        dataReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (item in p0.children) {
                    val c = item.getKey()
                    Log.d("fuck ", c)
                    disorderNameList.add(c!!)
                    Log.d("hello", disorderNameList[0])
                }//loop for


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
                                Log.d("goddamn", c!!.min.toString())
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

                Thread.sleep(1000)


                var outputList = mutableListOf<String>()
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
                                    outputList.add(disorder + ": " + resultList!![resultList.size-1])
                                }
                                if (score >= rangeList[i].min && score <= rangeList[i].max) {
                                    outputList.add(disorder + ": " + resultList!![i])
                                }

                            }
                            Log.d("goddamn", rangeLists["Depression"]!![0].min.toString())
                            Log.d("goddamn", outputList[0])
                            val adapter =
                                ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, outputList)
                            results.adapter = adapter


                        }

                    })
                }


                Thread.sleep(1000)

                var lolReference: DatabaseReference =
                    FirebaseDatabase.getInstance().getReference("Datas")
                lolReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        //history bullshit
                        var usrList: MutableList<String> = mutableListOf()
                        var usrReference = FirebaseDatabase.getInstance().getReference("History")
                        usrReference.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                if (p0!!.exists()) {
                                    for (i in p0.children) {
                                        var usr = i.getKey()
                                        usrList.add(usr!!)
                                    }
                                }
                            }
                        })
                        val sdf = SimpleDateFormat("dd-M-yyyy_hh:mm:ss")
                        val currentDate = sdf.format(Date())
                        Log.d(" C DATE is  ", currentDate)
                        var addingReference = FirebaseDatabase.getInstance()
                            .getReference("History/" + MyAppApplication.globalUser + "/" + currentDate)
                        for (output in outputList) {
                            var curRef = addingReference.push()
                            curRef.setValue(output)
                            Log.d("the output is", output)
                        }
                    }

                })

            }
        })

        //// Check time stamp

//        var timeList: MutableList<String> = mutableListOf()
//        var timeReference: DatabaseReference =
//            FirebaseDatabase.getInstance().getReference("History/" + globalUser + "/sdf")
//        timeReference.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//
//                for (item in p0.children) {
//                    var c = item.getValue(String::class.java)
//                    resultList.add(c!!)
//                }
//            }
//        })
//        resultLists[disorder] = resultList
//
//    }




    } //end overide


}// finish program
