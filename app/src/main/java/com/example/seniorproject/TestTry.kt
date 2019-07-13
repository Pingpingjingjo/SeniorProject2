package com.example.seniorproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.FirebaseError
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_test_try.*

class TestTry : AppCompatActivity() {

    lateinit var dataReference: DatabaseReference
    lateinit var disorderNameList: MutableList<String>


    lateinit var dataReferenceQ: DatabaseReference
    lateinit var QuestionList: MutableList<TestRecord>

    lateinit var dataReferenceC: DatabaseReference
    lateinit var ChoiceList: MutableList<String>

    lateinit var dataReferenceS: DatabaseReference
    lateinit var ScoreListD: MutableList<String>
    var i: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_try)

        disorderNameList = mutableListOf()
        dataReference = FirebaseDatabase.getInstance().getReference("Datas")
        dataReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getKey()
                    Log.d("fuck ", c)
                    disorderNameList.add(c!!)

                    for (items in disorderNameList[i]){
                        dataReference = FirebaseDatabase.getInstance().getReference("Datas/"+ disorderNameList[i]+"/Question")
                        dataReferenceQ.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }
                            override fun onDataChange(p0: DataSnapshot) {

                                for (item in p0.children) {
                                    val c = item.getKey()
                                    Log.d("cry ", c)
//                                    QuestionList.add(c!!)
                                }
                            }
                        })

                    }

                    for (items in disorderNameList[i]){
                        dataReference = FirebaseDatabase.getInstance().getReference("Datas/"+ disorderNameList[i]+"/Score")
                        dataReferenceS.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }
                            override fun onDataChange(p0: DataSnapshot) {

                                for (item in p0.children) {
                                    val c = item.getKey()
                                    Log.d("cry ", c)
                                    ScoreListD.add(c!!)
                                }
                            }
                        })

                    }







                }//loop for
            }
        })





    }





}
