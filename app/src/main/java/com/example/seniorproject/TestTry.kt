package com.example.seniorproject

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.RadioButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_knowledge2.*
import kotlinx.android.synthetic.main.activity_test.*
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.set
import kotlin.collections.shuffle
import com.google.firebase.database.DataSnapshot
import java.time.*


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

        //initlizaed disorder scores
        dataReference = FirebaseDatabase.getInstance().getReference("Datas")

        radioButton1.visibility = View.INVISIBLE
        radioButton2.visibility = View.INVISIBLE
        radioButton3.visibility  =View.INVISIBLE
        radioButton4.visibility  =View.INVISIBLE
        preBtn.visibility = View.INVISIBLE
        nextBtn.text = "เริ่ม"

        context = this;

        activity= this;
        dataReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (item in p0.children) {
                    val c = item.getKey()
              //      Log.d("Disorders ", c)
                //      Log.d("Full Data",item.toString());
                    disorderNameList.add(c!!)
                }


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

                        testTxt.setText(joined[i].Question)
                        disorderFunction(joined[i].type)
                        Clear()

                    }
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

                preBtn.visibility = View.INVISIBLE
            }
            if (i >= 0) {

                testTxt.setText(joined[i].Question)
                disorderFunction(joined[i].type)
                Clear()

            }

        }//
    }


    override fun onPostResume() {
        super.onPostResume()

        val userHistoryRef : DatabaseReference = FirebaseDatabase.getInstance().getReference("History").child(MyAppApplication.globalUser.toString())

        val queryRef = userHistoryRef.orderByChild("lastTry").limitToLast(1)

        queryRef.addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(p0: DataSnapshot) {

                for (ds in p0.getChildren()) {
                    val key = ds.getKey()
                    val lastTry = ds.child("lastTry").getValue(String::class.java)
                    Log.d("last try", lastTry)
                    val lastTime =  OffsetDateTime.parse(lastTry)
                    val curretnTime = OffsetDateTime.now()

                    var days = Duration.between(lastTime, curretnTime).toDays()

                    if(days <7){
                        val builder = AlertDialog.Builder(context)

                        builder.setMessage("กรุณา ทําแบบตรวจสอบ หลังจาก "+(7 - days) +"วัน")

                        builder.setPositiveButton("คกลง"){dialog, which ->
                            // Do something when user press the positive button

                          //  activity.finish();
                        }

                        var dialog  = builder.create();

                        dialog.setCanceledOnTouchOutside(false);

                        dialog.setCancelable(false)

                        dialog.show()
                    }
                }

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
                else if (radioButton2 == checked) {
                    scores[disorder] = scores[disorder]!! + scoreLists[disorder]!![1].toInt()!!
                    Ctxt2.setBackgroundColor(Blue)
                    Ctxt1.setBackgroundColor(White)
                    Ctxt3.setBackgroundColor(White)
                    Ctxt4.setBackgroundColor(White)
                    radioButton1.setChecked(false)
                    radioButton3.setChecked(false)
                    radioButton4.setChecked(false)
                }

               else if (radioButton3 == checked) {
                    scores[disorder] = scores[disorder]!! + scoreLists[disorder]!![2].toInt()!!
                    Ctxt1.setBackgroundColor(White)
                    Ctxt2.setBackgroundColor(White)
                    Ctxt3.setBackgroundColor(Blue)
                    Ctxt4.setBackgroundColor(White)
                    radioButton2.setChecked(false)
                    radioButton1.setChecked(false)
                    radioButton4.setChecked(false)

                }
               else if (radioButton4 == checked) {
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
            intent1.putExtra(disorder, scores[disorder])
        }
        startActivity(intent1)

        activity.finish();
    }

//    fun timestamp(args: Array<String>) {
//        val current = LocalDateTime.now()
//        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
//        val formatted = current.format(formatter)
//        println("Current Date is: $formatted")
//
//    }

//    fun timeout() = runBlocking {
//        //sampleStart
//        val result = withTimeoutOrNull(150000L) {
//            repeat(10) { i ->
//                Log.d("I'm sleeping",i.toString())
//               delay(500L)
//            }
//            "Done" // will get cancelled before it produces this result
//        }
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        Log.d("Result is ",result.toString())
////sampleEnd
//    }



    fun changehome(){

        val intent1 = Intent(this, Home::class.java)
        startActivity(intent1)

    }


    /*

    private void setScreenTimeout(int millseconds){
        boolean value = false;
        if(android.os.Build.VERSION.SDK_INK >= andriod.os.Build.VERSON_CODES>M)}{
        value = Settings.System.canWrite(getApplicationContext());
        if(value){
            Setting.system.putInt(getContentResolver(),Settings.system.SCREEN_OFF_TIMEOUT,milliseconds);
            success = true;
        }
        else{
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName() ))
            startActivity(intent);
        }
        }
        else{
        Setting.System.putInt(getContentResolver(), Setting.System.SCREEN_OFF_TIMEOUT,milliseconds)
        sucess =  true
        }
    }
    */





}
