package com.example.seniorproject


import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_test.*
import com.google.firebase.database.DatabaseReference
import java.util.ArrayList


class Test : AppCompatActivity() {
    // for read database
    var value: String = " "
    var scoreD = 0
    var scoreA = 0
    var scoreS = 0



    val Blue = Color.parseColor("#63ace5")
    val White = Color.parseColor("#e7eff6");

    //------------ Depression------
    lateinit var dataReferenceQD: DatabaseReference
    lateinit var QuestionList: MutableList<TestRecord>

    lateinit var dataReferenceCD: DatabaseReference
    lateinit var ChoiceList: MutableList<String>

    lateinit var dataReferenceSD: DatabaseReference
    lateinit var ScoreListD: MutableList<String>


    //--------------Anxiety----------------
    lateinit var dataReferenceQA: DatabaseReference
    lateinit var QuestionListA: MutableList<TestRecord>

    lateinit var dataReferenceCA: DatabaseReference
    lateinit var ChoiceListA: MutableList<String>

    lateinit var dataReferenceSA: DatabaseReference
    lateinit var ScoreListA: MutableList<String>



    val joined = ArrayList<TestRecord>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        var i: Int = -1
        var firstTime : Boolean = true


        depressionDatas()
        anxietyDatas()

        nextBtn.setOnClickListener {
            if (firstTime) {
                joined.addAll(QuestionList)
                joined.addAll(QuestionListA)
                joined.shuffle()
                firstTime = false
            }

            i++

            if (i== joined.size){
                //i = joined.size -1
                testTxt.setText(" สิ้นสุดการประเมิณ กดปุ่มถัดไปเพื่อดูผลการประเมิน")
            }
            if (i > joined.size){
                Log.d("finalA",scoreA.toString())
                Log.d("finalD",scoreD.toString())
                PageChange()
            }

            if (i < joined.size) {
                testTxt.setText(joined[i].Question)
                if (joined[i].type.equals("depression")) {
                    depressionFunction()
                    Clear()


                } else if (joined[i].type.equals("anxiety")) {
                    anxietyFunction()
                    Clear()

                }



            }
        }//btn

        preBtn.setOnClickListener {
            if (firstTime) {
                joined.addAll(QuestionList)
                joined.addAll(QuestionListA)
                joined.shuffle()
                firstTime = false
            }

            i--
            if(i<0){
                i=0
            }
            if (i >= 0) {

                testTxt.setText(joined[i].Question)
                if (joined[i].type.equals("depression")) {
                    depressionFunction()
                    Clear()

                } else if (joined[i].type.equals("anxiety")) {
                    anxietyFunction()
                    Clear()
                }

            }

        }//btn


    }// OncreateEnd

    fun Try() {
    radio_group.setOnCheckedChangeListener(
    RadioGroup.OnCheckedChangeListener
    {
        group, checkedId ->
        val radio: RadioButton = this.findViewById(checkedId)
//                Toast.makeText(applicationContext," On checked change : ${radio.text}",
//                    Toast.LENGTH_SHORT).show()
    })
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


    // Get the selected radio button text using radio button on click listener
    //------------------Depression_Choices & Button-------------------------
    fun DepressionRadioButtonClick(view: View){
        var checked = view as RadioButton
        if (radioButton1 == checked) {
            scoreD = scoreD + ScoreListD[0].toInt()
            Ctxt1.setBackgroundColor(Blue)
            Ctxt2.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(White)
            Ctxt4.setBackgroundColor(White)
            radioButton2.setChecked(false)
            radioButton3.setChecked(false)
            radioButton4.setChecked(false)

        }
        if (radioButton2 == checked) {
            scoreD = scoreD + ScoreListD[1].toInt()
            Ctxt2.setBackgroundColor(Blue)
            Ctxt1.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(White)
            Ctxt4.setBackgroundColor(White)
            radioButton1.setChecked(false)
            radioButton3.setChecked(false)
            radioButton4.setChecked(false)
        }

        if (radioButton3 == checked) {
            scoreD = scoreD + ScoreListD[2].toInt()
            Ctxt1.setBackgroundColor(White)
            Ctxt2.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(Blue)
            Ctxt4.setBackgroundColor(White)
            radioButton2.setChecked(false)
            radioButton1.setChecked(false)
            radioButton4.setChecked(false)

        }
        if (radioButton4 == checked) {
            scoreD = scoreD + ScoreListD[3].toInt()
            Ctxt1.setBackgroundColor(White)
            Ctxt2.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(White)
            Ctxt4.setBackgroundColor(Blue)
            radioButton2.setChecked(false)
            radioButton3.setChecked(false)
            radioButton1.setChecked(false)
        }
    }
        fun depressionFunction(){
            Ctxt1.setText(ChoiceList[0])
            Ctxt2.setText(ChoiceList[1])
            Ctxt3.setText(ChoiceList[2])
            Ctxt4.setText(ChoiceList[3])

            radioButton1.setOnClickListener(this::DepressionRadioButtonClick)
            radioButton2.setOnClickListener(this::DepressionRadioButtonClick)
            radioButton3.setOnClickListener(this::DepressionRadioButtonClick)
            radioButton4.setOnClickListener(this::DepressionRadioButtonClick)

        }


    //------------------Anxiety_Choices & Button-------------------------
    fun AnxietyRadioButtonClick(view: View){
        var checked = view as RadioButton
        if (radioButton1 == checked) {
            scoreA = scoreA + ScoreListA[0].toInt()
            Ctxt1.setBackgroundColor(Blue)
            Ctxt2.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(White)
            Ctxt4.setBackgroundColor(White)
            radioButton2.setChecked(false)
            radioButton3.setChecked(false)
            radioButton4.setChecked(false)

        }
        if (radioButton2 == checked) {
            scoreA = scoreA + ScoreListA[1].toInt()
            Ctxt2.setBackgroundColor(Blue)
            Ctxt1.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(White)
            Ctxt4.setBackgroundColor(White)
            radioButton1.setChecked(false)
            radioButton3.setChecked(false)
            radioButton4.setChecked(false)
        }

        if (radioButton3 == checked) {
            scoreA = scoreA + ScoreListA[2].toInt()
            Ctxt1.setBackgroundColor(White)
            Ctxt2.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(Blue)
            Ctxt4.setBackgroundColor(White)
            radioButton2.setChecked(false)
            radioButton1.setChecked(false)
            radioButton4.setChecked(false)

        }
        if (radioButton4 == checked) {
            scoreA = scoreA + ScoreListA[3].toInt()
            Ctxt1.setBackgroundColor(White)
            Ctxt2.setBackgroundColor(White)
            Ctxt3.setBackgroundColor(White)
            Ctxt4.setBackgroundColor(Blue)
            radioButton2.setChecked(false)
            radioButton3.setChecked(false)
            radioButton1.setChecked(false)
        }
    }
    fun anxietyFunction(){

        Ctxt1.setText(ChoiceListA[0])
        Ctxt2.setText(ChoiceListA[1])
        Ctxt3.setText(ChoiceListA[2])
        Ctxt4.setText(ChoiceListA[3])


        radioButton1.setOnClickListener(this::AnxietyRadioButtonClick)
        radioButton2.setOnClickListener(this::AnxietyRadioButtonClick)
        radioButton3.setOnClickListener(this::AnxietyRadioButtonClick)
        radioButton4.setOnClickListener(this::AnxietyRadioButtonClick)

    }

    //------------------Social Choices & Button-------------------------





    private fun PageChange() {
        //page transition
        val intent1 = Intent(this, Result::class.java)
        intent1.putExtra("ScoreDepression",scoreD.toString())
        intent1.putExtra("ScoreAnxiety",scoreA.toString())
        intent1.putExtra("ScoreSocial",scoreS.toString())

        startActivity(intent1)
    }
    //---------------------------Depression-------------------------
    fun depressionDatas(){

    //------------ Question ----------------

    QuestionList = mutableListOf()
    dataReferenceQD = FirebaseDatabase.getInstance().getReference("Datas/Depression/Question")
    dataReferenceQD.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }
        override fun onDataChange(p0: DataSnapshot) {

            for (item in p0.children) {
                val c = item.getValue(String::class.java)
                Log.d("Where:: ", c)
                QuestionList.add(TestRecord(c!!,"depression"))
            }
        }
    })
    //------------Choices ----------------
    ChoiceList = mutableListOf()
    dataReferenceCD = FirebaseDatabase.getInstance().getReference("Datas/Depression/Choices")
    dataReferenceCD.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }
        override fun onDataChange(p0: DataSnapshot) {

            for (item in p0.children) {
                val c = item.getValue(String::class.java)
                Log.d("Where:: ", c)
                ChoiceList.add(c!!)
            }
        }
    })
        //-----------score-------------------
        ScoreListD = mutableListOf()
        dataReferenceSD = FirebaseDatabase.getInstance().getReference("Datas/Depression/score")
        dataReferenceSD.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getValue(String::class.java)
                    Log.d("scoreD", c)
                    ScoreListD.add(c!!)
                }
            }
        })



}
    //---------------------------AnxietyDatas-------------------------
    fun anxietyDatas(){
        //------------ Question ----------------

        QuestionListA = mutableListOf()
        dataReferenceQA = FirebaseDatabase.getInstance().getReference("Datas/Anxiety/Question")
        dataReferenceQA.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getValue(String::class.java)
                    Log.d("Where:: ", c)
                    QuestionListA.add(TestRecord(c!!,"anxiety"))
                }
            }
        })
        //------------Choices ----------------
        ChoiceListA = mutableListOf()
        dataReferenceCA = FirebaseDatabase.getInstance().getReference("Datas/Anxiety/Choices")
        dataReferenceCA.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getValue(String::class.java)
                    Log.d("Where:: ", c)
                    ChoiceListA.add(c!!)
                }
            }
        })
        //----------------Score1-------------
        ScoreListA = mutableListOf()
        dataReferenceSA = FirebaseDatabase.getInstance().getReference("Datas/Anxiety/score1")
        dataReferenceSA.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {

                for (item in p0.children) {
                    val c = item.getValue(String::class.java)
                    Log.d("scoreA ", c)
                    ScoreListA.add(c!!)
                }
            }
        })



    }


}//End program
