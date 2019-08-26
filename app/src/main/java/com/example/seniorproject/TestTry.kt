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
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.set
import kotlin.collections.shuffle
import com.google.firebase.database.DataSnapshot
import java.time.*
import android.widget.TextView
import kotlin.collections.ArrayList


class TestTry : AppCompatActivity() {

    lateinit var dataReference: DatabaseReference
    var disorderNameList: MutableList<String> = mutableListOf()
    var quesChoicesLists: MutableMap<String, MutableList<QuesChoices>> = mutableMapOf()
    //var questionLists: MutableMap<String, MutableList<TestRecord>> = mutableMapOf()
    //var choiceLists: MutableMap<String, MutableList<String>> = mutableMapOf()
    //var scoreLists: MutableMap<String, MutableList<Int>> = mutableMapOf()
    var scores: MutableMap<String, Int> = mutableMapOf()
    var joined = ArrayList<QuesChoices>()

    val White = Color.parseColor("#e7f4fa")
    val Blue = Color.parseColor("#a2d3eb");

    lateinit var context: Context

    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_test)

        context = this;

        activity = this;


    }


    override fun onPostResume() {
        super.onPostResume()

        dataReference = FirebaseDatabase.getInstance().getReference("Data4")

        Ctxt1.visibility = View.INVISIBLE

        Ctxt2.visibility = View.INVISIBLE

        Ctxt3.visibility = View.INVISIBLE

        Ctxt4.visibility = View.INVISIBLE

        preBtn.visibility = View.INVISIBLE

        nextBtn.text = "เริ่ม"

        dataReference.addListenerForSingleValueEvent(object : ValueEventListener {


            override fun onCancelled(p0: DatabaseError) {

                Log.d("firebase error", p0.details)
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

        var quesChoicesList: MutableList<QuesChoices> = mutableListOf()
        var quesChoicesReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Data4/" + disorder + "/QuesChoices")
        //  Log.d("hello","Datas/" + disorder + "/Question")
        quesChoicesReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val question: String = item.key!!
                    var choiceRef: DataSnapshot = item.child("Choices")
                    var choiceList: MutableList<String> = mutableListOf()
                    for (choice in choiceRef.children) {
                        choiceList.add(choice.getValue(String::class.java)!!)
                    }
                    var pointRef: DataSnapshot = item.child("Point")
                    var pointList: MutableList<Int> = mutableListOf()
                    for (point in pointRef.children) {
                        pointList.add(point.getValue(Int::class.java)!!)
                    }
                    //    Log.d("question", c)
                    quesChoicesList.add(QuesChoices(disorder, question, choiceList, pointList))
                }
            }
        })
        quesChoicesLists[disorder] = quesChoicesList


    }


    // Get the selected radio button text using radio button on click listener
    //------------------Choices & Button-------------------------


    fun disorderFunction(quesChoices: QuesChoices) {
        //Log.d("scoreList",scoreLists[disorder]!![0])
        var choiceTexts = ArrayList<TextView>()
        choiceTexts.add(Ctxt1)
        choiceTexts.add(Ctxt2)
        choiceTexts.add(Ctxt3)
        choiceTexts.add(Ctxt4)
        for (i in 0 until choiceTexts.size) {
            if (i >= quesChoices.choices.size) {
                choiceTexts[i].visibility = View.INVISIBLE
            } else {
                choiceTexts[i].visibility = View.VISIBLE
                choiceTexts[i].setText(quesChoices.choices[i])
            }
        }
    }

    fun addPoint(quesChoices: QuesChoices): Int {

        var score: Int = -1
        if (Ctxt1.isChecked()) {
            score = quesChoices.points[0]
            scores[quesChoices.type] = scores[quesChoices.type]!! + quesChoices.points!![0].toInt()!!
            Ctxt1.setBackgroundColor(Blue)
            Ctxt2.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(White)
            Ctxt4.setBackgroundColor(White)
            Ctxt2.setChecked(false)
            Ctxt3.setChecked(false)
            Ctxt4.setChecked(false)

        } else if (Ctxt2.isChecked()) {
            score = quesChoices.points[1]
            scores[quesChoices.type] = scores[quesChoices.type]!! + quesChoices.points!![1].toInt()!!
            Ctxt2.setBackgroundColor(Blue)
            Ctxt1.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(White)
            Ctxt4.setBackgroundColor(White)
            Ctxt1.setChecked(false)
            Ctxt3.setChecked(false)
            Ctxt4.setChecked(false)
        } else if (Ctxt3.isChecked()) {

            score = quesChoices.points[2]
            scores[quesChoices.type] = scores[quesChoices.type]!! + quesChoices.points!![2].toInt()!!
            Ctxt1.setBackgroundColor(White)
            Ctxt2.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(Blue)
            Ctxt4.setBackgroundColor(White)
            Ctxt2.setChecked(false)
            Ctxt1.setChecked(false)
            Ctxt4.setChecked(false)

        } else if (Ctxt4.isChecked()) {

            score = quesChoices.points[3]
            scores[quesChoices.type] = scores[quesChoices.type]!! + quesChoices.points!![3].toInt()!!
            Ctxt1.setBackgroundColor(White)
            Ctxt2.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(White)
            Ctxt4.setBackgroundColor(Blue)
            Ctxt2.setChecked(false)
            Ctxt3.setChecked(false)
            Ctxt1.setChecked(false)
        }
        return score
    }

    fun Clear() {

        Ctxt1.setBackgroundColor(White)
        Ctxt2.setBackgroundColor(White)
        Ctxt3.setBackgroundColor(White)
        Ctxt4.setBackgroundColor(White)
        Ctxt2.setChecked(false)
        Ctxt2.setChecked(false)
        Ctxt3.setChecked(false)
        Ctxt4.setChecked(false)
        radiogroup.clearCheck()

    }

    private fun PageChange() {
        //page transition
        val intent1 = Intent(this, Result::class.java)
        for (disorder in disorderNameList) {
            intent1.putExtra(disorder, scores[disorder])

            Log.d("Send with", disorder + " " + scores[disorder])
        }
        startActivity(intent1)

        activity.finish();
    }

    fun setOnClickListener(radioButton: RadioButton) {

        radioButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                var checked = view as RadioButton
                if (Ctxt1 == checked) {
                    Ctxt1.setChecked(true)

                    Ctxt1.setBackgroundColor(Blue)
                    Ctxt2.setBackgroundColor(White)
                    Ctxt3.setBackgroundColor(White)
                    Ctxt4.setBackgroundColor(White)
                    Ctxt2.setChecked(false)
                    Ctxt3.setChecked(false)
                    Ctxt4.setChecked(false)

                } else if (Ctxt2 == checked) {
                    Ctxt2.setChecked(true)

                    Ctxt2.setBackgroundColor(Blue)
                    Ctxt1.setBackgroundColor(White)
                    Ctxt3.setBackgroundColor(White)
                    Ctxt4.setBackgroundColor(White)
                    Ctxt1.setChecked(false)
                    Ctxt3.setChecked(false)
                    Ctxt4.setChecked(false)
                } else if (Ctxt3 == checked) {
                    Ctxt3.setChecked(true)

                    Ctxt1.setBackgroundColor(White)
                    Ctxt2.setBackgroundColor(White)
                    Ctxt3.setBackgroundColor(Blue)
                    Ctxt4.setBackgroundColor(White)
                    Ctxt2.setChecked(false)
                    Ctxt1.setChecked(false)
                    Ctxt4.setChecked(false)

                } else if (Ctxt4 == checked) {
                    Ctxt4.setChecked(true)

                    Ctxt1.setBackgroundColor(White)
                    Ctxt2.setBackgroundColor(White)
                    Ctxt3.setBackgroundColor(White)
                    Ctxt4.setBackgroundColor(Blue)
                    Ctxt2.setChecked(false)
                    Ctxt3.setChecked(false)
                    Ctxt1.setChecked(false)
                }
            }
        })
    }


    private fun init() {

        var savedScore = ArrayList<Int>()
        var i: Int = -1
        var firstTime: Boolean = true

        setOnClickListener(Ctxt1)
        setOnClickListener(Ctxt2)
        setOnClickListener(Ctxt3)
        setOnClickListener(Ctxt4)



        nextBtn.setOnClickListener {

            if (firstTime) {
                for (item in disorderNameList) {
                    joined.addAll(quesChoicesLists[item]!!)
                }

                joined.shuffle()

                firstTime = false

                Ctxt1.visibility = View.VISIBLE
                Ctxt2.visibility = View.VISIBLE
                Ctxt3.visibility = View.VISIBLE
                Ctxt4.visibility = View.VISIBLE

                nextBtn.text = "ถัดไป"
                i++
                testTxt.setText(joined[i].question)
                disorderFunction(joined[i])
                Clear()
                return@setOnClickListener
            }


            when {
                i > joined.size -> {
                    PageChange()
                }
                i < joined.size -> {
                    val point = addPoint(joined[i])
                    if (point < 0) {
                        i--
                    } else {
                        savedScore.add(point)
                    }
                    i++
                    if (i == joined.size) {
                        testTxt.text = " สิ้นสุดการประเมิน กดปุ่มถัดไปเพื่อดูผลการประเมิน"
                        i++
                    } else {
                        testTxt.setText(joined[i].question)
                        disorderFunction(joined[i])
                        Clear()
                    }
                }
            }

            if (i > 0) {
                preBtn.visibility = View.VISIBLE
            } else {
                preBtn.visibility = View.INVISIBLE
            }
        }//btn


        preBtn.setOnClickListener {
            i--
            if (i <= 0) {
                i = 0
                preBtn.visibility = View.INVISIBLE
            }
            if (i >= 0) {
                Log.d("Current Index", i.toString())
                scores[joined[i].type] = scores[joined[i].type]!! - savedScore[i].toInt()
                savedScore.remove(i)
                testTxt.setText(joined[i].question)
                disorderFunction(joined[i])
                Clear()
            }

        }//
    }

    private fun checkInterval() {

        val userHistoryRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("History")

            .child(MyAppApplication.globalUser.toString())

        /*notsure bout this, please delete if wrong
        userHistoryRef.addListenerForSingleValueEvent(object : ValueEventListener{

        Log.d("checking", "tet")
        //notsure bout this, please delete if wrong
        userHistoryRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (!p0.exists()) {
                    init()
                    return
                }
            }
        })

        */


        val queryRef = userHistoryRef.orderByChild("lastTry").limitToLast(1)

        queryRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(p0: DataSnapshot) {

                if (p0.childrenCount < 1) {
                    init()
                } else {

                    for (ds in p0.children) {
                        val key = ds.getKey()
                        val lastTry = ds.child("lastTry").getValue(String::class.java)
                        Log.d("last try", lastTry)
                        val lastTime = OffsetDateTime.parse(lastTry)
                        val curretnTime = OffsetDateTime.now()

                        var days = Duration.between(lastTime, curretnTime).toDays()



                        if (days < 1) {
                            val builder = AlertDialog.Builder(context)
                            builder.setMessage("กรุณา ทําแบบตรวจสอบ หลังจาก " + (1 - days) + "วัน")
                            builder.setPositiveButton(" ตกลง") { dialog, which ->
                                // Do something when user press the positive button

                                //  activity.finish();
                            }


                            var dialog = builder.create();

                            dialog.setCanceledOnTouchOutside(false);

                            dialog.setCancelable(false)

                            dialog.show()

                            init()
                        } else {

                            init()

                        }
                    }

                }

            }

        })
    }

}
