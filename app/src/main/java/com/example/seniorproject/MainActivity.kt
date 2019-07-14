package com.example.seniorproject


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*


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
            }

            override fun onDataChange(p0: DataSnapshot) {
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
//                if (Uservalid1()==true) {
//                    Page()
//                }
               Page()

            }//end acceptBtn

    }//over ride close

    fun Uservalid1(): Boolean {
        for (record in msgList) {
            if (record.name.equals(UnameTxt.text.toString()) ) {
                if (record.password.equals(PwordTxt.text.toString())) {
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
        signinBnt.setOnClickListener {
            val intent1 = Intent(this, Home::class.java);
            startActivity(intent1);
        }
    }

    fun ResetPass() {
        PwordTxt.error = "รหัสผู้ใช้ไม่ถูกต้อง"
        PwordTxt.setText("");
    }
    fun ResetName() {
        UnameTxt.error = "ชื่อผู้ใช้ไม่ถูกต้อง"
        UnameTxt.setText("");
    }
} // Biggest close
