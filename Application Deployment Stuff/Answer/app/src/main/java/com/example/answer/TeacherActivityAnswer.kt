package com.example.answer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_teacher_answer.*

class TeacherActivityAnswer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_answer)

        submit_ans.setOnClickListener {
            val intent = Intent(this@TeacherActivityAnswer, TeacherActivity::class.java)
            startActivity(intent)
        }
    }
}