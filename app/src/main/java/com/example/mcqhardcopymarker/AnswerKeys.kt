package com.example.mcqhardcopymarker

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcqhardcopymarker.data.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

class AnswerKeys : Fragment() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var addButton: AppCompatButton
    private lateinit var answerKeyViewModel: AnswerKeyViewModel
    private lateinit var answerKeySetViewModel: AnswerKeySetViewModel
    private lateinit var answerKeySetAdapter: AnswerKeySetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_answer_keys, container, false)

        val userDB = UserDB.getDB(requireContext())

        val answerKeyRepository = AnswerKeyRepo(userDB.answerKeyDao(), userDB.answerKeySetDao())
        val answerKeyFactory = AnswerKeyViewModelFactory(answerKeyRepository)
        answerKeyViewModel = ViewModelProvider(this, answerKeyFactory)[AnswerKeyViewModel::class.java]

        val answerKeySetRepository = AnswerKeySetRepository(userDB.answerKeySetDao())
        val answerKeySetFactory = AnswerKeySetViewModel.AnswerKeySetViewModelFactory(answerKeySetRepository)
        answerKeySetViewModel = ViewModelProvider(this, answerKeySetFactory)[AnswerKeySetViewModel::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.answerKeySetsRecyclerView)
        answerKeySetAdapter = AnswerKeySetAdapter(emptyList()) { answerKeySet ->
            Snackbar.make(requireView(), "Clicked on: ${answerKeySet.title}", Snackbar.LENGTH_SHORT).show()
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = answerKeySetAdapter

        answerKeySetViewModel.allAnswerKeySets.observe(viewLifecycleOwner) { answerKeySets ->
            answerKeySetAdapter = AnswerKeySetAdapter(answerKeySets) { answerKeySet ->
                Snackbar.make(requireView(), "Clicked on: ${answerKeySet.title}", Snackbar.LENGTH_SHORT).show()
            }
            recyclerView.adapter = answerKeySetAdapter
        }

        toolbar = view.findViewById(R.id.toolbar)
        addButton = view.findViewById(R.id.addKeysButton)

        toolbar.setOnClickListener {
            val intent = Intent(requireContext(), Home::class.java)
            startActivity(intent)
        }

        addButton.setOnClickListener {
            showAddKeyDialog()
        }

        return view
    }

    private fun showAddKeyDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_answer_key, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        val questionsContainer = dialogView.findViewById<LinearLayout>(R.id.questionsContainer)
        val addQuestionButton = dialogView.findViewById<Button>(R.id.buttonAddQuestion)
        val saveButton = dialogView.findViewById<Button>(R.id.buttonSaveQuestions)
        val inputTitle = dialogView.findViewById<EditText>(R.id.inputTitle)
        val inputSubject = dialogView.findViewById<EditText>(R.id.inputSubject)

        var questionCount = 1

        addQuestionButton.setOnClickListener {
            val questionLayout = layoutInflater.inflate(R.layout.item_question_block, null)
            val questionLabel = questionLayout.findViewById<TextView>(R.id.questionNumber)
            questionLabel.text = "Question $questionCount"

            questionsContainer.addView(questionLayout)
            questionCount++

            val parent = addQuestionButton.parent as? ViewGroup
            parent?.removeView(addQuestionButton)
            questionsContainer.addView(addQuestionButton)
        }

        saveButton.setOnClickListener {
            val title = inputTitle.text.toString().trim()
            val subject = inputSubject.text.toString().trim()

            if (title.isEmpty() || subject.isEmpty()) {
                Snackbar.make(requireView(), "Please enter a title and subject.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val questionsList = mutableListOf<AnswerKey>()

            for (i in 0 until questionsContainer.childCount) {
                val questionView = questionsContainer.getChildAt(i)
                val answerInput = questionView.findViewById<EditText?>(R.id.inputAnswer)

                if (answerInput != null) {
                    val answerText = answerInput.text.toString().trim()
                    if (answerText.isNotEmpty()) {
                        val answerKey = AnswerKey(
                            id = 0,
                            answerKeySetId = 1,  // Will be updated later
                            question = "Question ${i + 1}",
                            correctAnswer = answerText,
                            points = 1
                        )
                        questionsList.add(answerKey)
                    }
                }
            }

            if (questionsList.isNotEmpty()) {
                val answerKeySet = AnswerKeySet(title = title, subject = subject)
                answerKeySetViewModel.insert(answerKeySet) { answerKeySetId ->
                    questionsList.forEach { it.answerKeySetId = answerKeySetId.toInt() }
                    answerKeyViewModel.insertAnswerKeys(questionsList)
                }

                Snackbar.make(requireView(), "Questions saved successfully!", Snackbar.LENGTH_SHORT).show()
                alertDialog.dismiss()
            } else {
                Snackbar.make(requireView(), "Please enter at least one answer.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
