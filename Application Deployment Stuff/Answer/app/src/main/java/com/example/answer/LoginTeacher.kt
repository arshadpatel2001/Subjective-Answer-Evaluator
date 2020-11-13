package com.example.answer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login_student.*
import kotlinx.android.synthetic.main.activity_login_teacher.*
import kotlinx.android.synthetic.main.activity_login_teacher.btnRegLogin
import kotlinx.android.synthetic.main.activity_main.*

class LoginTeacher : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_teacher)

        btnRegLogin.setOnClickListener {
            startActivity(Intent(this, RegisterTeacher::class.java ))
            overridePendingTransition(R.anim.from_right,R.anim.from_left)
        }
        login_t.setOnClickListener {
            val intent = Intent(this@LoginTeacher, TeacherActivity::class.java)
            startActivity(intent)
        }


    }
}