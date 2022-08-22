package com.example.pogulator

import android.icu.number.Notation
import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal
import java.text.NumberFormat

class BasicFunctioningTests {

    @Test
    fun `Testing push operation`() {
        val engine = MathEngine()
        var error = false
        try{
            engine.pushOperation(Operation.ADD)
        }catch (e: Exception){
            error=true
        }
        assertEquals(false, error)
    }

    @Test
    fun `Testing pushing digits`(){
        val engine = MathEngine()
        engine.pushDigit("5")
        assertEquals("5", engine.operand1)
        engine.pushDigit("9")
        assertEquals("59", engine.operand1)
        engine.pushOperation(Operation.ADD)
        engine.pushDigit("3")
        assertEquals("3", engine.operand2)

    }



    @Test
    fun `Testing adding`(){
        val engine = MathEngine()

        engine.pushDigit("5")
        engine.pushOperation(Operation.ADD)
        engine.pushDigit("3")
        assertEquals("8",engine.doOperation())

    }

    @Test
    fun `Testing subtracting`(){
        val engine = MathEngine()
        engine.pushDigit("5")
        engine.pushOperation(Operation.SUBTRACT)
        engine.pushDigit("3")
        assertEquals("2",engine.doOperation())
        engine.reset()
        engine.pushDigit("3")
        engine.pushOperation(Operation.SUBTRACT)
        engine.pushDigit("5")
        assertEquals("-2",engine.doOperation())
    }

    @Test
    fun `Testing multi`(){
        val engine = MathEngine()
        engine.pushDigit("5")
        engine.pushOperation(Operation.MULTIPLY)
        engine.pushDigit("3")
        assertEquals("15",engine.doOperation())
        engine.reset()
        engine.pushDigit("-5")
        engine.pushOperation(Operation.MULTIPLY)
        engine.pushDigit("3")
        assertEquals("-15",engine.doOperation())

    }

    @Test
    fun `Testing divide`(){
        val engine = MathEngine()
        engine.pushDigit("10")
        engine.pushOperation(Operation.DIVIDE)
        engine.pushDigit("5")
        assertEquals("2",engine.doOperation())
        engine.reset()
        engine.pushDigit("-10")
        engine.pushOperation(Operation.DIVIDE)
        engine.pushDigit("5")
        assertEquals("-2",engine.doOperation())
        engine.reset()
        engine.pushDigit("10")
        engine.pushOperation(Operation.DIVIDE)
        engine.pushDigit("3")   //if precision of rounding is changed these will have to be changed
        assertEquals("3.333333333333333",engine.doOperation())
        engine.reset()
        engine.pushDigit("1000")
        engine.pushOperation(Operation.DIVIDE)
        engine.pushDigit("3")
        assertEquals("333.33333",engine.doOperation())

    }

    @Test
    fun `Testing square`(){
        val engine = MathEngine()

        engine.pushDigit("5")
        engine.pushOperation(Operation.SQUARE)
        assertEquals("25",engine.doOperation())
        engine.reset()
        engine.pushDigit("-5")
        engine.pushOperation(Operation.SQUARE)
        assertEquals("25",engine.doOperation())
        engine.reset()
        engine.pushDigit(".5")
        engine.pushOperation(Operation.SQUARE)
        assertEquals("0.25",engine.doOperation())

    }

    @Test
    fun `Testing power`(){
        val engine = MathEngine()
        engine.pushDigit("3")
        engine.pushOperation(Operation.POWER)
        engine.pushDigit("3")
        assertEquals("27",engine.doOperation())

    }

    @Test
    fun `Testing root`(){
        val engine = MathEngine()
        engine.pushDigit("25")
        engine.pushOperation(Operation.ROOT)
        assertEquals("5.0000000000000000",engine.doOperation())
    }

    @Test
    fun miscTest(){
        var myString = NumberFormat.getInstance().format(BigDecimal("123456789.35"))
        assertEquals("123,456,789.35",myString)
    }

    @Test
    fun `del function Testing`(){
        val engine = MathEngine()
        engine.pushDigit("532")
        engine.del()
        assertEquals("53",engine.operand1)
        engine.pushOperation(Operation.ADD)
        engine.pushDigit("332")
        engine.del()
        assertEquals("33",engine.operand2)
        engine.doOperation()
        assertEquals("86",engine.operand1)
        engine.del()
        assertEquals("8",engine.operand1)
    }

    @Test
    fun `double equals testing`(){
        val engine = MathEngine()
        engine.pushDigit("1")
        engine.pushOperation(Operation.ADD)
        engine.pushDigit("3")
        engine.doOperation()
        assertEquals("4",engine.operand1)
        engine.doOperation()
        assertEquals("7",engine.operand1)

    }


}