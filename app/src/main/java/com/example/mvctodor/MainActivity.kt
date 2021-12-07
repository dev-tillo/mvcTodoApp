package com.example.mvctodor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mvctodor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binging: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binging = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binging.root)
    }
}