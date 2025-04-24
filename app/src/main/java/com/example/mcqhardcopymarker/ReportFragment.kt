package com.example.mcqhardcopymarker

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class ReportFragment : Fragment() {

    private lateinit var lineChart: LineChart

    // Sample weekly data: number of sheets scanned per day (Mon to Sun)
    private val sheetData = listOf(4, 7, 2, 9, 6, 3, 5)
    private val weekDays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_report, container, false)

        lineChart = view.findViewById(R.id.lineChart)
        setupLineChart()

        return view
    }

    private fun setupLineChart() {
        // Convert data to chart entries
        val entries = sheetData.mapIndexed { index, value ->
            Entry(index.toFloat(), value.toFloat())
        }

        val dataSet = LineDataSet(entries, "Sheets Scanned").apply {
            color = Color.parseColor("#3CDAAA")
            setCircleColor(Color.parseColor("#3CDAAA"))
            valueTextColor = Color.BLACK
            valueTextSize = 12f
            lineWidth = 2f
            circleRadius = 5f
        }

        lineChart.apply {
            data = LineData(dataSet)
            description.isEnabled = false
            setTouchEnabled(true)
            setPinchZoom(true)

            // Left Y-axis styling
            axisLeft.textColor = Color.BLACK
            axisRight.isEnabled = false

            // X-axis settings
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                textColor = Color.BLACK
                valueFormatter = IndexAxisValueFormatter(weekDays)
            }

            animateY(1000)
            invalidate()
        }
    }
}
