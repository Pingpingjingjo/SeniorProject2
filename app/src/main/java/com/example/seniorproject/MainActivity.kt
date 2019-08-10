package com.example.seniorproject


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigInteger
import java.security.MessageDigest


class MainActivity : AppCompatActivity() {

    lateinit var dataReference: DatabaseReference
    lateinit var msgList: MutableList<SignUpRecord>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView2.setOnClickListener {
            val intent2 = Intent(this, signUp::class.java);
            startActivity(intent2);

        }

        msgList = mutableListOf()
        dataReference = FirebaseDatabase.getInstance().getReference("Profile")
        dataReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {


                Log.d("firebase error" ,p0.details)
            }

            override fun onDataChange(p0: DataSnapshot) {

                Log.d("userList",p0.toString())
                if (p0!!.exists()) {
                    msgList.clear()

                    for (i in p0.children) {
                        val SignUpRecord = i.getValue(SignUpRecord::class.java)
                        msgList.add(SignUpRecord!!)
                    }
                    //to check can get data from fierbase
                    val adapter = MessageAdapter(applicationContext, R.layout.signrecord, msgList)
//                    trymain.adapter = adapter
                }
            }
        })


        signinBnt.setOnClickListener() {

            if (Uservalid1()) {

                Log.d("CLicked","clicked")
                MyAppApplication.globalUser = UnameTxt.text.toString()
                Page()
            }
            // Page()


        }//end acceptBtn

    }//over ride close

    fun Uservalid1(): Boolean {
        for (record in msgList) {
            if (record.name.equals(UnameTxt.text.toString()) ) {
                if (record.password.equals(PwordTxt.text.toString().md5())) {
                            //String().md5()
                            Log.d("try MD5",PwordTxt.text.toString().md5())
                            return true
                }
                else {
                    ResetPass()
                    return false
                }
            }
        }
        ResetName()
        return false
    }//end function


    fun Password1(): Boolean {
        for (record in msgList) {
            if (record.password.equals( PwordTxt.text.toString()) ) {
                return true
            }
        }
        return false
    }

    fun Page() {

            val intent1 = Intent(this, Home::class.java);
            startActivity(intent1);
         //   this.finish();

    }

    fun ResetPass() {
        PwordTxt.error = "รหัสผู้ใช้ไม่ถูกต้อง"
        PwordTxt.setText("");
    }
    fun ResetName() {
        UnameTxt.error = "ชื่อผู้ใช้ไม่ถูกต้อง"
        UnameTxt.setText("");
    }

    fun String.md5(): String{
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32,'0')
    }
} // Biggest close
