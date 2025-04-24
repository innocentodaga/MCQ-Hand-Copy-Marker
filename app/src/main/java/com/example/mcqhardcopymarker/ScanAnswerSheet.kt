package com.example.mcqhardcopymarker

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton

class ScanAnswerSheet : Fragment() {

    private lateinit var scanBtn: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_scan_answer_sheet, container, false)

        scanBtn = view.findViewById(R.id.scanBtn)

        scanBtn.setOnClickListener {
            val scanIntent = Intent(requireContext(),  CaptureScreen::class.java)
            startActivity(scanIntent)

        }


        return view
    }


}