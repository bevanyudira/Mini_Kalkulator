package com.example.pertemuan_2_tugas

import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvFormula: TextView

    private var firstNumber = ""
    private var currentNumber = ""
    private var currentOperator = ""
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)
        tvFormula = findViewById(R.id.tvFormula)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val layoutMain = findViewById<GridLayout>(R.id.layoutMain)
        layoutMain.children.filterIsInstance<Button>().forEach { button: Button ->
            button.setOnClickListener {
                val buttonText = button.text.toString()
                when {
                    buttonText.matches(Regex("[0-9]")) -> {
                        if (currentOperator.isEmpty()) {
                            firstNumber += buttonText
                            tvResult.text = firstNumber
                        } else {
                            currentNumber += buttonText
                            tvResult.text = currentNumber
                        }
                    }
                    buttonText.matches(Regex("[+\\-*/]")) -> {
                        currentNumber = ""
                        if (tvResult.text.toString().isNotEmpty())
                        {
                           currentOperator = buttonText
                            tvResult.text = "0"
                        }
                    }

                    buttonText == "=" -> {
                        if (currentNumber.isNotEmpty() && currentOperator.isNotEmpty())
                        {
                           tvFormula.text = "$firstNumber$currentOperator$currentNumber"
                            result = evaluateExpression(firstNumber,currentNumber,currentOperator)
                            firstNumber = result
                            tvResult.text = result
                        }
                    }

                    buttonText == "." -> {
                        if (currentOperator.isEmpty()) {
                            if (!firstNumber.contains(".")) {
                                if (firstNumber.isEmpty()) {
                                    firstNumber = "0."
                                } else {
                                    firstNumber += "."
                                }
                                tvResult.text = firstNumber
                            }
                        } else {
                            if (!currentNumber.contains(".")) {
                                if (currentNumber.isEmpty()) {
                                    currentNumber = "0."
                                } else {
                                    currentNumber += "."
                                }
                                tvResult.text = currentNumber
                            }
                        }
                    }

                    buttonText == "c" -> {
                        currentNumber = ""
                        firstNumber = ""
                        currentOperator = ""
                        result = ""
                        tvResult.text = "0"
                        tvFormula.text = ""

                    }
                }
            }
        }
    }
    private fun evaluateExpression(firstNumber:String,secondNumber:String,operator:String):String {
        val num1 = firstNumber.toDouble()
        val num2 = secondNumber.toDouble()
        return when(operator)
        {
            "+" -> (num1+num2).toString()
            "-" -> (num1-num2).toString()
            "*" -> (num1*num2).toString()
            "/" -> (num1/num2).toString()
            else -> ""
        }
    }
}
