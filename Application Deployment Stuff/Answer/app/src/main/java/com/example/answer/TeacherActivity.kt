package com.example.answer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_teacher.*

class TeacherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)

        tvForgot.setOnClickListener {
            val intent = Intent(this@TeacherActivity, LoginTeacher::class.java)
            startActivity(intent)
        }
        submit_que.setOnClickListener {
            val intent = Intent(this@TeacherActivity, TeacherActivityAnswer::class.java)
            startActivity(intent)
        }

    }
}