package com.example.mcqhardcopymarker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mcqhardcopymarker.data.UserViewModel

class Login : AppCompatActivity() {

    private lateinit var login: Button
    private lateinit var reset: TextView
    private lateinit var register:Button

    private lateinit var Loginemail: EditText
    private lateinit var Loginpassword: EditText

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Set full-screen mode with no limits
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Initialize the ViewModel in your Login activity
        userViewModel = UserViewModel(application)



        // Allocating the different IDs in the layouts
        login = findViewById(R.id.login)
        reset = findViewById(R.id.reset_pass)
        register = findViewById(R.id.register_btn)

        Loginemail = findViewById(R.id.email)
        Loginpassword = findViewById(R.id.login_password)


        // Creating the movements to different screens
        login.setOnClickListener {
            confirmDB()
        }

        reset.setOnClickListener{
            var resetPassord = Intent(this, ResetPassword::class.java)
            startActivity(resetPassord)
        }

        register.setOnClickListener {
            var regist = Intent(this, Register::class.java)
            startActivity(regist)
        }
    }

    private fun confirmDB() {
        val email = Loginemail.text.toString()
        val password = Loginpassword.text.toString()



        if (!checkInput(email, password)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        userViewModel.readEmailPassword(password, email).observe(this) { userList ->
            if (userList.isNotEmpty()) {
                val user = userList[0]
                val fullName = "${user.firstName} ${user.lastName}"

                // ✅ Save email to SharedPreferences
                val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                sharedPref.edit().putString("email", user.email).apply()

                val intent = Intent(this, Home::class.java)
                intent.putExtra("UserName", fullName)
                startActivity(intent)
                finish()

                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkInput(email: String, password: String): Boolean{
        return !(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
    }

}