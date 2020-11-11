package com.example.answer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login_student.*

class LoginStudent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_student)

        btnRegLogin.setOnClickListener {
            startActivity(Intent(this, StudentActivity::class.java ))
            overridePendingTransition(R.anim.from_right,R.anim.from_left)
        }

    }
}