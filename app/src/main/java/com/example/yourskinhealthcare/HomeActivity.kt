package com.example.yourskinhealthcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.yourskinhealthcare.databinding.ActivityHomeBinding
import com.example.yourskinhealthcare.main.CameraActivity
import com.example.yourskinhealthcare.main.DetectActivity
import com.example.yourskinhealthcare.main.NewActivity
import kotlinx.android.synthetic.main.activity_home.*

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