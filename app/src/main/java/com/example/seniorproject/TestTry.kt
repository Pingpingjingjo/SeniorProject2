package com.example.seniorproject

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import com.google.firebase.FirebaseError
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.activity_test_try.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class TestTry : AppCompatActivity() {

    lateinit var dataReference: DatabaseReference
    var disorderNameList: MutableList<String> = mutableListOf()
    var questionLists: MutableMap<String, MutableList<TestRecord>> = mutableMapOf()
    var choiceLists: MutableMap<String, MutableList<String>> = mutableMapOf()
    var scoreLists: MutableMap<String, MutableList<Int>> = mutableMapOf()
    var scores: MutableMap<String, Int> = mutableMapOf()
    var joined = ArrayList<TestRecord>()
    val Blue = Color.parseColor("#63ace5")
    val White = Color.parseColor("#e7eff6");


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        Log.d("woohoo ", "IM IN TEST TRY BITCHES")

        dataReference = FirebaseDatabase.getInstance().getReference("Datas")
        dataReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (item in p0.children) {
                    val c = item.getKey()
                    Log.d("fuck ", c)
                    disorderNameList.add(c!!)
                    Log.d("hello",disorderNameList[0])
                }//loop for


                for (item in disorderNameList) {
                    getData(item)
                    scores[item] = 0
                }
            }
        })


        var i: Int = -1
        var firstTime: Boolean = true



        nextBtn.setOnClickListener {
            if (firstTime) {
                for (item in disorderNameList) {
                    joined.addAll(questionLists[item]!!)
                }
                joined.shuffle()
                firstTime = false
            }

            i++

            if (i == joined.size) {
                //i = joined.size -1
                testTxt.setText(" สิ้นสุดการประเมิณ กดปุ่มถัดไปเพื่อดูผลการประเมิน")
            }
            if (i > joined.size) {
                Log.d("finalA", scores["Depression"].toString())
                Log.d("finalD", scores["Anxiety"].toString())
                PageChange()
            }

            if (i < joined.size) {
                testTxt.setText(joined[i].Question)
                disorderFunction(joined[i].type)
                Clear()

            }
        }//btn


        preBtn.setOnClickListener {
            if (firstTime) {
                for (item in disorderNameList) {
                    joined.addAll(questionLists[item]!!)
                }
                joined.shuffle()
                firstTime = false
            }

            i--
            if(i<0){
                i=0
            }
            if (i >= 0) {

                testTxt.setText(joined[i].Question)
                disorderFunction(joined[i].type)
                Clear()

            }

        }//


    }


    fun getData(disorder: String) {
        Log.d("hello","imhere")

        //------------ Question ----------------

        var questionList: MutableList<TestRecord> = mutableListOf()
        var questionReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Datas/" + disorder + "/Question")
        Log.d("hello","Datas/" + disorder + "/Question")
        questionReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getValue(String::class.java)
                    Log.d("question", c)
                    questionList.add(TestRecord(c!!, disorder))
                }
            }
        })
        questionLists[disorder] = questionList

        //------------Choices ----------------
        var choiceList: MutableList<String> = mutableListOf()
        var choiceReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Datas/" + disorder + "/Choices")
        choiceReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getValue(String::class.java)
                    Log.d("choice", c)
                    choiceList.add(c!!)
                }
            }
        })
        choiceLists[disorder] = choiceList
        //-----------score-------------------
        var scoreList: MutableList<Int> = mutableListOf()
        var scoreReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Datas/" + disorder + "/Point")
        scoreReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getValue(Int::class.java)
                    // Log.d("score", c)
                    scoreList.add(c!!)
                }
            }
        })
        scoreLists[disorder] = scoreList

    }


    // Get the selected radio button text using radio button on click listener
    //------------------Choices & Button-------------------------
    fun setOnClickListener(radioButton: RadioButton, disorder: String) {

        radioButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                var checked = view as RadioButton
                if (radioButton1 == checked) {
                    scores[disorder] = scores[disorder]!! + scoreLists[disorder]!![0].toInt()!!
                    Ctxt1.setBackgroundColor(Blue)
                    Ctxt2.setBackgroundColor(White)
                    Ctxt3.setBackgroundColor(White)
                    Ctxt4.setBackgroundColor(White)
                    radioButton2.setChecked(false)
                    radioButton3.setChecked(false)
                    radioButton4.setChecked(false)

                }
                if (radioButton2 == checked) {
                    scores[disorder] = scores[disorder]!! + scoreLists[disorder]!![1].toInt()!!
                    Ctxt2.setBackgroundColor(Blue)
                    Ctxt1.setBackgroundColor(White)
                    Ctxt3.setBackgroundColor(White)
                    Ctxt4.setBackgroundColor(White)
                    radioButton1.setChecked(false)
                    radioButton3.setChecked(false)
                    radioButton4.setChecked(false)
                }

                if (radioButton3 == checked) {
                    scores[disorder] = scores[disorder]!! + scoreLists[disorder]!![2].toInt()!!
                    Ctxt1.setBackgroundColor(White)
                    Ctxt2.setBackgroundColor(White)
                    Ctxt3.setBackgroundColor(Blue)
                    Ctxt4.setBackgroundColor(White)
                    radioButton2.setChecked(false)
                    radioButton1.setChecked(false)
                    radioButton4.setChecked(false)

                }
                if (radioButton4 == checked) {
                    scores[disorder] = scores[disorder]!! + scoreLists[disorder]!![3].toInt()!!
                    Ctxt1.setBackgroundColor(White)
                    Ctxt2.setBackgroundColor(White)
                    Ctxt3.setBackgroundColor(White)
                    Ctxt4.setBackgroundColor(Blue)
                    radioButton2.setChecked(false)
                    radioButton3.setChecked(false)
                    radioButton1.setChecked(false)
                }
            }
        })
    }

    fun disorderFunction(disorder: String) {
        //Log.d("scoreList",scoreLists[disorder]!![0])
        Ctxt1.setText(choiceLists[disorder]!![0])
        Ctxt2.setText(choiceLists[disorder]!![1])
        Ctxt3.setText(choiceLists[disorder]!![2])
        Ctxt4.setText(choiceLists[disorder]!![3])

        setOnClickListener(radioButton1, disorder)
        setOnClickListener(radioButton2, disorder)
        setOnClickListener(radioButton3, disorder)
        setOnClickListener(radioButton4, disorder)
    }


    fun Clear(){

        Ctxt1.setBackgroundColor(White)
        Ctxt2.setBackgroundColor(White)
        Ctxt3.setBackgroundColor(White)
        Ctxt4.setBackgroundColor(White)
        radioButton2.setChecked(false)
        radioButton2.setChecked(false)
        radioButton3.setChecked(false)
        radioButton4.setChecked(false)

    }

    private fun PageChange() {
        //page transition
        val intent1 = Intent(this, Result::class.java)
        for (disorder in disorderNameList){
            intent1.putExtra("score"+disorder, scores[disorder].toString())
        }
        startActivity(intent1)
    }


    fun main(args: Array<String>) {
//        val current = LocalDateTime.now()
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
//        val formatted = current.format(formatter)
//        println("Current Date and Time is: $formatted")

        //create one variable (string) to keep each (formatted) day
        // check the lastest array if lastest day >= current

    }



}
