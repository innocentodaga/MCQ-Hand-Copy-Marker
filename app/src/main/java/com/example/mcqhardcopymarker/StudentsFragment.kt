package com.example.mcqhardcopymarker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcqhardcopymarker.data.Student


class StudentsFragment : Fragment() {

    private lateinit var adapter: StudentAdapter
    private lateinit var searchInput: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var noResultsText: TextView

    private val fullStudentList = listOf(
        Student("John Doe", "S001", "10A", "Science"),
        Student("Jane Smith", "S002", "10B", "Arts"),
        Student("Michael Lee", "S003", "11A", "Commerce")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_students, container, false)

        searchInput = view.findViewById(R.id.searchInput)
        recyclerView = view.findViewById(R.id.studentRecyclerView)
        noResultsText = view.findViewById(R.id.noResultsText)

        adapter = StudentAdapter(fullStudentList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().lowercase().trim()
                val filtered = fullStudentList.filter {
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

        return view
    }
}
