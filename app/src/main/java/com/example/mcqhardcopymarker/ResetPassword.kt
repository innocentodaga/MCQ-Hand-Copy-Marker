package com.example.mcqhardcopymarker

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResetPassword : AppCompatActivity() {

    private lateinit var back_to_login: Button
    private lateinit var reset_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Set full-screen mode with no limits
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContentView(R.layout.activity_reset_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Locating the IDs in the layouts
        back_to_login = findViewById(R.id.login_btn)
        reset_btn = findViewById(R.id.reset_btn)

        // Navigating to different screens
        reset_btn.setOnClickListener {
            var reset = Intent(this, Login::class.java)
            startActivity(reset)
        }

        back_to_login.setOnClickListener {
            var back = Intent(this, Login::class.java)
            startActivity(back)
        }
    }
}