package com.example.seniorproject

import android.app.Application


 class MyAppApplication : Application() {

     companion object {
         var globalUser: String? = null
     }

     override fun onCreate() {
         super.onCreate()
     }
}