package com.example.seniorproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.CountDownLatch
import com.google.firebase.database.DataSnapshot
import com.google.firebase.FirebaseError
import com.google.firebase.database.ValueEventListener
import java.math.BigInteger
import java.security.MessageDigest

//import sun.font.GlyphLayout.done


class signUp : AppCompatActivity() {

    lateinit var dataReference: DatabaseReference
    lateinit var msgList: MutableList<SignUpRecord>
    //var sp:TextView? = ui
    var gender =""





    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_sign_up)
        super.onCreate(savedInstanceState)


        msgList = mutableListOf()
        dataReference = FirebaseDatabase.getInstance().getReference("Profile")
        dataReference.addListenerForSingleValueEvent(object : ValueEventListener {
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
//                    trylist.adapter = adapter

                }
            }
        })



        acceptBtn.setOnClickListener() {
            if (Uservalid() == true && Password() == true) {
                Save()
                PageChange()
            } else if (Uservalid() == true && Password() == false) {
                ResetPass()
            } else if (Uservalid() == false && Password() == true) {
                ResetName()
            } else {
                ResetPass()
                ResetName()
            }

        }//end acceptBtn

        //-------------------------------Spinner------------------------------------------------//
        // Initializing a String Array
        val genderlist = arrayOf("ชาย", "หญิง", "อื่นๆ")
        // Initializing an ArrayAdapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderlist)
        // Set the drop down view resource
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        // Finally, data bind the spinner object with dapter
        spinner.adapter = adapter;
        // Set an on item selected listener for spinner object
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view
                //sp = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                gender = spinner.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        //----------------------------------------------------------------------------------//
    }//end OnCreate

    fun Password(): Boolean {
        var a = passtxt1.text.toString()
        var b = passTxt2.text.toString()
        if (a.equals(b)) {
            //String().md5()
            //Log.d("try MD5",md.toString())

            return true
        } else {
            passTxt2.error = "รหัสไม่ตรงกัน1"
            return false
        }
    }




     fun Uservalid(): Boolean {
         for ( record in msgList) {
             if (record.name.equals(nameTxt.text.toString())) {
                 return false
             }
         }
         return true
    }

    fun Save() {
        // Save a message to the database
        var database = FirebaseDatabase.getInstance()
        var myRef = database.getReference("Profile")
        var id = 0

        var tempRef = myRef.push()
        val users =
            SignUpRecord(id.toString(), nameTxt.text.toString(), AgeTxt.text.toString(), passtxt1.text.toString().md5()
                ,gender)
        tempRef.setValue(users)
        MyAppApplication.globalUser = nameTxt.text.toString()
        Toast.makeText(applicationContext,"บันทึก",Toast.LENGTH_SHORT).show()
    }

    private fun PageChange() {
        //page transition
        val intent1 = Intent(this, Home::class.java)
        startActivity(intent1)
    }

    fun ResetPass() {
        //passTxt2.error ="รหัสไม่ตรงกัน"
        //passtxt1.setText("");
        passTxt2.setText("");
    }

    fun ResetName() {
        nameTxt.error = "ชื่อนี้ได้ถูกใข้ไปแล้ว"
        nameTxt.setText("");
    }

    fun String.md5(): String{
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32,'0')
    }
}//End program
