package com.example.seniorproject

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.MediaController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_knowledge2.*
import java.io.File

class knowledge2 : AppCompatActivity() {

    lateinit var localFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        var i : Int = 0


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge2)

        val dataReference = FirebaseDatabase.getInstance().getReference("Knowledge/Filenames")
        var filenames :MutableList<String> = mutableListOf()
        dataReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (item in p0.children) {
                    val c = item.getValue(String::class.java)
                    Log.d("fuck ", c)
                    filenames.add(c!!)
                }//loop for


                val ref = FirebaseStorage.getInstance().reference.child(filenames[i])
                localFile = File.createTempFile(filenames[i].split(".")[0], "m4v")
                ref.getFile(localFile).addOnSuccessListener {
                    // Local temp file has been created
                }.addOnFailureListener {
                    // Handle any errors
                }
                uppertext.setText(filenames[i].split(".")[0])
            }
        })






        next.setOnClickListener{
            i++
            if (i == filenames.size){
                i = filenames.size-1
//                video.stopPlayback()
//                video.setVideoURI(null)
//                video.start()
//                video.stopPlayback()
                return@setOnClickListener
            }
            video.stopPlayback()
            video.setVisibility(GONE);
            video.setVisibility(VISIBLE);
        //    video.setVideoURI(null)
           // video.start()
           // video.stopPlayback()
           // configureVideoView2()

            uppertext.setText(filenames[i].split(".")[0])
            val ref = FirebaseStorage.getInstance().reference.child(filenames[i])
            localFile = File.createTempFile(filenames[i].split(".")[0], "m4v")
            ref.getFile(localFile).addOnSuccessListener {
                // Local temp file has been created
            }.addOnFailureListener {
                // Handle any errors
            }
        }



        prev.setOnClickListener{
            i--
            if (i < 0){
                i = 0
//                video.stopPlayback()
//                video.setVideoURI(null)
//                video.start()
//                video.stopPlayback()
                return@setOnClickListener
            }
            video.stopPlayback()
            video.setVisibility(GONE);
            video.setVisibility(VISIBLE);
          //  video.setVideoURI(null)
           // video.start()
            //video.stopPlayback()

           // configureVideoView2()

            uppertext.setText(filenames[i].split(".")[0])
            val ref = FirebaseStorage.getInstance().reference.child(filenames[i])
            localFile = File.createTempFile(filenames[i].split(".")[0], "m4v")
            ref.getFile(localFile).addOnSuccessListener {
                // Local temp file has been created
            }.addOnFailureListener {
                // Handle any errors
            }
        }

        play.setOnClickListener {
            configureVideoView()
        }
    }


    private fun configureVideoView() {

//        if(localFile.exists()){
//            Log.d("shit","woohoo")
//        }
//        else{
//            Log.d("shit","goddamnit")
//        }

        val videoUri = Uri.fromFile(localFile)


        video.setVideoURI(videoUri)


        var mediaController  = MediaController(this)
        mediaController.setAnchorView(video)
        video.setMediaController(mediaController)

        video.setOnPreparedListener { mp ->
            mp.isLooping = true
            Log.i("lol", "Duration = " + video.duration)
        }
        video.start()
    }

    private fun configureVideoView2() {

//        if(localFile.exists()){
//            Log.d("shit","woohoo")
//        }
//        else{
//            Log.d("shit","goddamnit")
//        }



        video.setVideoURI(null)

        var mediaController  = MediaController(this)
        mediaController.setAnchorView(video)
        video.setMediaController(mediaController)

        video.setOnPreparedListener { mp ->
            mp.isLooping = true
            Log.i("lol", "Duration = " + video.duration)
        }
        video.start()
    }
}
