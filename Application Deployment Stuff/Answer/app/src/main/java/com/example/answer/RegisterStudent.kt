package com.example.answer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register_student.*

class RegisterStudent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_student)

        btnLogRegister.setOnClickListener {
            onBackPressed()
        }
        register.setOnClickListener {
            val intent = Intent(this@RegisterStudent, LoginStudent::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@RegisterStudent, LoginStudent::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.from_right,R.anim.from_left)
    }

}