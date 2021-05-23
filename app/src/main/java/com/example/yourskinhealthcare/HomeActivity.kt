package com.example.yourskinhealthcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btn = findViewById<ImageView>(R.id.button1)
        btn.setOnClickListener {
            val intent = Intent(this, profile::class.java)
            startActivity(intent);
        }
    }
}