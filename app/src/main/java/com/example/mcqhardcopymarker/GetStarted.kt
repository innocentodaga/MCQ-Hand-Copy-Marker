package com.example.mcqhardcopymarker

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GetStarted : AppCompatActivity() {

    private lateinit var signinbtn: Button
    private lateinit var signupbtn: Button
    private lateinit var backgroundView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        // Set full-screen mode with no limits
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Views
        signinbtn = findViewById(R.id.signinbtn)
        signupbtn = findViewById(R.id.signupbtn)
        backgroundView = findViewById(R.id.main) // Root Layout

        // Optimize & Set Background Image
        setOptimizedBackground(R.drawable.bg)

        // Button Click Listeners
        signinbtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))

        }

        signupbtn.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }

    private fun setOptimizedBackground(imageRes: Int) {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true // Get dimensions without loading into memory
        }
        BitmapFactory.decodeResource(resources, imageRes, options)

        val reqWidth = resources.displayMetrics.widthPixels
        val reqHeight = resources.displayMetrics.heightPixels
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false

        val scaledBitmap: Bitmap = BitmapFactory.decodeResource(resources, imageRes, options)
        backgroundView.background = BitmapDrawable(resources, scaledBitmap)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

}
