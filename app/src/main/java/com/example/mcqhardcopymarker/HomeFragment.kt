package com.example.mcqhardcopymarker

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView

class HomeFragment : Fragment() {

    private lateinit var scanCard: CardView
    private lateinit var resultsCard: CardView
    private lateinit var cardimg: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Locate views
        cardimg = view.findViewById(R.id.cardimg)
        scanCard = view.findViewById(R.id.scanCard)
        resultsCard = view.findViewById(R.id.resultsCard)

        // Load and downscale large image
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeResource(resources, R.drawable.man, options)

        // Calculate inSampleSize for downscaling
        options.inSampleSize = calculateInSampleSize(options, 80, 150)
        options.inJustDecodeBounds = false

        // Decode downscaled bitmap and set to ImageView
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.man, options)
        cardimg.setImageBitmap(bitmap)

        // Card click listeners
        scanCard.setOnClickListener {
            val fragment = ScanAnswerSheet()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        resultsCard.setOnClickListener {
            val results = Intent(requireContext(), Results::class.java)
            startActivity(results)
        }

        return view
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }
}
