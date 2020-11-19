package com.example.answer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_student.*

class StudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        sst.setOnClickListener {
            val intent = Intent(this@StudentActivity, SstActivity::class.java)
            startActivity(intent)
        }
        math.setOnClickListener {
            val intent = Intent(this@StudentActivity, MathActivity::class.java)
            startActivity(intent)
        }
        sci.setOnClickListener {
            val intent = Intent(this@StudentActivity, ScienceActivity::class.java)
            startActivity(intent)
        }
        eng.setOnClickListener {
            val intent = Intent(this@StudentActivity, EnglishActivity::class.java)
            startActivity(intent)
        }
        pe.setOnClickListener {
            val intent = Intent(this@StudentActivity, PEActivity::class.java)
            startActivity(intent)
        }
        comp.setOnClickListener {
            val intent = Intent(this@StudentActivity, CompActivity::class.java)
            startActivity(intent)
        }
    }
}