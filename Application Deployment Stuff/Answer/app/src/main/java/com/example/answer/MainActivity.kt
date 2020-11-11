package com.example.answer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reg_t.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterTeacher::class.java)
            startActivity(intent)
        }
        reg_st.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterStudent::class.java)
            startActivity(intent)
        }
    }




}