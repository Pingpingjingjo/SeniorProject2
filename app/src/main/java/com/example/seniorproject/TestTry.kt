package com.example.seniorproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.RadioButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.set
import kotlin.collections.shuffle
import com.google.firebase.database.DataSnapshot
import java.time.*
import com.google.firebase.storage.StorageException
import android.support.annotation.NonNull




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

    lateinit var context: Context

    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_test)

        context = this;

        activity= this;


    }


    override fun onPostResume() {
        super.onPostResume()

        dataReference = FirebaseDatabase.getInstance().getReference("Datas")

        radioButton1.visibility = View.INVISIBLE

        radioButton2.visibility = View.INVISIBLE

        radioButton3.visibility  =View.INVISIBLE

        radioButton4.visibility  =View.INVISIBLE

        preBtn.visibility = View.INVISIBLE

        nextBtn.text = "เริ่ม"

        dataReference.addListenerForSingleValueEvent(object : ValueEventListener {


            override fun onCancelled(p0: DatabaseError) {

                Log.d("firebase error" ,p0.details)
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (item in p0.children) {
                    val c = item.getKey()

                    //      Log.d("Full Data",item.toString());
                    disorderNameList.add(c!!)
                }


                for (item in disorderNameList) {
                    getData(item)
                    scores[item] = 0
                }

                checkInterval()
            }
        })


    }


    fun getData(disorder: String) {

        //------------ Question ----------------

        var questionList: MutableList<TestRecord> = mutableListOf()
        var questionReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Datas/" + disorder + "/Question")
      //  Log.d("hello","Datas/" + disorder + "/Question")
        questionReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getValue(String::class.java)
                        //    Log.d("question", c)
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
                 //   Log.d("choice", c)
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


    fun disorderFunction(disorder: String) {
        //Log.d("scoreList",scoreLists[disorder]!![0])
        Ctxt1.setText(choiceLists[disorder]!![0])
        Ctxt2.setText(choiceLists[disorder]!![1])
        Ctxt3.setText(choiceLists[disorder]!![2])
        Ctxt4.setText(choiceLists[disorder]!![3])


    }

    fun addPoint(disorder: String) : Int{

        var score :Int = 0
        if (radioButton1.isChecked()) {

            score = scoreLists[disorder]!![0]
            scores[disorder] = scores[disorder]!! + scoreLists[disorder]!![0].toInt()!!
            Ctxt1.setBackgroundColor(Blue)
            Ctxt2.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(White)
            Ctxt4.setBackgroundColor(White)
            radioButton2.setChecked(false)
            radioButton3.setChecked(false)
            radioButton4.setChecked(false)

        }
        else if (radioButton2.isChecked()) {
            score = scoreLists[disorder]!![1]
            scores[disorder] = scores[disorder]!! + scoreLists[disorder]!![1].toInt()!!
            Ctxt2.setBackgroundColor(Blue)
            Ctxt1.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(White)
            Ctxt4.setBackgroundColor(White)
            radioButton1.setChecked(false)
            radioButton3.setChecked(false)
            radioButton4.setChecked(false)
        }

        else if (radioButton3.isChecked()) {

            score = scoreLists[disorder]!![2]

            scores[disorder] = scores[disorder]!! + scoreLists[disorder]!![2].toInt()!!
            Ctxt1.setBackgroundColor(White)
            Ctxt2.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(Blue)
            Ctxt4.setBackgroundColor(White)
            radioButton2.setChecked(false)
            radioButton1.setChecked(false)
            radioButton4.setChecked(false)

        }
        else if (radioButton4.isChecked()) {

            score = scoreLists[disorder]!![3
            ]
            scores[disorder] = scores[disorder]!! + scoreLists[disorder]!![3].toInt()!!
            Ctxt1.setBackgroundColor(White)
            Ctxt2.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(White)
            Ctxt4.setBackgroundColor(Blue)
            radioButton2.setChecked(false)
            radioButton3.setChecked(false)
            radioButton1.setChecked(false)
        }

        return score
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
            intent1.putExtra(disorder, scores[disorder])

            Log.d("Send with",disorder + " " + scores[disorder])
        }
        startActivity(intent1)

        activity.finish();
    }

    fun setOnClickListener(radioButton: RadioButton) {

        radioButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                var checked = view as RadioButton
                if (radioButton1 == checked) {

                    Ctxt1.setBackgroundColor(Blue)
                    Ctxt2.setBackgroundColor(White)
                    Ctxt3.setBackgroundColor(White)
                    Ctxt4.setBackgroundColor(White)
                    radioButton2.setChecked(false)
                    radioButton3.setChecked(false)
                    radioButton4.setChecked(false)

                }
                else if (radioButton2 == checked) {

                    Ctxt2.setBackgroundColor(Blue)
                    Ctxt1.setBackgroundColor(White)
                    Ctxt3.setBackgroundColor(White)
                    Ctxt4.setBackgroundColor(White)
                    radioButton1.setChecked(false)
                    radioButton3.setChecked(false)
                    radioButton4.setChecked(false)
                }

                else if (radioButton3 == checked) {

                    Ctxt1.setBackgroundColor(White)
                    Ctxt2.setBackgroundColor(White)
                    Ctxt3.setBackgroundColor(Blue)
                    Ctxt4.setBackgroundColor(White)
                    radioButton2.setChecked(false)
                    radioButton1.setChecked(false)
                    radioButton4.setChecked(false)

                }
                else if (radioButton4 == checked) {

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






    private fun init(){

        var savedScore = ArrayList<Int>()

        var i: Int = -1

        var firstTime: Boolean = true

        setOnClickListener(radioButton1)

        setOnClickListener(radioButton2)

        setOnClickListener(radioButton3)

        setOnClickListener(radioButton4)



        nextBtn.setOnClickListener {

            if (firstTime) {

                for (item in disorderNameList) {

                    joined.addAll(questionLists[item]!!)
                }

                joined.shuffle()

                firstTime = false

                radioButton1.visibility = View.VISIBLE
                radioButton2.visibility = View.VISIBLE
                radioButton3.visibility  =View.VISIBLE
                radioButton4.visibility  =View.VISIBLE

                nextBtn.text = "ถัดไป"
            }



            i++

            if(i>0){


                preBtn.visibility = View.VISIBLE

            }else{

                preBtn.visibility = View.INVISIBLE
            }

            when {
                i == joined.size -> //i = joined.size -1

                    testTxt.text = " สิ้นสุดการประเมิณ กดปุ่มถัดไปเพื่อดูผลการประเมิน"


                i > joined.size -> {


                    PageChange()
                }
                i < joined.size -> {

                    if(i>0){

                       savedScore.add(addPoint(joined[i-1].type))
                    }


                    testTxt.setText(joined[i].Question)

                    disorderFunction(joined[i].type)

                    Clear()

                }
            }



        }//btn



        preBtn.setOnClickListener {


            i--

            if(i<=0){

                i=0

                preBtn.visibility = View.INVISIBLE



            }
            if (i >= 0) {

                Log.d("Current Index", i.toString())
                scores[joined[i].type] =- savedScore[i]

                savedScore.remove(i)

                testTxt.setText(joined[i].Question)

                disorderFunction(joined[i].type)

                Clear()

            }

        }//
    }


    private fun checkInterval(){

        val userHistoryRef : DatabaseReference = FirebaseDatabase.getInstance().getReference("History")

            .child(MyAppApplication.globalUser.toString())

<<<<<<< HEAD
        Log.d("checking","tet")
=======
        //notsure bout this, please delete if wrong
        userHistoryRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                    if (!p0.exists()){
                        init()
                        return
                    }
            }
        })
        //
>>>>>>> a725da00da9af421f320d9a68d38edb6501ba4a1

        val queryRef = userHistoryRef.orderByChild("lastTry").limitToLast(1)

        queryRef.addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(p0: DataSnapshot) {

                if(p0.childrenCount < 1){
                    init()
                }else{

                    for (ds in p0.children) {
                        val key = ds.getKey()
                        val lastTry = ds.child("lastTry").getValue(String::class.java)
                        Log.d("last try", lastTry)
                        val lastTime =  OffsetDateTime.parse(lastTry)
                        val curretnTime = OffsetDateTime.now()

                        var days = Duration.between(lastTime, curretnTime).toDays()

<<<<<<< HEAD
                        if(days <7){
                            val builder = AlertDialog.Builder(context)

                            builder.setMessage("กรุณา ทําแบบตรวจสอบ หลังจาก "+(7 - days) +"วัน")
=======
                        builder.setMessage("กรุณา ทําแบบตรวจสอบ หลังจาก "+(14 - days) +"วัน")

                        builder.setPositiveButton("ตกลง"){dialog, which ->
                            // Do something when user press the positive button
>>>>>>> a725da00da9af421f320d9a68d38edb6501ba4a1

                            builder.setPositiveButton("คกลง"){dialog, which ->
                                // Do something when user press the positive button

                                //  activity.finish();
                            }

                            var dialog  = builder.create();

                            dialog.setCanceledOnTouchOutside(false);

                            dialog.setCancelable(false)

                            dialog.show()

                            init()
                        }else{

                            init()

                        }
                }

                }

            }

        })
    }

}
