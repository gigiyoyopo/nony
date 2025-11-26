package com.example.appnony

import android.app.Application
import android.net.Uri

class App : Application() {


    var selectedImageUri: Uri? = null

    override fun onCreate() {
        super.onCreate()
    }

}