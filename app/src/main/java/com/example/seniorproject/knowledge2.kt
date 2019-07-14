package com.example.seniorproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import java.io.File

class knowledge2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge2)

//        var gpath: String = Environment.getExternalStorageDirectory().absolutePath
//        var spath = " depression"
//        var fullpath = File(gpath + File.separator + spath)
//        Log.w("fullpath", "" + fullpath)
//        imageReader(fullpath)
    }

//    fun imageReaderNew(root: File) {
//        val fileList: ArrayList<File> = ArrayList()
//        val listAllFiles = root.listFiles()
//
//        if (listAllFiles != null && listAllFiles.size > 0) {
//            for (currentFile in listAllFiles) {
//                if (currentFile.name.endsWith(".m4v")) {
//                    // File absolute path
//                    Log.e("downloadFilePath", currentFile.getAbsolutePath())
//                    // File Name
//                    Log.e("downloadFileName", currentFile.getName())
//                    fileList.add(currentFile.absoluteFile)
//                }
//            }
//            Log.w("fileList", "" + fileList.size)
//        }
//    }

//    fun imageReader(root : File):ArrayList<File>{
//        val a : ArrayList<File> ? = null
//        val files = root.listFiles()
//        for (i in 0..files.size-1){
//            if (files[i].name.endsWith(".m4v")){
//                a?.add(files[i])
//                Log.d("try", a.toString())
//
//            }
//        }
//
//        return a!!
//    }
}
