package com.example.seniorproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.google.firebase.database.*

class Info : AppCompatActivity() {


    lateinit var dataReference: DatabaseReference
    var disorderNameList: MutableList<String> = mutableListOf()
    var scores: MutableMap<String, Int> = mutableMapOf()
    var rangeLists: MutableMap<String, MutableList<Range>> = mutableMapOf()
    var resultLists: MutableMap<String, MutableList<String>> = mutableMapOf()
    private lateinit var listView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)


//        populateRangeList { populateResultList { populateOutput() } }
                populateRangeList { populateResultList{} }


    }




    private fun populateResultList(callback:()->Unit){

        var count : Int = disorderNameList.size;

        Log.d("Count @ Populate Result",count.toString())
        for (disorder in disorderNameList) {

            var tempResultList: MutableList<String> = mutableListOf()

            var resultReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Data4/" + disorder + "/Result")
            resultReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {

                    for (item in p0.children) {
                        var c = item.getValue(String::class.java)
                        tempResultList.add(c!!)
                        Log.d("resultlistTem",tempResultList.toString())

                    }
                    resultLists[disorder] = tempResultList
                    Log.d("resultlist",tempResultList.toString())

                    count--

                    if(count==0){

                        callback()
                    }
                }
            })
        }
    }

    private fun populateRangeList( callback: ()->Unit ) {

        var count : Int = disorderNameList.size

        for (disorder in disorderNameList) {

            var tempRangList: MutableList<Range> = mutableListOf()
            var rangeReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Data4/" + disorder + "/Score")
            rangeReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {

                    for (item in p0.children) {
                        var c = item.getValue(Range::class.java)

                        tempRangList.add(c!!)
                        Log.d("rangelistTem",tempRangList.toString())
                    }

                    rangeLists[disorder] = tempRangList

                    Log.d("rangelist",rangeLists.toString())


                    count--

                    if(count ==0 ){


                        callback()
                    }
                }
            })
        }
    }




}
