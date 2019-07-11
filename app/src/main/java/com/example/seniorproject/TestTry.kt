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
    lateinit var testList: MutableList<TestRecord>
    private val Trynode: MutableList<TestRecord> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_try)
//        initSaladMenu()
    }//overide
    private fun initSaladMenu(firebaseData: DatabaseReference) {
        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Trynode.clear()
                dataSnapshot.children.mapNotNullTo(Trynode) { it.getValue<TestRecord>(TestRecord::class.java) }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
            firebaseData.child("Datas").addListenerForSingleValueEvent(menuListener)
    }





}
