package com.example.seniorproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_result.*

class History : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        Log.d("hello","here")

        var usrList: MutableList<String> = mutableListOf()
        var usrReference = FirebaseDatabase.getInstance().getReference("History")
        usrReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    for (i in p0.children) {
                        var usr = i.getKey()
                        usrList.add(usr!!)
                    }



                    var outputList: MutableList<String> = mutableListOf()
                    for (user in usrList){
                        Log.d("user",user)
                        if (MyAppApplication.globalUser.equals(user)){
                            var usrReference = FirebaseDatabase.getInstance().getReference("History/"+user)
                            usrReference.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onCancelled(p0: DatabaseError) {
                                }

                                override fun onDataChange(p0: DataSnapshot) {
                                    if (p0!!.exists()) {
                                        for (i in p0.children) {
                                            var date = i.getKey()
                                            outputList.add(date!!)
                                            for (j in i.children){
                                                var record = j.getValue(String::class.java)
                                                outputList.add(record!!)
                                            }
                                        }

                                        val adapter =
                                            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, outputList)
                                        history.adapter = adapter

                                        for (output in outputList){
                                            Log.d("wtf",output)
                                        }
                                    }
                                }
                            })
                        }
                    }
                }
            }
        })

    }
}
