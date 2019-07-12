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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_try)
//        initSaladMenu()

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
                }
            }
        })
    }





}
