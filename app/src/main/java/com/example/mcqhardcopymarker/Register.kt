package com.example.mcqhardcopymarker

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mcqhardcopymarker.data.User
import com.example.mcqhardcopymarker.data.UserViewModel

class Register : AppCompatActivity() {

    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

    private lateinit var firstNameField: EditText
    private lateinit var lastNameField: EditText
    private lateinit var emailField: EditText
    private lateinit var institutionField: EditText
    private lateinit var passwordField: EditText
    private lateinit var confirmPasswordField: EditText

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Set full-screen layout
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContentView(R.layout.activity_register)

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI components
        registerButton = findViewById(R.id.register_btn)
        loginButton = findViewById(R.id.login_btn)

        firstNameField = findViewById(R.id.first_name)
        lastNameField = findViewById(R.id.last_name)
        emailField = findViewById(R.id.email)
        institutionField = findViewById(R.id.institution)
        passwordField = findViewById(R.id.register_password)
        confirmPasswordField = findViewById(R.id.confirm_password)

        // Initialize ViewModel
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Handle register button click
        registerButton.setOnClickListener {
            insertDataToDatabase()
        }

        // Handle login button click
        loginButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    private fun insertDataToDatabase() {
        val firstName = firstNameField.text.toString()
        val lastName = lastNameField.text.toString()
        val email = emailField.text.toString()
        val institution = institutionField.text.toString()
        val password = passwordField.text.toString()
        val confirmPassword = confirmPasswordField.text.toString()

        if (!inputCheck(firstName, lastName, email, password, confirmPassword, institution)) {
            if (TextUtils.isEmpty(firstName)) firstNameField.error = "First name is required"
            if (TextUtils.isEmpty(lastName)) lastNameField.error = "Last name is required"
            if (TextUtils.isEmpty(email)) emailField.error = "Email is required"
            if (TextUtils.isEmpty(institution)) institutionField.error = "Institution is required"
            if (TextUtils.isEmpty(password)) passwordField.error = "Password is required"
            if (TextUtils.isEmpty(confirmPassword)) confirmPasswordField.error = "Confirm password is required"
            return
        }

        if (!isValidEmail(email)) {
            emailField.error = "Invalid email format"
            return
        }

        if (!isValidPassword(password)) {
            passwordField.error = "Password must be at least 6 characters and include uppercase, lowercase, digit, and special character"
            return
        }

        if (password != confirmPassword) {
            confirmPasswordField.error = "Passwords do not match"
            return
        }

        userViewModel.readEmail(email).observe(this) { existingUsers ->
            if (existingUsers.isNotEmpty()) {
                Toast.makeText(this, "Email already exists in the database", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(0, firstName, lastName, password, email, institution)
                userViewModel.addUser(user)

                Toast.makeText(this, "Successfully registered", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Login::class.java))
                finish()
            }
        }
    }

    private fun inputCheck(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String,
        institution: String
    ): Boolean {
        return !(TextUtils.isEmpty(firstName) ||
                TextUtils.isEmpty(lastName) ||
                TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(confirmPassword) ||
                TextUtils.isEmpty(institution))
    }

    // Validating the email
    private fun isValidEmail(email: String): Boolean{
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    // Validating the password
    private fun isValidPassword(password: String): Boolean{
        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{6,}$"
        return password.matches(passwordPattern.toRegex())
    }
}
