package com.example.mcqhardcopymarker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class PerformanceFragment : Fragment() {

    private lateinit var barChart: BarChart
    private lateinit var performanceText: TextView
    private lateinit var lineChart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_performance, container, false)

        barChart = view.findViewById(R.id.barChart)
        performanceText = view.findViewById(R.id.performanceText)


        setupChart()

        return view
    }

    private fun setupChart() {
        val scores = listOf(25f, 42f, 38f, 50f, 46f, 33f, 40f) // Dummy data
        val entries = scores.mapIndexed { index, score -> BarEntry(index.toFloat(), score) }

        val dataSet = BarDataSet(entries, "Student Scores").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            valueTextSize = 12f
        }

        barChart.apply {
            data = BarData(dataSet)
            description.isEnabled = false
            animateY(1000)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            axisRight.isEnabled = false
            legend.isEnabled = true
            invalidate()
        }


        val average = scores.average()
        performanceText.text = if (average >= 40) {
            "Good performance overall. Keep it up!"
        } else {
            "Performance is below average. Needs improvement."
        }
    }
}
