package com.example.answer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login_student.*
import kotlinx.android.synthetic.main.activity_login_student.btnRegLogin
import kotlinx.android.synthetic.main.activity_login_teacher.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginStudent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_student)

        btnRegLogin.setOnClickListener {
            startActivity(Intent(this, RegisterStudent::class.java ))
            overridePendingTransition(R.anim.from_right,R.anim.from_left)
        }
        login.setOnClickListener {
            val intent = Intent(this@LoginStudent, StudentActivity::class.java)
            startActivity(intent)
        }

    }
}