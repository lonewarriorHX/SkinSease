package com.example.yourskinhealthcare.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yourskinhealthcare.databinding.ActivityHomeBinding
import com.example.yourskinhealthcare.main.CameraActivity
import com.example.yourskinhealthcare.main.NewActivity
import com.example.yourskinhealthcare.profile

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        activityHomeBinding.button1.setOnClickListener {
            val intent = Intent(this, profile::class.java)
            startActivity(intent)
        }
        activityHomeBinding.cobaTes.setOnClickListener {
            val intent = Intent(this, NewActivity::class.java)
            startActivity(intent)
        }
        activityHomeBinding.btnCamDetect.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java )
            startActivity(intent)
        }
    }
}