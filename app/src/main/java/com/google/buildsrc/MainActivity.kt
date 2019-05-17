package com.google.buildsrc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.library1.Library1Class
import com.example.library2.Library2Class

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Library1Class()
        Library2Class()
    }
}
