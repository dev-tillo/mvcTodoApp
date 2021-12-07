package com.example.mvctodor

import android.media.Ringtone
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvctodor.databinding.ActivitySeconBinding
import com.example.mvctodor.reciver.MyReciver

class SeconActivity : AppCompatActivity() {

    lateinit var binding: ActivitySeconBinding
    private var ringtone: Ringtone? = null
    private lateinit var myReceiver: MyReciver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeconBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myReceiver = MyReciver()

        var uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL)
        ringtone = RingtoneManager.getRingtone(this, uri)
        if (ringtone == null) {
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL)
            ringtone = RingtoneManager.getRingtone(this, uri)
        }
        if (ringtone != null) {
            ringtone!!.play()
        }
    }

    override fun onDestroy() {
        if (ringtone != null && ringtone!!.isPlaying) {
            ringtone!!.stop()
        }
        super.onDestroy()
    }
}