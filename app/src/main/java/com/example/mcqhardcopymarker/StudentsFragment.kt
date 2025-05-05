package com.example.mcqhardcopymarker

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcqhardcopymarker.data.UserDB
import com.example.mcqhardcopymarker.data.Student
import kotlinx.coroutines.launch

class StudentsFragment : Fragment() {

    private lateinit var adapter: StudentAdapter
    private lateinit var searchInput: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var noResultsText: TextView
    private lateinit var addButton: Button

    private var studentList: MutableList<Student> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_students, container, false)

        searchInput = view.findViewById(R.id.searchInput)
        recyclerView = view.findViewById(R.id.studentRecyclerView)
        noResultsText = view.findViewById(R.id.noResultsText)
        addButton = view.findViewById(R.id.addStudentButton)

        adapter = StudentAdapter(studentList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        loadStudentsFromDb()

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().lowercase().trim()
                val filtered = studentList.filter {
                    it.name.lowercase().contains(query) ||
                            it.studentId.lowercase().contains(query) ||
                            it.studentClass.lowercase().contains(query) ||
                            it.section.lowercase().contains(query)
                }
                adapter.updateList(filtered)
                noResultsText.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        addButton.setOnClickListener {
            showAddStudentDialog()
        }

        return view
    }

    private fun showAddStudentDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_student, null)
        val builder = AlertDialog.Builder(requireContext()).setView(dialogView).setCancelable(false)
        val alertDialog = builder.create()
        alertDialog.show()

        val etName = dialogView.findViewById<EditText>(R.id.etStudentName)
        val etID = dialogView.findViewById<EditText>(R.id.etStudentID)
        val etClass = dialogView.findViewById<EditText>(R.id.etClass)
        val etSection = dialogView.findViewById<EditText>(R.id.etSection)
        val btnAdd = dialogView.findViewById<Button>(R.id.btnAdd)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        btnCancel.setOnClickListener { alertDialog.dismiss() }

        btnAdd.setOnClickListener {
            val name = etName.text.toString()
            val studentId = etID.text.toString()
            val studentClass = etClass.text.toString()
            val section = etSection.text.toString()

            if (name.isNotBlank() && studentId.isNotBlank() && studentClass.isNotBlank() && section.isNotBlank()) {
                val newStudent = Student(name, studentId, studentClass, section)
                lifecycleScope.launch {
                    UserDB.getDB(requireContext()).studentDao().insertStudent(newStudent)
                    studentList.add(newStudent)
                    adapter.updateList(studentList)
                    alertDialog.dismiss()
                }
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadStudentsFromDb() {
        UserDB.getDB(requireContext()).studentDao().getAllStudents().observe(viewLifecycleOwner) { students ->
            studentList.clear()
            studentList.addAll(students)
            adapter.updateList(studentList)
            noResultsText.visibility = if (studentList.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}
