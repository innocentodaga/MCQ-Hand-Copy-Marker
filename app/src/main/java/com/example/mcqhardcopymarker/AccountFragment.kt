package com.example.mcqhardcopymarker

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.mcqhardcopymarker.data.User
import com.example.mcqhardcopymarker.data.UserViewModel

class AccountFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var email: String

    private lateinit var firstNameText: TextView
    private lateinit var lastNameText: TextView
    private lateinit var emailText: TextView
    private lateinit var institutionText: TextView
    private lateinit var profileName: TextView

    private lateinit var editButton: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // Get user email from SharedPreferences
        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        email = sharedPref.getString("email", "") ?: ""

        // Initialize views
        firstNameText = view.findViewById(R.id.firstName)
        lastNameText = view.findViewById(R.id.lastName)
        emailText = view.findViewById(R.id.email)
        institutionText = view.findViewById(R.id.institute)
        profileName = view.findViewById(R.id.profileName)

        editButton = view.findViewById(R.id.editButton)

        loadUserData() // Load data for the user with this email

        editButton.setOnClickListener {
            showEditProfileDialog()
        }

        return view
    }

    private var currentUser: User? = null

    // Load user data from the ViewModel using the email
    private fun loadUserData() {
        userViewModel.getUserByEmail(email).observe(viewLifecycleOwner) { user ->
            if (user != null) {
                currentUser = user
                firstNameText.text = user.firstName
                lastNameText.text = user.lastName
                emailText.text = user.email
                institutionText.text = user.institution

            }
        }
    }

    private fun showEditProfileDialog() {
        val user = currentUser ?: return // Don't open dialog if no user

        // Update the email in SharedPreferences (if needed, when editing)
        val sharedPref = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPref.edit().putString("email", user.email).apply()

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_profile, null)
        val builder = AlertDialog.Builder(requireContext()).setView(dialogView)
        val alertDialog = builder.create()

        val firstNameEdit = dialogView.findViewById<EditText>(R.id.editFirstName)
        val lastNameEdit = dialogView.findViewById<EditText>(R.id.editLastName)
        val emailEdit = dialogView.findViewById<EditText>(R.id.editEmail)
        val institutionEdit = dialogView.findViewById<EditText>(R.id.editInstitution)
        val saveButton = dialogView.findViewById<AppCompatButton>(R.id.saveButton)
        val cancelButton = dialogView.findViewById<AppCompatButton>(R.id.cancelButton)

        // Pre-fill data into the EditText fields
        firstNameEdit.setText(user.firstName)
        lastNameEdit.setText(user.lastName)
        emailEdit.setText(user.email)
        institutionEdit.setText(user.institution)

        profileName.text = "${user.firstName} ${user.lastName}"



        saveButton.setOnClickListener {
            Log.d("DialogDebug", "Save button clicked")
            val updatedUser = user.copy(
                firstName = firstNameEdit.text.toString(),
                lastName = lastNameEdit.text.toString(),
                email = emailEdit.text.toString(),
                institution = institutionEdit.text.toString()
            )
            userViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Profile Updated", Toast.LENGTH_SHORT).show()
            alertDialog.dismiss()
        }

        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}
