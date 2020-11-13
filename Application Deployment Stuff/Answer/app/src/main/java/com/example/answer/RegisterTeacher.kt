package com.example.answer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register_student.*
import kotlinx.android.synthetic.main.activity_register_student.btnLogRegister
import kotlinx.android.synthetic.main.activity_register_teacher.*

class RegisterTeacher : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_teacher)

        btnLogRegister.setOnClickListener {
            onBackPressed()
        }
        register_t.setOnClickListener {
            val intent = Intent(this@RegisterTeacher, LoginTeacher::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@RegisterTeacher, LoginTeacher::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.from_right,R.anim.from_left)
    }
}