package com.example.mcqhardcopymarker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mcqhardcopymarker.data.AnswerKeySet

class AnswerKeySetAdapter(
    private val answerKeySets: List<AnswerKeySet>,
    private val onItemClick: (AnswerKeySet) -> Unit
) : RecyclerView.Adapter<AnswerKeySetAdapter.AnswerKeySetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerKeySetViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_answer_key_set, parent, false)
        return AnswerKeySetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnswerKeySetViewHolder, position: Int) {
        val answerKeySet = answerKeySets[position]
        holder.title.text = answerKeySet.title
        holder.subject.text = answerKeySet.subject
        holder.itemView.setOnClickListener {
            onItemClick(answerKeySet)
        }
    }

    override fun getItemCount() = answerKeySets.size

    class AnswerKeySetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textTitle)
        val subject: TextView = itemView.findViewById(R.id.textSubject)
    }
}
