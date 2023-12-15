package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

        btnAdd.setOnClickListener(this)
        btnSub.setOnClickListener(this)
        btnMultiply.setOnClickListener(this)
        btnDivision.setOnClickListener(this)
        btnAC.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val textA = etA.text.toString()
        val textB = etB.text.toString()

        if (textA.isBlank() || textB.isBlank()) {
            // Display a message or handle the empty input case
            resultTv.text = "Please enter values in both fields."
            return
        }

        try {
            val a = textA.toDouble()
            val b = textB.toDouble()

            var result = 0.0

            when (v?.id) {
                R.id.btn_add -> {
                    result = a + b
                }

                R.id.btn_sub -> {
                    result = a - b
                }

                R.id.btn_multiplication -> {
                    result = a * b
                }

                R.id.btn_division -> {
                    if (b != 0.0) {
                        result = a / b
                    } else {
                        // Handle division by zero
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
            }

            resultTv.text = "Result is: $result"
        } catch (e: NumberFormatException) {
            resultTv.text = "Invalid input. Please enter valid numbers."
        }
    }
}