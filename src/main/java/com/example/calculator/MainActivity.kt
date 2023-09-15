package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import java.util.Stack

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var prevNum: CharSequence? = null
    private var sign= "+"
    private var sumSoFar = 0
    private var isResultCalled = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun calculate(s: CharSequence?): Double {
        var num = 0
        var result = 0.0
        var sign = '+'
        val st = Stack<Int>()

        if (s != null) {
            for (i in 0 until s.length) {
                val ch = s?.get(i)
                if (ch?.isDigit()==true) {
                    num = num * 10 + (ch - '0')
                }
                if (ch != null) {
                    if (!ch.isDigit() || i == s.length - 1) {
                        when (sign) {
                            '+' -> st.push(num)
                            '-' -> st.push(-num)
                            '/' -> st.push(st.pop() / num)
                            else -> st.push(st.pop() * num)
                        }
                        sign = ch
                        num = 0
                    }
                }
            }
        }

        while (st.isNotEmpty()) {
            result += st.pop()
        }

        isResultCalled = true
        return result
    }


    fun calculateResult(view: View){
        tvInput?.text = calculate(tvInput?.text).toString()


    }

    fun isPreviousCharOperator(): Boolean {
        val text = tvInput?.text.toString()
        val lastIndex = text.length - 1

        if (lastIndex >= 0) {
            val prevChar: Char = text[lastIndex]
            return prevChar == '-' || prevChar == '+' || prevChar == '/' || prevChar == '*'
        }

        return false
    }

    fun isCurrentCharOperator(t: CharSequence): Boolean {
            var text = t.first()
            return text == '-' || text == '+' || text == '/' || text == '*'
    }


    fun onClick(view: View){
        var ch = (view as Button).text

        if(isResultCalled){
            isResultCalled = false
            tvInput?.text = ""
            tvInput?.append(ch)

        }else{
            tvInput?.append(ch)
        }



    }

    fun onClear(view: View){
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View){
        if ((tvInput?.text?.contains("."))==false)
            tvInput?.append((view as Button).text)

    }
}