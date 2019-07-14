package com.example.seniorproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_knowledge_main.*

class KnowledgeMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge_main)

        //page transition//
        DBtn.setOnClickListener {
            val intent1 = Intent(this,Knowledge::class.java);
            startActivity(intent1)

        }

        ABtn.setOnClickListener {
            val intent2 = Intent(this, Knowledge::class.java);
            startActivity(intent2)

        }
        SBtn.setOnClickListener {
            val intent3 = Intent(this, History::class.java);
            startActivity(intent3)

        }

    }

    
}

