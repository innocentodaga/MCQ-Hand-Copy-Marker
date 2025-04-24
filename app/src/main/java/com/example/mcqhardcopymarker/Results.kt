package com.example.mcqhardcopymarker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.progressindicator.CircularProgressIndicator

class Results : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var progressBAr: CircularProgressIndicator

    private lateinit var totalQuestions: TextView
    private lateinit var correctAnswers: TextView
    private lateinit var progressText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Set full-screen mode with no limits
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContentView(R.layout.activity_results)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        progressBAr = findViewById(R.id.circularProgress)
        toolbar = findViewById(R.id.toolbar)
        totalQuestions = findViewById(R.id.totalQuestions)
        correctAnswers = findViewById(R.id.correctAnswers)
        progressText = findViewById(R.id.progressText)

        toolbar.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }

        val totalQ = totalQuestions.text.toString().toIntOrNull() ?: 1
        val correctA = correctAnswers.text.toString().toIntOrNull() ?: 0

        val percentage = (correctA / totalQ)
        progressText.text = "$percentage%"

        progressBAr.max = totalQ
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBAr.setProgress(correctA, true)
        } else {
            progressBAr.progress = correctA
        }

    }
}