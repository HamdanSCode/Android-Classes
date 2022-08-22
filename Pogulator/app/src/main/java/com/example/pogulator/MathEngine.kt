package com.example.pogulator

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.NumberFormat


class MathEngine {

    var operand1: String? = null
    var operand2: String? = null
    var operation: Operation? = null
    var result: String? = null
    var history: String? = null

    var histOp1: String? = null
    var histOp2: String? = null
    var histOperator: String? = null

    /*
    1 + 1 =
    2
    equal pressed again
    2 + 1
    3

    push 1 to operand1 which is currently null
    push operation
    push 1 to operand 2 which is currently null
    doOperation which will set result and also create the history by
        perform operation, set result to the resultant of the operation
        set histOp1 to operand1
        set histOp2 to operand2
        set histOperator to Operator
        concatenate that together for history
    set operator and operand2 to null
    set operand1 to be result

    equal pressed again
    if operator == null
        if histOperator != null
            set operator to histOperator and operand2 to histOp2 (if not root or square)

     */






    fun doOperation(): String{
        if(operation==null&&histOperator!=null){
            operation=toOperation(histOperator.toString())
            if(histOp2!=null)operand2=histOp2.toString().replace(",","")
        }
        return when(operation){
            Operation.ADD ->{addOperands()}
            Operation.SUBTRACT ->{subOpperands()}
            Operation.DIVIDE ->{divOpperands()}
            Operation.MULTIPLY ->{mulOpperands()}
            Operation.SQUARE ->{squareOpperands()}
            Operation.ROOT ->{rootOpperands()}
            Operation.POWER ->{powerOpperands()}
            else ->"oof"
        }
    }
    fun reset(){
        operand1 = null
        operand2 = null
        operation = null
        histOperator=null
        history=null
        histOp1=null
        histOp2=null
    }
    fun pushDigit(s: String){
        if(operation == null){
            if(operand1 == null) operand1 =""
            if(operand1.toString().length>15) throw IllegalStateException("Op1 has 16 Chars")
            operand1 += s
        }
        else {
            if(operand2==null) operand2 =""
            if(operand2.toString().length>15) throw IllegalStateException("Op2 has 16 Chars")
            operand2 += s
        }
    }
    fun pushPi(){
        if(operation == null){
            operand1="3.14159"
        }else{
            operand2="3.14159"
        }
    }
    fun pushOperation(op: Operation){
        if(operand1!=null&&operand2!=null&&operation!=null){
            if(operation==Operation.DIVIDE&&operand2=="0")throw IllegalStateException("Divide by 0")
            doOperation()
        }
        operation = op
        histOperator=op.toString()
        histOp2=""
        operand2=null
    }
    fun pushDeci(){
        if(operation == null){
            if(operand1 == null) operand1 ="0"
            if(!operand1.toString().contains("."))operand1 += "."}
        else {
            if(operand2==null) operand2 ="0"
            if(!operand2.toString().contains("."))operand2 += "."}
    }

    fun posNeg(){
        if(operation==null){
            if(operand1==null) {}//donothing
            else operand1 = BigDecimal(operand1).multiply(BigDecimal("-1")).toString()
        }else{
            if(operand2==null) {}//donothing
            else operand2 = BigDecimal(operand2).multiply(BigDecimal("-1")).toString()

        }
    }


    private fun addOperands(): String{
        if(operand1 == null) throw IllegalStateException("Op1 is null")
        if(operand2 == null) throw IllegalStateException("Op2 is null")
        if(operand1=="3.14159")histOp1="π"
        else histOp1=formatCommas(operand1)
        if(operand2=="3.14159")histOp2="π"
        else histOp2=formatCommas(operand2)
        histOperator=operation.toString()
        history=histOp1+histOperator+histOp2
        operand1 = (BigDecimal(operand1).add(BigDecimal(operand2))).toString()
        operand2=null
        operation=null
        return operand1.toString()
    }
    private fun subOpperands(): String{
        if(operand1 == null) throw IllegalStateException("Op1 is null")
        if(operand2 == null) throw IllegalStateException("Op2 is null")
        if(operand1=="3.14159")histOp1="π"
        else histOp1=formatCommas(operand1)
        if(operand2=="3.14159")histOp2="π"
        else histOp2=formatCommas(operand2)
        histOperator=operation.toString()
        history=histOp1+histOperator+histOp2
        operand1 = (BigDecimal(operand1).subtract(BigDecimal(operand2))).toString()
        operand2=null
        operation=null
        return operand1.toString()
    }
    private fun divOpperands(): String{
        if(operand1 == null) throw IllegalStateException("Op1 is null")
        if(operand2 == null) throw IllegalStateException("Op2 is null")
        if(operand2 =="0") throw IllegalStateException("Divide by 0")
        //the precision is required for run on decimals like 10/3, infinite 3's
        if(operand1=="3.14159")histOp1="π"
        else histOp1=formatCommas(operand1)
        if(operand2=="3.14159")histOp2="π"
        else histOp2=formatCommas(operand2)
        histOperator=operation.toString()
        history=histOp1+histOperator+histOp2
        operand1 = (BigDecimal(operand1).divide(BigDecimal(operand2),MathContext(16, RoundingMode.HALF_UP))).toString()
        operand2=null
        operation=null
        return operand1.toString()
    }
    private fun mulOpperands(): String{
        if(operand1 == null) throw IllegalStateException("Op1 is null")
        if(operand2 == null) throw IllegalStateException("Op2 is null")
        if(operand1=="3.14159")histOp1="π"
        else histOp1=formatCommas(operand1)
        if(operand2=="3.14159")histOp2="π"
        else histOp2=formatCommas(operand2)
        histOperator=operation.toString()
        history=histOp1+histOperator+histOp2
        operand1 = (BigDecimal(operand1).multiply(BigDecimal(operand2))).toString()
        operand2=null
        operation=null
        return operand1.toString()
    }
    private fun squareOpperands(): String{
        if(operand1 == null) throw IllegalStateException("Op1 is null")
        if(operand1=="3.14159")histOp1="π"
        else histOp1=formatCommas(operand1)
        histOp2=null
        histOperator=operation.toString()
        history=histOperator+histOp1
        operand1 = (BigDecimal(operand1).multiply(BigDecimal(operand1))).toString()
        operand2=null
        operation=null
        return operand1.toString()
    }
    private fun rootOpperands(): String{
        if(operand1 == null) throw IllegalStateException("Op1 is null")
        if(operand1=="3.14159")histOp1="π"
        else histOp1=formatCommas(operand1)
        histOp2=null
        histOperator=operation.toString()
        history=histOperator+histOp1
        operand1 = sqrt(BigDecimal(operand1),16).toString()
        operand2=null
        operation=null
        return operand1.toString()
    }
    private fun powerOpperands(): String{
        if(operand1 == null) throw IllegalStateException("Op1 is null")
        if(operand2 == null) throw IllegalStateException("Op2 is null")
        //note there are some powers that will just cause such a massive number, make a cut off point in the palce we return this to
        if(BigDecimal(operand2).compareTo(BigDecimal("50"))==0||BigDecimal(operand2).compareTo(BigDecimal("50"))==1 )throw IllegalStateException("Op2 is too large")
        if(operand1=="3.14159")histOp1="π"
        else histOp1=formatCommas(operand1)
        if(operand2=="3.14159")histOp2="π"
        else histOp2=formatCommas(operand2)
        histOperator=operation.toString()
        history=histOp1+histOperator+histOp2
        operand1 = (BigDecimal(operand1).pow(BigDecimal(operand2).toInt())).toString()
        operand2 = null
        operation = null
        return operand1.toString()
    }

    fun del(){
        if(operation==null){
            if(operand1!=null && (operand1!=""||operand1!="0")){
                operand1=operand1.toString().dropLast(1)
                if(operand1==""){
                    operand1="0"
                    history=""
                    histOp1=null
                    histOp2=null
                    histOperator=null
                }
            }
        }else{
            if(operand2!=null && (operand2!=""||operand2!="0")){
                operand2=operand2.toString().dropLast(1)
                if(operand2==""){
                    operand2="0"
                    history=""
                    histOp1=null
                    histOp2=null
                    histOperator=null
                }
            }
        }
    }


    fun outputString(): String{
        if(operation==null){
            if(operand1==null) return ""
            else return formatCommas(operand1)
        }else{
            if(operand2==null) return formatCommas(operand1)
            else return formatCommas(operand2)
        }
    }

    fun toOperation(s:String): Operation {
        return when(s){
            "+"->Operation.ADD
            "-"->Operation.SUBTRACT
            "×"->Operation.MULTIPLY
            "^"->Operation.POWER
            "÷"->Operation.DIVIDE
            "sqrt"->Operation.ROOT
            "square"->Operation.SQUARE
            else ->Operation.ADD
        }
    }

    fun formatCommas(s: String?): String{
        return if(s==null) "0"
        else NumberFormat.getInstance().format(BigDecimal(s))
    }


    fun sqrt(A: BigDecimal, SCALE: Int): BigDecimal? {
        var x0 = BigDecimal("0")
        var x1 = BigDecimal(kotlin.math.sqrt(A.toDouble()))
        while (x0 != x1) {
            x0 = x1
            x1 = A.divide(x0, SCALE, RoundingMode.HALF_UP)
            x1 = x1.add(x0)
            x1 = x1.divide(BigDecimal("2"), SCALE, RoundingMode.HALF_UP)
        }
        return x1
    }



}