package com.example.yourskinhealthcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.yourskinhealthcare.main.DetectActivity
import com.example.yourskinhealthcare.main.NewActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btn = findViewById<ImageView>(R.id.button1)
        val coba = findViewById<Button>(R.id.cobaTes)
        btn.setOnClickListener {
            val intent = Intent(this, profile::class.java)
            startActivity(intent);
        }
        coba.setOnClickListener {
            val intent = Intent(this, NewActivity::class.java)
            startActivity(intent);
        }
    }
}