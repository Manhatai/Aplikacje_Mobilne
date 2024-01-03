package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() , View.OnClickListener {

    lateinit var btnAdd: Button
    lateinit var btnSub: Button
    lateinit var btnMultiply: Button
    lateinit var btnDivision: Button
    lateinit var btnAC: Button
    lateinit var etA: EditText
    lateinit var etB: EditText
    lateinit var resultTv: TextView
    lateinit var et_text: TextView


    override fun onDestroy() {
        saveLatestResult()
        super.onDestroy()
    }

    private fun saveLatestResult() {
        val latestResult = et_text.text.toString()

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("LATEST_RESULT_KEY", latestResult)
        editor.apply()
    }

    private fun loadLatestResult() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val latestResult = sharedPreferences.getString("LATEST_RESULT_KEY", null)

        if (latestResult != null) {
            et_text.text = latestResult
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAdd = findViewById(R.id.btn_add)
        btnSub = findViewById(R.id.btn_sub)
        btnMultiply = findViewById(R.id.btn_multiplication)
        btnDivision = findViewById(R.id.btn_division)
        etA = findViewById(R.id.et_a)
        etB = findViewById(R.id.et_b)
        resultTv = findViewById(R.id.result_tv)
        btnAC = findViewById(R.id.btn_AC)
        et_text = findViewById(R.id.et_text)

        btnAdd.setOnClickListener(this)
        btnSub.setOnClickListener(this)
        btnMultiply.setOnClickListener(this)
        btnDivision.setOnClickListener(this)
        btnAC.setOnClickListener(this)

        loadLatestResult()
        loadData()
    }


    private fun performOperation(
        a: Double,
        b: Double,
        operation: (Double, Double) -> Double
    ): Double {
        return operation(a, b)
    }

    override fun onClick(v: View?) {
        try {
            val textA = etA.text.toString()
            val textB = etB.text.toString()

            if (textA.isBlank() || textB.isBlank()) {
                resultTv.text = "Please enter values in both fields."
                return
            }

            val a = textA.toDouble()
            val b = textB.toDouble()

            val result = when (v?.id) {
                R.id.btn_add -> performOperation(a, b) { x, y -> x + y }
                R.id.btn_sub -> performOperation(a, b) { x, y -> x - y }
                R.id.btn_multiplication -> performOperation(a, b) { x, y -> x * y }
                R.id.btn_division -> {
                    if (b != 0.0) {
                        performOperation(a, b) { x, y -> x / y }
                    } else {
                        resultTv.text = "Cannot divide by zero!"
                        return
                    }
                }

                R.id.btn_AC -> {
                    etA.text.clear()
                    etB.text.clear()
                    resultTv.text = "Cleared!"
                    return
                }

                else -> {
                    resultTv.text = "Invalid operation!"
                    return
                }
            }

            resultTv.text = "Result is: $result"

            val currentLastResult = et_text.text.toString()
            val padding = "\n\n"
            val newLastResult = "Latest Result:$padding$result$padding$currentLastResult"

            val stringBuilder = StringBuilder(newLastResult)

            val linesList = stringBuilder.lines()
            if (linesList.size > 15) {
                val truncatedText = linesList.subList(0, 15).joinToString("\n")
                stringBuilder.clear()
                stringBuilder.append(truncatedText)
            }

            et_text.text = stringBuilder.toString()

            saveLatestResult()
            saveData()
            loadData()

        } catch (e: NumberFormatException) {
            resultTv.text = "Invalid input. Please enter valid numbers."
        }
    }

    private fun saveData() {
        val insertedTextA = etA.text.toString()
        val insertedTextB = etB.text.toString()

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_KEY_A", insertedTextA)
            putString("STRING_KEY_B", insertedTextB)
        }.apply()


    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedStringA = sharedPreferences.getString("STRING_KEY_A", null)
        val savedStringB = sharedPreferences.getString("STRING_KEY_B", null)

        etA.setText(savedStringA)
        etB.setText(savedStringB)
    }
}
