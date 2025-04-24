package com.example.mcqhardcopymarker

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.appbar.MaterialToolbar

class AnswerKeys : Fragment() {

    private  lateinit var toolbar: MaterialToolbar
    private lateinit var addButton: Button

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_answer_keys, container, false)

         // Locating the ids in the layout
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
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_answer_key,null)
        val dialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogView)
            .setCancelable(true)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        val questionsContainer = dialogView.findViewById<LinearLayout>(R.id.questionsContainer)
        val addQuestionButton = dialogView.findViewById<Button>(R.id.buttonAddQuestion)

        var questionCount = 1

        addQuestionButton.setOnClickListener {
            val questionLayout = layoutInflater.inflate(R.layout.item_question_block, null)
            val questionLabel = questionLayout.findViewById<TextView>(R.id.questionNumber)
            questionLabel.text = "Question $questionCount"

            questionsContainer.addView(questionLayout)
            questionCount++

            // Move the button below after first click
            (addQuestionButton.parent as? ViewGroup)?.removeView(addQuestionButton)
            questionsContainer.addView(addQuestionButton)
        }
    }
}