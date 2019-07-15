package com.example.seniorproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*
import android.support.annotation.NonNull
import android.util.Log
import android.widget.Button
import android.widget.MediaController
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FileDownloadTask
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_knowledge.*
import java.io.File


class Knowledge : AppCompatActivity() {

    lateinit var depression: StorageReference
    lateinit var localFile: File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge)


        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        depression = storageRef.child("depression/example.mp4")

         localFile = File.createTempFile("depression", "mp4")
        depression.getFile(localFile).addOnSuccessListener {
            // Local temp file has been created
        }.addOnFailureListener {
            // Handle any errors
        }

        button1.setOnClickListener {
            configureVideoView()
        }



    }//onCreate


    private fun configureVideoView() {

//        if(localFile.exists()){
//            Log.d("shit","woohoo")
//        }
//        else{
//            Log.d("shit","goddamnit")
//        }

        val videoUri = Uri.fromFile(localFile)


        videoView.setVideoURI(videoUri)

        var mediaController  = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            Log.i("lol", "Duration = " + videoView.duration)
        }
        videoView.start()
    }
        //https://stackoverflow.com/questions/51168486/getting-list-of-images-from-a-folder-in-android-kotlin


}


