package com.example.answer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window

class SplashActivity : AppCompatActivity() {
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler = Handler()
        handler.postDelayed(
            {

                // Delay and Start Activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.from_right,R.anim.from_left)
            } , 3000)
    }

}