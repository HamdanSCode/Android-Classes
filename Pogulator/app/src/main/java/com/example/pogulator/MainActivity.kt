package com.example.pogulator

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    val OUTPUT = "OUTPUT"
    val HISTORY = "HISTORY"

    //dont forget ripples
    var equalFlag: Boolean = false
    private val _mathEngine = MathEngine()
    val mathEngine: MathEngine
        get() = _mathEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (resources.configuration.orientation == 2) {
            allButtons()
            //enable all buttons
        } else {
            portraitButtons()
            //enable portrait buttons
        }
        if(savedInstanceState!=null){
            findViewById<TextView>(R.id.output).text=savedInstanceState.getString(OUTPUT)
            if(savedInstanceState.getString(OUTPUT)!=null||savedInstanceState.getString(OUTPUT)!="")mathEngine.pushDigit(savedInstanceState.getString(OUTPUT).toString())
            findViewById<TextView>(R.id.history).text=savedInstanceState.getString(HISTORY)
        }
        findViewById<TextView>(R.id.output).setOnLongClickListener{
            copyTextToClipboard(findViewById<TextView>(R.id.output).text.toString())
            true
        }
    }

    fun portraitButtons() {
        findViewById<TextView>(R.id.deci).setOnClickListener(::others)
        findViewById<TextView>(R.id.posNeg).setOnClickListener(::others)
        findViewById<ImageView>(R.id.back).setOnClickListener(::others)
        findViewById<TextView>(R.id.clear).setOnClickListener(::others)
        findViewById<TextView>(R.id.equal).setOnClickListener(::others)


        findViewById<TextView>(R.id.divide).setOnClickListener(::operators)
        findViewById<TextView>(R.id.plus).setOnClickListener(::operators)
        findViewById<TextView>(R.id.multi).setOnClickListener(::operators)
        findViewById<TextView>(R.id.sub).setOnClickListener(::operators)


        findViewById<TextView>(R.id.num0).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num1).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num2).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num3).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num4).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num5).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num6).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num7).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num8).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num9).setOnClickListener(::numbers)

    }

    fun allButtons() {
        findViewById<TextView>(R.id.deci).setOnClickListener(::others)
        findViewById<TextView>(R.id.posNeg).setOnClickListener(::others)
        findViewById<ImageView>(R.id.back).setOnClickListener(::others)
        findViewById<TextView>(R.id.clear).setOnClickListener(::others)
        findViewById<TextView>(R.id.equal).setOnClickListener(::others)

        findViewById<TextView>(R.id.divide).setOnClickListener(::operators)
        findViewById<TextView>(R.id.plus).setOnClickListener(::operators)
        findViewById<TextView>(R.id.multi).setOnClickListener(::operators)
        findViewById<TextView>(R.id.sub).setOnClickListener(::operators)
        findViewById<TextView>(R.id.squared).setOnClickListener(::operators)
        findViewById<TextView>(R.id.root).setOnClickListener(::operators)
        findViewById<TextView>(R.id.power).setOnClickListener(::operators)

        findViewById<TextView>(R.id.num0).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num1).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num2).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num3).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num4).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num5).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num6).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num7).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num8).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.num9).setOnClickListener(::numbers)
        findViewById<TextView>(R.id.pi).setOnClickListener(::numbers)
    }

    fun numbers(v: View) {
    try {
        when (v.id) {
            R.id.num0 -> mathEngine.pushDigit("0")
            R.id.num1 -> mathEngine.pushDigit("1")
            R.id.num2 -> mathEngine.pushDigit("2")
            R.id.num3 -> mathEngine.pushDigit("3")
            R.id.num4 -> mathEngine.pushDigit("4")
            R.id.num5 -> mathEngine.pushDigit("5")
            R.id.num6 -> mathEngine.pushDigit("6")
            R.id.num7 -> mathEngine.pushDigit("7")
            R.id.num8 -> mathEngine.pushDigit("8")
            R.id.num9 -> mathEngine.pushDigit("9")
            R.id.pi -> mathEngine.pushPi()
        }
        displayOut()
    }
    catch (e:IllegalStateException){
        Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
        displayOut()
    }
    }

    fun operators(v: View) {
        //try catch on this when block
        try{
        when (v.id) {
            R.id.plus -> {
                mathEngine.pushOperation(Operation.ADD)
            }
            R.id.sub -> {
                mathEngine.pushOperation(Operation.SUBTRACT)
            }
            R.id.multi -> {
                mathEngine.pushOperation(Operation.MULTIPLY)
            }
            R.id.divide -> {
                mathEngine.pushOperation(Operation.DIVIDE)
            }
            R.id.squared -> {
                mathEngine.pushOperation(Operation.SQUARE)
                mathEngine.doOperation()
                displayOutFadeIn()
                return
            }
            R.id.power -> {
                mathEngine.pushOperation(Operation.POWER)
            }
            R.id.root -> {
                mathEngine.pushOperation(Operation.ROOT)
                mathEngine.doOperation()
                displayOutFadeIn()
                return
            }
        }
        displayOut()
        }
        catch(e:IllegalStateException){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
        }
    }
        fun others(v: View) {
            when (v.id) {
                R.id.back -> {
                    mathEngine.del()
                    displayOut()
                }
                R.id.deci -> {
                        mathEngine.pushDeci()
                }
                R.id.posNeg -> {
                    mathEngine.posNeg()
                    displayOut()
                }
                R.id.clear -> {
                    clear()
                }
                R.id.equal -> {
                    try{
                    mathEngine.doOperation()
                    displayOutFadeIn()
                    }catch(e:IllegalStateException) {
                        Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    fun displayOut() {
        if(countOfDigits()>15){
            findViewById<TextView>(R.id.output).text=REALformat(BigDecimal(mathEngine.outputString().replace(",","")))
        }
        else findViewById<TextView>(R.id.output).text = mathEngine.outputString()
        if (mathEngine.history != null) findViewById<TextView>(R.id.history).text =
            mathEngine.history
        else findViewById<TextView>(R.id.history).text = ""
    }
    fun displayOutFadeIn(){
        if(countOfDigits()>15){
            findViewById<TextView>(R.id.output).text=REALformat(BigDecimal(mathEngine.outputString().replace(",","")))
        }
        else findViewById<TextView>(R.id.output).text = mathEngine.outputString()
        if (mathEngine.history != null) findViewById<TextView>(R.id.history).text =
            mathEngine.history
        else findViewById<TextView>(R.id.history).text = ""
        findViewById<TextView>(R.id.output).startAnimation(AnimationUtils.loadAnimation(this,R.anim.fadeing_in))
    }
    fun clear(){
        mathEngine.reset()
        displayOut()
    }
    fun formatOutput(){
        var out = findViewById<TextView>(R.id.output).text


    }

    private fun copyTextToClipboard(s:String) {
        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", s)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "text copied", Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putString(OUTPUT, findViewById<TextView>(R.id.output).text.toString())
        //declare values before saving the state
        savedInstanceState.putString(HISTORY, findViewById<TextView>(R.id.history).text.toString())
        super.onSaveInstanceState(savedInstanceState)
    }

    fun countOfDigits():Int{
        var x = mathEngine.outputString()
        x = x.replace(",","")
        x = x.replace(".","")
        return x.length
    }

    /*
    to do
    more robust way of checking if were showing the result
     */

    private fun REALformat(x: BigDecimal): String {
        val numberOfDigits = x.toPlainString().run {
            this.length -
                    (if (this.contains("-")) 1 else 0) -
                    (if (this.contains(".")) 1 else 0)
        }
        return if (numberOfDigits > 6) format(x).orEmpty() else x.toPlainString()
    }

    private fun format(x: BigDecimal): String? {
        val formatter: NumberFormat = DecimalFormat("0.0E0")
        formatter.roundingMode = RoundingMode.HALF_UP
        formatter.minimumFractionDigits = if (x.scale() > 0) x.precision() else x.scale()
        return formatter.format(x)
    }


}