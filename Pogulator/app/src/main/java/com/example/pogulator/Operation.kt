package com.example.pogulator

enum class Operation {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    SQUARE,
    ROOT,
    POWER;

    override fun toString(): String {
        return when(this){
            ADD->"+"
            SUBTRACT->"-"
            MULTIPLY ->"×"
            POWER->"^"
            DIVIDE->"÷"
            ROOT->"sqrt"
            SQUARE->"square"
        }
    }

}



