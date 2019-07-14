package com.example.seniorproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.activity_test.*

class Result : AppCompatActivity() {
    //----------    Depression---------
    lateinit var dataReferenceSD1: DatabaseReference
    lateinit var ScoreListD1: MutableList<String>

    lateinit var dataReferenceSD2: DatabaseReference
    lateinit var ScoreListD2: MutableList<String>

    lateinit var dataReferenceSD3: DatabaseReference
    lateinit var ScoreListD3: MutableList<String>

    lateinit var dataReferenceSD4: DatabaseReference
    lateinit var ScoreListD4: MutableList<String>

    lateinit var dataReferenceRD: DatabaseReference
    lateinit var ResultListD: MutableList<String>

    //----------    Anxiety-------------

    lateinit var dataReferenceSA1: DatabaseReference
    lateinit var ScoreListA1: MutableList<String>

    lateinit var dataReferenceSA2: DatabaseReference
    lateinit var ScoreListA2: MutableList<String>

    lateinit var dataReferenceSA3: DatabaseReference
    lateinit var ScoreListA3: MutableList<String>

    lateinit var dataReferenceSA4: DatabaseReference
    lateinit var ScoreListA4: MutableList<String>

    lateinit var dataReferenceRA: DatabaseReference
    lateinit var ResultListA: MutableList<String>


//    var inputD: String =""
//    var inputA: String =""
//    var inputS: String =""

    var inputD: String ="0"
    var inputA: String ="9"
    var inputS: String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)




//        val bundle = intent.extras
//        if(bundle!=null){
//            inputD = bundle.getString("ScoreDepression")!!
//            inputA = bundle.getString("ScoreAnxiety")!!
//            inputS = bundle.getString("ScoreSocial")!!
//
//        }

//        DresultForText()
//        AresulForText()
//        resultForImg()
//        depression()
   //     anxiety()
//        imageButton.setOnClickListener{
//                    DresultForText()
//                    resultForImg()
//
//
//        }
//
//
//
//
//
//
//        }// oncreate
//    fun DresultForText(){
//
////        if (inputD.toInt() >= ScoreListD1[1].toInt() && inputD.toInt() < ScoreListD1[0].toInt()){
////           Log.d("Toint",inputD)
////            //textView14.setText(ResultListD[0]).toString()
////        }
//        if (inputD >= ScoreListD1[1]&& inputD < ScoreListD1[0]){
//            textView14.setText(ResultListD[0]).toString()
//        }
//        else if (inputD>= ScoreListD2[1]&& inputD < ScoreListD2[0]){
//            textView14.setText(ResultListD[1])
//        }
//        else if (inputD>= ScoreListD3[1]&& inputD < ScoreListD3[0]){
//            textView14.setText(ResultListD[2])
//        }
//        else if (inputD>= ScoreListD4[1]&& inputD < ScoreListD4[0]){
//            textView14.setText(ResultListD[3])
//        }
//        else{
//            textView14.setText(ResultListD[4])
//        }
//
//    }
//    fun AresulForText(){
//        if (inputA >= ScoreListA1[1]&& inputA < ScoreListA1[0]){
//            textView16.setText(ResultListA[0])
//        }
//        else if(inputA >= ScoreListA2[1]&& inputA < ScoreListA2[0]){
//            textView16.setText(ResultListA[1])
//        }
//        else if(inputA >= ScoreListA3[1]&& inputA < ScoreListA3[0]){
//            textView16.setText(ResultListA[2])
//        }
//        else if(inputA >= ScoreListA4[1]&& inputA < ScoreListA4[0]){
//            textView16.setText(ResultListA[3])
//        }
//        else{
//            textView16.setText(ResultListA[4])
//        }
//
//    }
//
//    fun resultForImg(){
//        if (inputD.toInt()==0 && inputA.toInt()==0){
//            dtxt.setText(inputD+"%").toString()
//            atxt.setText(inputA+"%").toString()
//            datxt.setText("0%").toString()
//        }
//        else if(inputD.toInt()>0 && inputA.toInt()==0){
//            var a = (((inputD.toInt())* 100)/ScoreListD4[0].toInt()).toString()
//
//            dtxt.setText(a + "%").toString()
//            atxt.setText(inputA + "%").toString()
//            datxt.setText("0%").toString()
//        }
//        else if(inputD.toInt()==0 && inputA.toInt()>0){
//            var a = (((inputA.toInt())* 100)/ScoreListD4[0].toInt()).toString()
//
//            dtxt.setText(inputD + "%").toString()
//            atxt.setText(a + "%").toString()
//            datxt.setText("0%").toString()
//        }
//        else if(inputD.toInt()>0 && inputA.toInt()>0){
//            var a = (((inputA.toInt())* 100)/ScoreListD4[0].toInt()).toString()
//            var a = (((inputA.toInt())* 100)/ScoreListD4[0].toInt()).toString()
//            dtxt.setText(inputD + "%").toString()
//            atxt.setText(a + "%").toString()
//            datxt.setText("0%").toString()
//        }


    }



    fun anxiety() {
        //------------Result ----------------
        ResultListA = mutableListOf()
        dataReferenceRA = FirebaseDatabase.getInstance().getReference("Datas/Anxiety/Result")
        dataReferenceRA.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getValue(String::class.java)
                    Log.d("Where:: ", c)
                    ResultListA.add(c!!)
                }
            }
        })

        //------------Score ----------------
        ScoreListA1 = mutableListOf()
        dataReferenceSA1 = FirebaseDatabase.getInstance().getReference("Datas/Anxiety/Score/1")
        dataReferenceSA1.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {

                    for (item in p0.children) {
                        val c = item.getValue(String::class.java)
                        Log.d("scoreanxiety1:: ", c)
                        ScoreListA1.add(c!!)
                    }
                }
            })

        ScoreListA2 = mutableListOf()
        dataReferenceSA2 = FirebaseDatabase.getInstance().getReference("Datas/Anxiety/Score/2")
        dataReferenceSA2.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {

                    for (item in p0.children) {
                        val c = item.getValue(String::class.java)
                        Log.d("scoreanxiety2:: ", c)
                        ScoreListA2.add(c!!)
                    }
                }
            })
        ScoreListA3 = mutableListOf()
        dataReferenceSA3 = FirebaseDatabase.getInstance().getReference("Datas/Anxiety/Score/3/min")
        dataReferenceSA3.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {

                    for (item in p0.children) {
                        val c = item.getValue(String::class.java)
                        Log.d("scoreanxiety3:: ", c)
                        ScoreListA3.add(c!!)
                    }
                }
            })
        ScoreListA4 = mutableListOf()
        dataReferenceSA4 = FirebaseDatabase.getInstance().getReference("Datas/Anxiety/Score/4")
        dataReferenceSA4.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {

                    for (item in p0.children) {
                        val c = item.getValue(String::class.java)
                        Log.d("scoreanxiety4:: ", c)
                        ScoreListA4.add(c!!)
                    }
                }
            })


    }//--                   End Anxiety                  -------


    //--                   Depression                  -------
    fun depression() {

        //------------Result ----------------
        ResultListD = mutableListOf()
        dataReferenceRD = FirebaseDatabase.getInstance().getReference("Datas/Depression/Result")
        dataReferenceRD.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getValue(String::class.java)
                    Log.d("Where:: ", c)
                    ResultListD.add(c!!)
                }
            }
        })

        //------------Score ----------------
        ScoreListD1 = mutableListOf()
        dataReferenceSD1 = FirebaseDatabase.getInstance().getReference("Datas/Depression/Score/1")
        dataReferenceSD1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getValue(String::class.java)
                    Log.d("scoredepression1 ", c)
                    ScoreListD1.add(c!!)
                }
            }
        })

        ScoreListD2 = mutableListOf()
        dataReferenceSD2 = FirebaseDatabase.getInstance().getReference("Datas/Depression/Score/2")
        dataReferenceSD2.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getValue(String::class.java)
                    Log.d("scoredepression2 ", c)
                    ScoreListD2.add(c!!)
                }
            }
        })

        ScoreListD3 = mutableListOf()
        dataReferenceSD3 = FirebaseDatabase.getInstance().getReference("Datas/Depression/Score/3")
        dataReferenceSD3.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getValue(String::class.java)
                    Log.d("scoredepression3 ", c)
                    ScoreListD3.add(c!!)
                }
            }
        })
        ScoreListD4 = mutableListOf()
        dataReferenceSD4 = FirebaseDatabase.getInstance().getReference("Datas/Depression/Score/4")
        dataReferenceSD4.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getValue(String::class.java)
                    Log.d("scoredepression4 ", c)
                    ScoreListD4.add(c!!)
                }
            }
        })
    }//--                   End depression                  -------

}// finish program
